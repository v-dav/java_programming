package chat;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;

@Controller
@RestController
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ConcurrentLinkedQueue<ChatMessage> messages = new ConcurrentLinkedQueue<>();
    private final ConcurrentHashMap<String, UserSession> activeUsers = new ConcurrentHashMap<>();
    private final AtomicLong messageIdCounter = new AtomicLong(0);
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<PrivateMessage>> privateMessages = new ConcurrentHashMap<>();

    private static final int MAX_MESSAGES = 1000;
    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MAX_MESSAGE_LENGTH = 1000;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessageRequest request) {
        if (request.username == null || request.message == null) {
            return null;
        }

        String username = request.username.trim();
        String message = request.message.trim();

        if (username.isEmpty() || message.isEmpty() ||
                username.length() > MAX_USERNAME_LENGTH ||
                message.length() > MAX_MESSAGE_LENGTH) {
            return null;
        }

        if (!activeUsers.containsKey(username)) {
            return null;
        }

        UserSession session = activeUsers.get(username);
        if (session != null) {
            session.updateActivity();
        }

        ChatMessage chatMessage = new ChatMessage(
                messageIdCounter.incrementAndGet(),
                username,
                message,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd HH:mm")),
                request.messageId
        );

        messages.offer(chatMessage);
        if (messages.size() > MAX_MESSAGES) {
            messages.poll();
        }

        return chatMessage;
    }

    @MessageMapping("/private-message")
    @SendTo("/topic/private-messages")  // Broadcast to all users
    public PrivateMessage sendPrivateMessage(PrivateMessageRequest request) {
        if (request.fromUser == null || request.toUser == null || request.message == null) {
            return null;
        }

        String fromUser = request.fromUser.trim();
        String toUser = request.toUser.trim();
        String message = request.message.trim();

        if (fromUser.isEmpty() || toUser.isEmpty() || message.isEmpty() ||
                fromUser.length() > MAX_USERNAME_LENGTH ||
                toUser.length() > MAX_USERNAME_LENGTH ||
                message.length() > MAX_MESSAGE_LENGTH) {
            return null;
        }

        // Check if both users are active
        if (!activeUsers.containsKey(fromUser) || !activeUsers.containsKey(toUser)) {
            return null;
        }

        // Update sender activity
        UserSession session = activeUsers.get(fromUser);
        if (session != null) {
            session.updateActivity();
        }

        PrivateMessage privateMessage = new PrivateMessage(
                messageIdCounter.incrementAndGet(),
                fromUser,
                toUser,
                message,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd HH:mm")),
                request.messageId
        );

        // Store private message using conversation key
        String conversationKey = createConversationKey(fromUser, toUser);
        privateMessages.computeIfAbsent(conversationKey, k -> new ConcurrentLinkedQueue<>()).offer(privateMessage);

        // Limit stored messages
        ConcurrentLinkedQueue<PrivateMessage> conversation = privateMessages.get(conversationKey);
        if (conversation.size() > MAX_MESSAGES) {
            conversation.poll();
        }

        // Return message to be broadcast - client will filter for relevant users
        return privateMessage;
    }

    @PostMapping("/api/private-messages")
    public ResponseEntity<List<PrivateMessage>> getPrivateMessages(@RequestBody PrivateMessagesRequest request) {
        if (request.user1 == null || request.user2 == null) {
            return ResponseEntity.badRequest().build();
        }

        String user1 = request.user1.trim();
        String user2 = request.user2.trim();

        if (user1.isEmpty() || user2.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        String conversationKey = createConversationKey(user1, user2);
        ConcurrentLinkedQueue<PrivateMessage> conversation = privateMessages.get(conversationKey);

        if (conversation == null) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        List<PrivateMessage> messageList = new ArrayList<>(conversation);

        return ResponseEntity.ok(messageList);
    }

    // Create consistent conversation key for two users
    private String createConversationKey(String user1, String user2) {
        if (user1.compareTo(user2) < 0) {
            return user1 + ":" + user2;
        } else {
            return user2 + ":" + user1;
        }
    }

    @PostMapping("/api/join")
    public ResponseEntity<JoinResponse> joinChat(@RequestBody JoinRequest request) {
        if (request.username == null) {
            return ResponseEntity.badRequest().body(new JoinResponse(
                    false, "Username is required", null, null));
        }

        String username = request.username.trim();

        if (username.isBlank()) {
            return ResponseEntity.badRequest().body(new JoinResponse(
                    false, "Username cannot be empty", null, null));
        }

        if (username.length() > MAX_USERNAME_LENGTH) {
            return ResponseEntity.badRequest().body(new JoinResponse(
                    false, "Username too long", null, null));
        }

        // Thread-safe check and put
        UserSession newSession = new UserSession(username);
        UserSession existingSession = activeUsers.putIfAbsent(username, newSession);

        if (existingSession != null) {
            return ResponseEntity.badRequest().body(new JoinResponse(
                    false, "Username already taken", null, null));
        }

        List<ChatMessage> recentMessages = messages.stream()
                .skip(Math.max(0, messages.size() - 100))
                .toList();

        List<String> otherUsers = activeUsers.keySet().stream()
                .filter(user -> !user.equals(username))
                .sorted()
                .toList();

        UserEvent joinEvent = new UserEvent("join", username);
        messagingTemplate.convertAndSend("/topic/users", joinEvent);

        return ResponseEntity.ok(new JoinResponse(
                true,
                "Welcome to chat",
                recentMessages, otherUsers)
        );
    }

    @MessageMapping("/leave")
    public void handleLeave(LeaveRequest request) {
        if (request.username != null) {
            UserSession removed = activeUsers.remove(request.username);

            if (removed != null) {
                UserEvent leaveEvent = new UserEvent("leave", request.username);
                messagingTemplate.convertAndSend("/topic/users", leaveEvent);
            }
        }
    }

    @PostMapping("/api/leave")
    public ResponseEntity<Void> leaveChat(@RequestBody LeaveRequest request) {
        if (request.username != null) {
            UserSession removed = activeUsers.remove(request.username);

            if (removed != null) {
                UserEvent leaveEvent = new UserEvent("leave", request.username);
                messagingTemplate.convertAndSend("/topic/users", leaveEvent);
            }
        }
        return ResponseEntity.ok().build();
    }

    // Clean up inactive users periodically
    @Scheduled(fixedRate = 300000) // Every 5 minutes
    public void cleanupInactiveUsers() {
        long now = System.currentTimeMillis();
        long inactiveThreshold = 30 * 60 * 1000L; // 30 minutes

        activeUsers.entrySet().removeIf(entry -> {
            boolean isInactive = now - entry.getValue().getLastActivity() > inactiveThreshold;
            if (isInactive) {
                UserEvent leaveEvent = new UserEvent("leave", entry.getKey());
                messagingTemplate.convertAndSend("/topic/users", leaveEvent);
            }
            return isInactive;
        });
    }

    public record ChatMessage(Long id, String username, String message, String date, String messageId) {}
    public record ChatMessageRequest(String username, String message, String messageId) {}
    public record PrivateMessage(Long id, String fromUser, String toUser, String message, String date, String messageId) {}
    public record PrivateMessageRequest(String fromUser, String toUser, String message, String messageId) {}
    public record PrivateMessagesRequest(String user1, String user2) {}

    public record JoinRequest(String username) {}
    public record JoinResponse(boolean success, String message, List<ChatMessage> messages, List<String> users) {}
    public record LeaveRequest(String username) {}
    public record UserEvent(String action, String username) {}

    private static class UserSession {
        private final String username;
        private volatile long lastActivity;

        public UserSession(String username) {
            this.username = username;
            this.lastActivity = System.currentTimeMillis();
        }

        public void updateActivity() {
            this.lastActivity = System.currentTimeMillis();
        }

        public long getLastActivity() {
            return lastActivity;
        }

        public String getUsername() {
            return username;
        }
    }
}