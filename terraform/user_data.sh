#!/bin/bash
set -e

# Update system
yum update -y

# Install Docker
yum install -y docker
systemctl start docker
systemctl enable docker
usermod -a -G docker ec2-user

# Install Docker Compose (optional, for future use)
curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose

# Wait for Docker to be ready
sleep 10

# Pull and run the chatbot application
docker pull ${docker_image}:${docker_tag}

# Stop any existing container
docker stop chatbot-app || true
docker rm chatbot-app || true

# Run the chatbot container
docker run -d \
  --name chatbot-app \
  --restart unless-stopped \
  -p 8080:8080 \
  -e CHATBOT_API_KEY="${api_key}" \
  ${docker_image}:${docker_tag}

# Create a simple health check script
cat > /home/ec2-user/health_check.sh << 'EOF'
#!/bin/bash
if curl -f http://localhost:8080/ > /dev/null 2>&1; then
    echo "Chatbot app is running on port 8080"
else
    echo "Chatbot app is not responding"
    exit 1
fi
EOF

chmod +x /home/ec2-user/health_check.sh

# Log the deployment
echo "Chatbot application deployed successfully on $(date)" >> /var/log/chatbot-deployment.log
echo "Docker image: ${docker_image}:${docker_tag}" >> /var/log/chatbot-deployment.log
echo "Container name: chatbot-app" >> /var/log/chatbot-deployment.log
echo "Port: 8080" >> /var/log/chatbot-deployment.log
