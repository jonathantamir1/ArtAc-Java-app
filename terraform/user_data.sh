#!/bin/bash
set -e

# Update system
yum update -y

# Install Docker
yum install -y docker
systemctl start docker
systemctl enable docker
usermod -a -G docker ec2-user



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





# Log the deployment
echo "Chatbot application deployed successfully on $(date)" >> /var/log/chatbot-deployment.log
echo "Docker image: ${docker_image}:${docker_tag}" >> /var/log/chatbot-deployment.log
