# Deployment Guide

This guide covers both manual deployment and automated deployment using Terraform.

## 🚀 Automated Deployment with Terraform (Recommended)

### Prerequisites
1. **Terraform** installed (version >= 1.0)
2. **AWS CLI** configured with credentials
3. **AWS Key Pair** created in your AWS account
4. **Docker image** pushed to Docker Hub

### Quick Start
```bash
# Navigate to terraform directory
cd terraform

# Copy and configure variables
cp terraform.tfvars.example terraform.tfvars
# Edit terraform.tfvars with your values

# Initialize and deploy
terraform init
terraform plan
terraform apply
```

### What Gets Created
- **EC2 Instance**: Amazon Linux 2 with t2.micro
- **Security Group**: Ports 22 (SSH), 8080 (Chatbot), 8081 (Jenkins)
- **Docker**: Automatically installed and configured
- **Chatbot Container**: Running on port 8080 with your API key

### Access Your Application
- **Chatbot App**: `http://<terraform-output-public-ip>:8080`
- **SSH Access**: Use the SSH command from terraform output

## 🔧 Manual Deployment

### Option 1: Direct EC2 Deployment
1. **Launch EC2 instance** (Amazon Linux 2 recommended)
2. **Configure security group** to allow ports 22, 8080, 8081
3. **SSH to instance** and run:
   ```bash
   # Install Docker
   sudo yum update -y
   sudo yum install -y docker
   sudo systemctl start docker
   sudo systemctl enable docker
   sudo usermod -a -G docker ec2-user
   
   # Deploy chatbot
   docker run -d \
     --name chatbot-app \
     --restart unless-stopped \
     -p 8080:8080 \
     -e CHATBOT_API_KEY="your-api-key" \
     jamir7/chatbot-app:latest
   ```

### Option 2: Jenkins Pipeline Deployment
1. **Configure Jenkins job** with your EC2 instance IP
2. **Set environment variables**:
   - `EC2_HOST`: Your EC2 public IP
   - `DOCKER_IMAGE`: `jamir7/chatbot-app`
3. **Run pipeline** - it will automatically deploy to EC2

## 🌐 Port Configuration

| Service | Port | Purpose |
|---------|------|---------|
| SSH | 22 | Server access |
| Chatbot App | 8080 | Main application |
| Jenkins | 8081 | CI/CD pipeline |

## 🔐 Security Considerations

- **SSH Access**: Consider restricting to your IP address
- **API Keys**: Store securely in Jenkins credentials
- **Docker Images**: Use trusted base images
- **Network**: Only expose necessary ports

## 📊 Monitoring and Health Checks

### Health Check Script
The Terraform deployment includes a health check script:
```bash
/home/ec2-user/health_check.sh
```

### Logs
- **Application logs**: `docker logs chatbot-app`
- **Deployment logs**: `/var/log/chatbot-deployment.log`
- **System logs**: `journalctl -u docker`

## 🧹 Cleanup

### Terraform Cleanup
```bash
cd terraform
terraform destroy
```

### Manual Cleanup
```bash
# Stop and remove container
docker stop chatbot-app
docker rm chatbot-app

# Remove image (optional)
docker rmi jamir7/chatbot-app:latest
```

## 🆘 Troubleshooting

### Common Issues
1. **Port already in use**: Check what's running on port 8080
2. **Docker not starting**: Ensure Docker service is enabled
3. **Container not responding**: Check container logs and health
4. **SSH connection failed**: Verify security group and key pair

### Debug Commands
```bash
# Check container status
docker ps -a

# View container logs
docker logs chatbot-app

# Check port usage
sudo netstat -tlnp | grep :8080

# Test application
curl http://localhost:8080/
```

## 📚 Additional Resources

- [Terraform Documentation](https://www.terraform.io/docs)
- [Docker Documentation](https://docs.docker.com/)
- [AWS EC2 Documentation](https://docs.aws.amazon.com/ec2/)
- [Spring Boot Deployment](https://spring.io/guides/gs/spring-boot/)
