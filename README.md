# Chatbot Application

A secure Spring Boot web application that provides a chatbot interface using Google's Gemini API.

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

### 5. Docker (optional)
Build and run locally with Docker:
```bash
docker build -t chatbot-app:local .
docker run -p 8080:8080 -e CHATBOT_API_KEY="$CHATBOT_API_KEY" chatbot-app:local
```

### 6. CI/CD with Jenkins
This repo includes a `Jenkinsfile` that:
- Checks out code
- Builds and tests with Maven inside a container
- Builds a Docker image and pushes to Docker Hub
- Deploys to an EC2 instance via SSH using `scripts/deploy.sh`

Required Jenkins credentials:
- `dockerhub-creds`: Docker Hub username/password
- `ec2-ssh-key`: SSH private key for EC2 (Username often `ec2-user` or `ubuntu`)
- `CHATBOT_API_KEY`: Secret text containing your Gemini API key

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

## Security Considerations

1. **API Key Security**: Never commit API keys to version control
2. **Input Validation**: All user inputs are validated and sanitized
3. **HTTPS**: Configure SSL/TLS for production
4. **Headers**: Security headers are configured to prevent common attacks
5. **Rate Limiting**: Consider implementing rate limiting for production use


