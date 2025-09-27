document.addEventListener('DOMContentLoaded', function() {
    // Global variables
    let currentUsername = '';
    let stompClient = null;
    let socket = null;
    let reconnectAttempts = 0;
    const maxReconnectAttempts = 5;

    // Chat state management
    let currentChatType = 'public'; // 'public' or 'private'
    let currentPrivateUser = null;
    let publicMessages = [];
    let privateMessages = new Map();

    // Unread message tracking
    let unreadCounts = new Map(); // Track unread messages per user
    let userOrder = []; // Track user order for prioritization

    // DOM elements
    const usernameSection = document.getElementById('username-section');
    const chatSection = document.getElementById('chat-section');
    const inputUsername = document.getElementById('input-username');
    const sendUsernameBtn = document.getElementById('send-username-btn');
    const usernameError = document.getElementById('username-error');
    const usernameDisplay = document.getElementById('username-display');
    const inputMsg = document.getElementById('input-msg');
    const sendBtn = document.getElementById('send-msg-btn');
    const messagesContainer = document.getElementById('messages');
    const usersContainer = document.getElementById('users');
    const userCount = document.getElementById('user-count');

    // Chat control elements
    const chatWithElement = document.getElementById('chat-with');
    const publicChatBtn = document.getElementById('public-chat-btn');

    // Focus on username input when page loads
    inputUsername.focus();

    // Event listeners setup
    setupEventListeners();

    function setupEventListeners() {
        // Username section
        sendUsernameBtn.addEventListener('click', joinChat);
        inputUsername.addEventListener('keydown', handleUsernameKeydown);
        inputUsername.addEventListener('input', () => {
            updateUsernameButtonState();
            clearError();
        });

        // Chat section
        sendBtn.addEventListener('click', sendMessage);
        inputMsg.addEventListener('keydown', handleMessageKeydown);
        inputMsg.addEventListener('input', () => {
            updateSendButtonState();
            autoResizeTextarea();
        });

        // Public chat button event listener
        publicChatBtn.addEventListener('click', switchToPublicChat);

        // Page unload events - use multiple for better coverage
        window.addEventListener('beforeunload', handlePageUnload);
        window.addEventListener('unload', handlePageUnload);
        window.addEventListener('pagehide', handlePageUnload);
        document.addEventListener('visibilitychange', handleVisibilityChange);
    }

    // Handle visibility changes without leaving the chat
    function handleVisibilityChange() {
        if (document.visibilityState === 'visible') {
            // Tab became visible - could reconnect if disconnected
            console.log('Tab became visible');

            // Check if we're still connected and reconnect if needed
            if (currentUsername && (!stompClient || !stompClient.connected)) {
                console.log('Attempting to reconnect...');
                connectWebSocket().catch(error => {
                    console.error('Failed to reconnect:', error);
                });
            }
        } else if (document.visibilityState === 'hidden') {
            // Tab became hidden - just log it, don't leave the chat
            console.log('Tab became hidden');
        }
    }

    function handleUsernameKeydown(e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            joinChat();
        }
    }

    function handleMessageKeydown(e) {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            sendMessage();
        }
    }

    function handlePageUnload() {
        if (!currentUsername) return;

        // Use sendBeacon for reliable delivery even during page unload
        // This works better than WebSocket messages during page close
        if (navigator.sendBeacon) {
            const data = JSON.stringify({ username: currentUsername });
            const blob = new Blob([data], { type: 'application/json' });
            navigator.sendBeacon('/api/leave', blob);
        }

        // Also try to send via WebSocket as backup (might not work but doesn't hurt)
        if (stompClient && stompClient.connected) {
            try {
                // Use synchronous send if possible
                stompClient.send("/app/leave", {}, JSON.stringify({
                    username: currentUsername
                }));

                // Try to disconnect cleanly
                stompClient.disconnect();
            } catch (error) {
                // Expected to fail sometimes during page close
            }
        }

        // Clear username to prevent duplicate sends
        currentUsername = '';
    }

    async function joinChat() {
        const username = inputUsername.value.trim();

        if (!validateUsername(username)) return;

        sendUsernameBtn.disabled = true;
        sendUsernameBtn.textContent = 'Joining...';

        try {
            const response = await fetch('/api/join', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username })
            });

            if (!response.ok) {
                throw new Error(`HTTP ${response.status}`);
            }

            const data = await response.json();

            if (data.success) {
                currentUsername = username;
                usernameDisplay.textContent = username;

                // Load previous messages and users
                loadPreviousMessages(data.messages);
                loadCurrentUsers(data.users);

                // Initialize to public chat by default
                initializePublicChat();

                // Transition to chat interface
                transitionToChat();

                // Connect WebSocket
                await connectWebSocket();
            } else {
                showError(data.message || 'Failed to join chat');
            }
        } catch (error) {
            console.error('Error joining chat:', error);
            showError('Failed to join chat. Please check your connection and try again.');
        } finally {
            sendUsernameBtn.disabled = false;
            sendUsernameBtn.textContent = 'Join';
        }
    }

    function validateUsername(username) {
        if (username === '') {
            showError('Username cannot be empty');
            return false;
        }
        if (username.length > 20) {
            showError('Username must be 20 characters or less');
            return false;
        }
        return true;
    }

    function loadPreviousMessages(messages) {
        if (messages?.length > 0) {
            // Store public messages and display them
            publicMessages = messages.map(msg => ({ ...msg, type: 'public' }));
            messages.forEach(message => displayMessage(message, message.username === currentUsername));
        }
    }

    function loadCurrentUsers(users) {
        // Clear existing users first
        usersContainer.innerHTML = '';
        // Clear tracking data
        unreadCounts.clear();
        userOrder = [];

        if (users?.length > 0) {
            // Only add other users, not the current user
            users.forEach(username => {
                if (username !== currentUsername) {
                    addUser(username);
                }
            });
        }
        updateUserCount();
    }

    // Initialize public chat state
    function initializePublicChat() {
        currentChatType = 'public';
        currentPrivateUser = null;
        updateChatIndicator();
        updatePublicChatButton();
    }

    function transitionToChat() {
        usernameSection.style.display = 'none';
        chatSection.style.display = 'flex';
        inputMsg.focus();
    }

    function connectWebSocket() {
        return new Promise((resolve, reject) => {
            try {
                socket = new SockJS("/chat");
                stompClient = Stomp.over(socket);

                stompClient.connect({},
                    function onConnect() {
                        console.log('Connected to WebSocket');
                        reconnectAttempts = 0;
                        setupSubscriptions();
                        resolve();
                    },
                    function onError(error) {
                        console.error('WebSocket connection error:', error);
                        handleConnectionError();
                        reject(error);
                    }
                );

                socket.onclose = function(event) {
                    console.log('WebSocket connection closed:', event);

                    // When socket closes, ensure we clean up
                    if (currentUsername && !event.wasClean) {
                        // Try to notify server of disconnect via REST
                        if (navigator.sendBeacon) {
                            const data = JSON.stringify({ username: currentUsername });
                            const blob = new Blob([data], { type: 'application/json' });
                            navigator.sendBeacon('/api/leave', blob);
                        }
                        handleConnectionError();
                    }
                };

            } catch (error) {
                console.error('Failed to create WebSocket connection:', error);
                reject(error);
            }
        });
    }

    function setupSubscriptions() {
        // Subscribe to messages
        stompClient.subscribe('/topic/messages', function(message) {
            const messageData = JSON.parse(message.body);
            // NEW: Handle message based on current chat type
            handleIncomingMessage(messageData);
        });

        // Subscribe to user events
        stompClient.subscribe('/topic/users', function(message) {
            const userEvent = JSON.parse(message.body);
            handleUserEvent(userEvent);
        });

        // Subscribe to broadcast private messages and filter client-side
        stompClient.subscribe('/topic/private-messages', function(message) {
            const privateMessageData = JSON.parse(message.body);

            // Only process if message involves current user (either sender or recipient)
            if (privateMessageData.fromUser === currentUsername ||
                privateMessageData.toUser === currentUsername) {
                handleIncomingPrivateMessage(privateMessageData);
            }
        });
    }

    // Handle incoming messages based on chat type
    function handleIncomingMessage(messageData) {
        // Add to public messages storage
        publicMessages.push({ ...messageData, type: 'public' });

        // Only display if we're in public chat
        if (currentChatType === 'public') {
            displayMessage(messageData, messageData.username === currentUsername);
        }
    }

    // Handle incoming private messages with counter logic
    function handleIncomingPrivateMessage(privateMessageData) {
        const otherUser = privateMessageData.fromUser === currentUsername ?
            privateMessageData.toUser : privateMessageData.fromUser;

        // Store the private message
        if (!privateMessages.has(otherUser)) {
            privateMessages.set(otherUser, []);
        }
        privateMessages.get(otherUser).push({
            ...privateMessageData,
            type: 'private'
        });

        // f this is a received message (not sent by current user)
        if (privateMessageData.fromUser !== currentUsername) {
            // Increment unread count if not in private chat with this user
            if (currentChatType !== 'private' || currentPrivateUser !== otherUser) {
                incrementUnreadCount(otherUser);
            }

            // Move user to top of list
            moveUserToTop(otherUser);

            // Display message if in active chat
            if (currentChatType === 'private' && currentPrivateUser === otherUser) {
                displayMessage({
                    username: privateMessageData.fromUser,
                    message: privateMessageData.message,
                    date: privateMessageData.date,
                    id: privateMessageData.id
                }, false);
            }
        }
    }

    function handleUserEvent(userEvent) {
        console.log('User event received:', userEvent);

        if (userEvent.action === 'join' && userEvent.username !== currentUsername) {
            // Only add other users to the current user's list, not themselves
            addUser(userEvent.username);
            updateUserCount();
        } else if (userEvent.action === 'leave') {
            removeUser(userEvent.username);
            updateUserCount();
            // If we're in private chat with the user who left, switch to public
            if (currentChatType === 'private' && currentPrivateUser === userEvent.username) {
                switchToPublicChat();
            }
        }
    }

    function handleConnectionError() {
        if (reconnectAttempts < maxReconnectAttempts) {
            reconnectAttempts++;
            const delay = Math.min(1000 * Math.pow(2, reconnectAttempts), 30000);

            setTimeout(() => {
                console.log(`Attempting to reconnect... (${reconnectAttempts}/${maxReconnectAttempts})`);
                connectWebSocket().catch(() => {
                    // Will retry or show final error
                });
            }, delay);
        } else {
            showError('Connection lost. Please refresh the page to reconnect.');
        }
    }

    function sendMessage() {
        const message = inputMsg.value.trim();
        if (message === '' || !isConnected()) return;

        try {
            // Send different message types based on chat mode
            if (currentChatType === 'public') {
                stompClient.send("/app/message", {}, JSON.stringify({
                    username: currentUsername,
                    message: message,
                    messageId: generateMessageId()
                }));
            } else {
                // Send private message through backend
                const messageId = generateMessageId();
                stompClient.send("/app/private-message", {}, JSON.stringify({
                    fromUser: currentUsername,
                    toUser: currentPrivateUser,
                    message: message,
                    messageId: messageId
                }));

                // Move user to top when sending private message
                moveUserToTop(currentPrivateUser);

                // Immediately display the sent message locally to fix display issue
                const localMessage = {
                    id: Date.now(),
                    fromUser: currentUsername,
                    toUser: currentPrivateUser,
                    message: message,
                    date: new Date().toLocaleString('en-US', {
                        month: '2-digit',
                        day: '2-digit',
                        hour: '2-digit',
                        minute: '2-digit'
                    }),
                    messageId: messageId,
                    type: 'private'
                };

                // Store in private messages
                if (!privateMessages.has(currentPrivateUser)) {
                    privateMessages.set(currentPrivateUser, []);
                }
                privateMessages.get(currentPrivateUser).push(localMessage);

                // Display the message immediately
                displayMessage({
                    username: currentUsername,
                    message: message,
                    date: localMessage.date,
                    id: localMessage.id
                }, true);
            }

            resetMessageInput();
        } catch (error) {
            console.error('Failed to send message:', error);
            showError('Failed to send message. Please try again.');
        }
    }

    function generateMessageId() {
        return Date.now() + '-' + Math.random().toString(36).slice(2, 11);
    }

    function isConnected() {
        if (!stompClient?.connected) {
            showError('Not connected to chat. Please refresh the page.');
            return false;
        }
        return true;
    }

    function resetMessageInput() {
        inputMsg.value = '';
        updateSendButtonState();
        inputMsg.style.height = 'auto';
        inputMsg.focus();
    }

    function displayMessage(messageData, isOwnMessage) {
        const messageContainer = createMessageContainer(messageData, isOwnMessage);
        messagesContainer.appendChild(messageContainer);
        scrollToBottom();
    }

    function createMessageContainer(messageData, isOwnMessage) {
        const container = document.createElement('div');
        container.classList.add('message-container');
        container.classList.add(isOwnMessage ? 'own-message' : 'other-message');

        const sender = document.createElement('div');
        sender.classList.add('sender');
        sender.textContent = messageData.username;

        const message = document.createElement('div');
        message.classList.add('message');
        message.textContent = messageData.message;

        const date = document.createElement('div');
        date.classList.add('date');
        date.textContent = messageData.date;

        container.appendChild(sender);
        container.appendChild(message);
        container.appendChild(date);

        return container;
    }

    function scrollToBottom() {
        setTimeout(() => {
            messagesContainer.scrollTop = messagesContainer.scrollHeight;
        }, 10);
    }

    // Create new user structure with container and counter
    function addUser(username) {
        // Check if user already exists
        if (usersContainer.querySelector(`[data-username="${username}"]`)) {
            return;
        }

        // Create user container
        const userContainer = document.createElement('div');
        userContainer.classList.add('user-container');
        userContainer.setAttribute('data-username', username);

        // Create user element
        const userElement = document.createElement('div');
        userElement.classList.add('user');
        userElement.textContent = username;

        // Create message counter
        const messageCounter = document.createElement('div');
        messageCounter.classList.add('new-message-counter');
        messageCounter.textContent = '0';

        userContainer.appendChild(userElement);
        userContainer.appendChild(messageCounter);

        // Add click handler for user container
        userContainer.addEventListener('click', () => switchToPrivateChat(username));

        usersContainer.appendChild(userContainer);

        // Initialize unread count and add to order tracking
        unreadCounts.set(username, 0);
        userOrder.push(username);
    }

    // Remove user and clean up tracking data
    function removeUser(username) {
        const userContainer = usersContainer.querySelector(`[data-username="${username}"]`);
        if (userContainer) {
            userContainer.remove();
            console.log('Removed user from DOM:', username);
        }

        // Clean up tracking data
        unreadCounts.delete(username);
        userOrder = userOrder.filter(user => user !== username);
    }

    // Increment unread message count
    function incrementUnreadCount(username) {
        const currentCount = unreadCounts.get(username) || 0;
        unreadCounts.set(username, currentCount + 1);
        updateMessageCounter(username);
    }

    // Reset unread message count
    function resetUnreadCount(username) {
        unreadCounts.set(username, 0);
        updateMessageCounter(username);
    }

    // Update visual message counter
    function updateMessageCounter(username) {
        const userContainer = usersContainer.querySelector(`[data-username="${username}"]`);
        if (!userContainer) return;

        const counter = userContainer.querySelector('.new-message-counter');
        const count = unreadCounts.get(username) || 0;

        if (count > 0) {
            counter.textContent = count;
            counter.classList.add('visible');
        } else {
            counter.classList.remove('visible');
        }
    }

    // Move user to top of list
    function moveUserToTop(username) {
        // Remove from current position
        userOrder = userOrder.filter(user => user !== username);
        // Add to beginning
        userOrder.unshift(username);

        // Reorder DOM elements
        reorderUsers();
    }

    // Reorder users in DOM based on priority
    function reorderUsers() {
        const fragment = document.createDocumentFragment();

        // Add users in priority order
        userOrder.forEach(username => {
            const userContainer = usersContainer.querySelector(`[data-username="${username}"]`);
            if (userContainer) {
                fragment.appendChild(userContainer);
            }
        });

        // Clear container and append reordered elements
        usersContainer.innerHTML = '';
        usersContainer.appendChild(fragment);
    }

    // Switch to private chat with a user
    async function switchToPrivateChat(username) {
        if (currentChatType === 'private' && currentPrivateUser === username) {
            return; // Already in this private chat
        }

        currentChatType = 'private';
        currentPrivateUser = username;

        // Reset unread count for this user
        resetUnreadCount(username);

        // Clear messages container
        clearMessages();

        // Load existing private messages from backend
        try {
            const response = await fetch('/api/private-messages', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    user1: currentUsername,
                    user2: username
                })
            });

            if (response.ok) {
                const existingMessages = await response.json();

                // Store messages locally
                privateMessages.set(username, existingMessages.map(msg => ({
                    ...msg,
                    type: 'private'
                })));

                // Display existing messages
                existingMessages.forEach(message => {
                    displayMessage({
                        username: message.fromUser,
                        message: message.message,
                        date: message.date,
                        id: message.id
                    }, message.fromUser === currentUsername);
                });
            } else {
                console.error('Failed to load private messages');
                // Initialize empty conversation
                if (!privateMessages.has(username)) {
                    privateMessages.set(username, []);
                }
            }
        } catch (error) {
            console.error('Error loading private messages:', error);
            // Initialize empty conversation on error
            if (!privateMessages.has(username)) {
                privateMessages.set(username, []);
            }
        }

        // Update UI
        updateChatIndicator();
        updatePublicChatButton();
        updateActiveUser(username);

        console.log(`Switched to private chat with ${username}`);
    }

    // Switch to public chat
    function switchToPublicChat() {
        if (currentChatType === 'public') {
            return; // Already in public chat
        }

        currentChatType = 'public';
        currentPrivateUser = null;

        // Clear messages container
        clearMessages();

        // Load public messages
        publicMessages.forEach(message => {
            displayMessage(message, message.username === currentUsername);
        });

        // Update UI
        updateChatIndicator();
        updatePublicChatButton();
        updateActiveUser(null);

        console.log('Switched to public chat');
    }

    // Clear all messages from container
    function clearMessages() {
        messagesContainer.innerHTML = '';
    }

    // Update chat indicator text
    function updateChatIndicator() {
        if (currentChatType === 'public') {
            chatWithElement.textContent = 'Public chat';
        } else {
            chatWithElement.textContent = currentPrivateUser;
        }
    }

    // Update public chat button state
    function updatePublicChatButton() {
        if (currentChatType === 'public') {
            publicChatBtn.classList.add('active');
        } else {
            publicChatBtn.classList.remove('active');
        }
    }

    // Update active user in sidebar to work with the new structure
    function updateActiveUser(activeUsername) {
        // Remove active class from all user containers
        usersContainer.querySelectorAll('.user-container').forEach(container => {
            container.classList.remove('active');
        });

        // Add active class to current user container if in private chat
        if (activeUsername) {
            const activeContainer = usersContainer.querySelector(`[data-username="${activeUsername}"]`);
            if (activeContainer) {
                activeContainer.classList.add('active');
            }
        }
    }

    function updateUserCount() {
        const count = usersContainer.children.length;
        userCount.textContent = count;
        console.log('Updated user count to:', count);
    }

    function showError(message) {
        usernameError.textContent = message;
        usernameError.style.color = '#dc3545';
        setTimeout(clearError, 5000);
    }

    function clearError() {
        usernameError.textContent = '';
    }

    function updateUsernameButtonState() {
        sendUsernameBtn.disabled = inputUsername.value.trim() === '';
    }

    function updateSendButtonState() {
        sendBtn.disabled = inputMsg.value.trim() === '';
    }

    function autoResizeTextarea() {
        inputMsg.style.height = 'auto';
        inputMsg.style.height = Math.min(inputMsg.scrollHeight, 120) + 'px';
    }

    // Initialize button states
    updateUsernameButtonState();
    updateSendButtonState();
});