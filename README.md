# ArtAc Home Assignment ChatBot

A Spring Boot web application with an AI-powered chatbot using Google's Gemini API.

## 📋 Prerequisites

Before running this application locally, you need:

### **Required Software**

#### **Java 17+**
```bash
# macOS (using Homebrew)
brew install openjdk@17

# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-17-jdk

# CentOS/RHEL/Amazon Linux
sudo yum install java-17-openjdk-devel

# Windows
# Download from: https://adoptium.net/
```

#### **Maven 3.6+**
```bash
# macOS (using Homebrew)
brew install maven

# Ubuntu/Debian
sudo apt update
sudo apt install maven

# CentOS/RHEL/Amazon Linux
sudo yum install maven

# Windows
# Download from: https://maven.apache.org/download.cgi
```

### **Required Accounts & Keys**
- **Google Gemini API Key** - [Get one here](https://makersuite.google.com/app/apikey)

### **Optional (for advanced features)**

#### **Docker**
```bash
# macOS
brew install --cask docker

# Ubuntu/Debian
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh
sudo usermod -aG docker $USER

# CentOS/RHEL/Amazon Linux
sudo yum install -y docker
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker $USER

# Windows
# Download from: https://www.docker.com/products/docker-desktop
```

#### **Git**
```bash
# macOS
brew install git

# Ubuntu/Debian
sudo apt update
sudo apt install git

# CentOS/RHEL/Amazon Linux
sudo yum install git

# Windows
# Download from: https://git-scm.com/downloads
```

## 🚀 Quick Start

### 1. Clone and Setup 
```bash
git clone https://github.com/jonathantamir1/ArtAc-Java-app.git
cd ArtAc-Java-app
```

### 2. Get API Key
1. Visit [Google AI Studio](https://makersuite.google.com/app/apikey)
2. Create an API key
3. Set it as environment variable:
```bash
export CHATBOT_API_KEY="your-api-key-here"
```

### 3. Run the Application
```bash
./mvnw spring-boot:run
```

### 4. Access the Chatbot
Open [http://localhost:8080](http://localhost:8080) in your browser

## 🐳 Docker (Optional)

```bash
docker build -t chatbot-app .
docker run -p 8080:8080 -e CHATBOT_API_KEY="$CHATBOT_API_KEY" chatbot-app
```

## ☁️ AWS Deployment

### Prerequisites
- AWS CLI configured
- Terraform installed

### Deploy
```bash
cd terraform
cp terraform.tfvars.example terraform.tfvars
# Edit terraform.tfvars with your API key
terraform init
terraform apply
```

## 🔄 CI/CD Pipeline

The project includes a Jenkins pipeline that:
- Builds the application
- Runs tests
- Creates Docker image
- Deploys to AWS EC2

## 📁 Project Structure

```
src/main/java/com/example/chatbot/
├── ChatbotApplication.java      # Main application
├── config/SecurityConfig.java   # Security settings
├── controller/ChatController.java # Web endpoints
├── model/ChatMessage.java       # Data model
└── service/ChatbotService.java  # Business logic
```

## 🧪 Testing

```bash
./mvnw test
```

## 🔧 Configuration

Key settings in `application.properties`:
```properties
chatbot.api.key=${CHATBOT_API_KEY}
chatbot.api.url=https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent
```

## 🚨 Common Issues

### API Key Not Working
```bash
export CHATBOT_API_KEY="your-actual-key"
./mvnw spring-boot:run
```

### Port Already in Use
```bash
export SERVER_PORT=8081
./mvnw spring-boot:run
```

## 📚 API Endpoint

- **POST** `/api/chat`
- **Body**: `{"message": "Hello"}`
- **Response**: `{"response": "AI response"}`

---

**Built with Spring Boot, Docker, and AWS**

