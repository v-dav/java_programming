# Real-Time Chat Application

A modern, real-time chat application built with Spring Boot and WebSocket technology, featuring both public chat rooms and private messaging capabilities.
<img width="960" height="1149" alt="Skärmavbild 2025-09-27 kl  12 05 28" src="https://github.com/user-attachments/assets/03f9bbb8-9440-472b-93ed-d802b81cecb6" />
<img width="960" height="1042" alt="Skärmavbild 2025-09-27 kl  12 05 56" src="https://github.com/user-attachments/assets/8113367d-6c65-4d67-819b-37520ac1a2a8" />

## Features

### 🚀 Core Features
- **Real-time Messaging**: Instant message delivery using WebSocket connections
- **Public Chat Room**: Global chat accessible to all connected users
- **Private Messaging**: Secure one-on-one conversations between users
- **User Management**: Real-time user presence and activity tracking
- **Message History**: Persistent storage and retrieval of chat messages
- **Connection Management**: Automatic reconnection and graceful disconnection handling

### 💬 Chat Features
- **Dual Chat Modes**: Seamlessly switch between public and private conversations
- **Message Counters**: Visual indicators for unread private messages
- **User Activity Tracking**: Automatic cleanup of inactive users (30-minute timeout)
- **Message Persistence**: Store up to 1,000 messages per conversation
- **Dynamic User Ordering**: Recently active users appear at the top of the list

### 🎨 User Interface
- **Responsive Design**: Mobile-optimized interface that works on all devices
- **Modern UI**: Clean, professional design with smooth animations
- **Real-time Updates**: Live user list and message updates
- **Intuitive Navigation**: Easy switching between chat modes
- **Visual Feedback**: Message delivery confirmation and typing indicators

### 🔒 Security & Validation
- **Input Validation**: Username and message length restrictions
- **Session Management**: Secure user session handling
- **Connection Security**: WebSocket connection validation
- **Data Sanitization**: Protection against malicious input

### 📱 Mobile Support
- **Touch-Friendly**: Optimized for mobile interactions
- **Responsive Layout**: Adaptive design for different screen sizes
- **Mobile Navigation**: Streamlined interface for mobile devices

## Technology Stack

### Backend
- **Spring Boot 3.2.0**: Main application framework
- **Spring WebSocket**: Real-time communication
- **Spring Web**: REST API endpoints
- **Java 17**

### Frontend
- **HTML5**
- **CSS3**
- **JavaScript (ES6+)**
- **SockJS**: WebSocket fallback support
- **STOMP**: Simple Text Oriented Messaging Protocol

### Build & Development
- **Gradle 8.14**: Build automation and dependency management

## Quick Start

### Prerequisites
- Java 17 or higher
- Gradle 8.x (or use included wrapper)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd real_time_chat
   ```

2. **Build the application**
   ```bash
   ./gradlew build
   ```

3. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

4. **Access the application**

   Open your browser and navigate to: `http://localhost:28852`

### Using the Application

1. **Join the Chat**
    - Enter a username (max 20 characters)
    - Click "Join" to enter the chat room

2. **Public Chat**
    - Send messages visible to all users
    - View real-time user list
    - See message history

3. **Private Chat**
    - Click on any user in the user list
    - Send private messages
    - Switch back to public chat using the "Public Chat" button

## API Endpoints

### REST Endpoints
- `POST /api/join` - Join the chat room
- `POST /api/leave` - Leave the chat room
- `POST /api/private-messages` - Retrieve private message history

### WebSocket Endpoints
- `/chat` - Main WebSocket connection endpoint
- `/app/message` - Send public messages
- `/app/private-message` - Send private messages
- `/app/leave` - Leave chat via WebSocket
- `/topic/messages` - Subscribe to public messages
- `/topic/private-messages` - Subscribe to private messages
- `/topic/users` - Subscribe to user events

## Configuration

### Application Properties
```properties
server.port=28852
```

### Customizable Limits
- Maximum messages per conversation: 1,000
- Maximum username length: 20 characters
- Maximum message length: 1,000 characters
- User inactivity timeout: 30 minutes
- Cleanup interval: 5 minutes

## Project Structure
```
real_time_chat/
├── src/
│   ├── main/
│   │   ├── java/chat/
│   │   │   ├── ChatController.java    # Main chat logic and WebSocket handling
│   │   │   ├── Main.java              # Spring Boot application entry point
│   │   │   └── WebSocketConfig.java   # WebSocket configuration
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── index.html         # Main UI
│   │       │   ├── scripts.js         # Frontend logic
│   │       │   └── styles.css         # Styling and responsive design
│   │       └── application.properties # Application configuration
│   └── test/
│       └── ChatTests.java             # Tests to write
├── gradle/wrapper/                    # Gradle wrapper files
├── build.gradle                       # Build configuration
└── README.md                          # This file
```
## Building for production

```
./gradlew build
java -jar build/libs/Real-Time-Chat.jar
```

## Contributing
- Fork the repository
- Create a feature branch (git checkout -b feature/amazing-feature)
- Commit your changes (git commit -m 'Add some amazing feature')
- Push to the branch (git push origin feature/amazing-feature)
- Open a Pull Request

## License
This project is open source and available under the MIT License.

## Support
For support, questions, or feature requests, please open an issue in the repository.
