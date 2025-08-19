variable "aws_region" {
  description = "AWS region to deploy resources"
  type        = string
  default     = "us-east-1"
}

variable "instance_type" {
  description = "EC2 instance type"
  type        = string
  default     = "t3.micro"
}

variable "key_pair_name" {
  description = "Name of the AWS key pair to use for SSH access"
  type        = string
  default     = "chatbot-key"
}

variable "docker_image" {
  description = "Docker image name to deploy"
  type        = string
  default     = "jamir7/chatbot-app"
}

variable "docker_tag" {
  description = "Docker image tag to deploy"
  type        = string
  default     = "latest"
}

variable "chatbot_api_key" {
  description = "Chatbot API key for Gemini"
  type        = string
  sensitive   = true
}


