# Secure Chatbot Application

A secure Spring Boot web application that provides a chatbot interface using Google's Gemini API.

## Security Features

- Spring Security configuration with secure headers
- Input validation and sanitization
- API key management through environment variables
- CSRF protection considerations
- Secure cookie configuration
- Rate limiting considerations

## Setup Instructions

### 1. Clone the Repository
```bash
git clone <your-repo-url>
cd chatbot-app
```

### 2. Set Environment Variables
**IMPORTANT: Never commit your API key to Git!**

#### For Local Development:
Create a `.env` file in the project root (this file is gitignored):
```bash
CHATBOT_API_KEY=your-actual-api-key-here
```

Or set the environment variable directly:
```bash
export CHATBOT_API_KEY=your-actual-api-key-here
```

#### For Production/Deployment:
Set the environment variable in your deployment platform:
- **Heroku**: `heroku config:set CHATBOT_API_KEY=your-actual-api-key`
- **AWS**: Set in EC2 instance or Lambda environment variables
- **Docker**: Use `-e CHATBOT_API_KEY=your-key` or docker-compose environment section

### 3. Build and Run
```bash
# Using Maven
./mvnw spring-boot:run

# Or build JAR and run
./mvnw clean package
java -jar target/chatbot-app-0.0.1-SNAPSHOT.jar
```

### 4. Access the Application
Open your browser and go to: `http://localhost:8080`

## Project Structure
```
src/
├── main/
│   ├── java/com/example/chatbot/
│   │   ├── ChatbotApplication.java      # Main Spring Boot application
│   │   ├── config/
│   │   │   └── SecurityConfig.java      # Security configuration
│   │   ├── controller/
│   │   │   └── ChatController.java      # Web and API endpoints
│   │   ├── model/
│   │   │   └── ChatMessage.java         # Data model
│   │   └── service/
│   │       └── ChatbotService.java      # Business logic
│   └── resources/
│       ├── application.properties        # Configuration
│       └── templates/
│           └── index.html               # Frontend template
```

## API Endpoints

- `GET /` - Main chat interface
- `POST /api/chat` - Send message to chatbot

## Security Considerations

1. **API Key Security**: Never commit API keys to version control
2. **Input Validation**: All user inputs are validated and sanitized
3. **HTTPS**: Configure SSL/TLS for production
4. **Headers**: Security headers are configured to prevent common attacks
5. **Rate Limiting**: Consider implementing rate limiting for production use

## Deployment Checklist

- [ ] Set `CHATBOT_API_KEY` environment variable
- [ ] Enable HTTPS/SSL
- [ ] Configure proper CORS policies if needed
- [ ] Enable CSRF protection for production
- [ ] Set up monitoring and logging
- [ ] Configure rate limiting
- [ ] Review and test security headers

## Environment Variables

| Variable | Description | Required |
|----------|-------------|----------|
| `CHATBOT_API_KEY` | Google Gemini API key | Yes |
| `SERVER_PORT` | Server port (default: 8080) | No |

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License.
