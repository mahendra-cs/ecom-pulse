#!/bin/bash

# Update and install dependencies
sudo apt-get update -y
sudo apt-get install -y docker.io
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker $USER

# Install Docker Compose
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

# Clone the repository
git clone https://github.com/mahendra-cs/ecom-pulse.git

# Navigate to the correct directory
cd ecom-pulse/ecom-pulse

# Build and run the services
docker-compose up --build -d
