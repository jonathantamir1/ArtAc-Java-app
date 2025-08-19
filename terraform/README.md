# Terraform Infrastructure as Code

This directory contains Terraform configuration to automatically provision AWS infrastructure for the Chatbot application.

## Prerequisites 

1. **Terraform** installed (version >= 1.0)
2. **AWS CLI** configured with appropriate credentials
3. **AWS Key Pair** created in your AWS account
4. **Docker image** pushed to Docker Hub

## Quick Start

1. **Copy the example variables file:**
   ```bash
   cp terraform.tfvars.example terraform.tfvars
   ```

2. **Edit terraform.tfvars** with your values:
   ```hcl
   aws_region = "us-east-1"
   key_pair_name = "your-actual-key-pair-name"
   chatbot_api_key = "your-actual-gemini-api-key"
   ```

3. **Initialize Terraform:**
   ```bash
   cd terraform
   terraform init
   ```

4. **Plan the deployment:**
   ```bash
   terraform plan
   ```

5. **Apply the configuration:**
   ```bash
   terraform apply
   ```

## What Gets Created

- **Security Group** with ports 22 (SSH), 8080 (Chatbot), 8081 (Jenkins)
- **EC2 Instance** running Amazon Linux 2
- **Docker** automatically installed and configured
- **Chatbot Container** automatically deployed and running on port 8080

## Outputs

After successful deployment, Terraform will output:
- Instance ID
- Public IP address
- Public DNS name
- SSH command to connect

## Access Your Application

- **Chatbot App**: `http://<public-ip>:8080`
- **SSH Access**: `ssh -i your-key.pem ec2-user@<public-ip>`

## Clean Up

To destroy all resources:
```bash
terraform destroy
```

## Security Notes

- Ports 22, 8080, and 8081 are open to the internet (0.0.0.0/0)
- Consider restricting SSH access to your IP address in production
- The chatbot API key is stored as a sensitive variable
