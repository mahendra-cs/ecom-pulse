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
git clone https://github.com/mahendra-cs/ecom-pulse.git /home/$USER/ecom-pulse

# Start Jenkins with JCasC
docker run -d -p 8080:8080 -p 50000:50000 \
-v /home/$USER/ecom-pulse/jenkins-setup/jenkins.yaml:/var/jenkins_home/casc_configs/jenkins.yaml \
-v /var/run/docker.sock:/var/run/docker.sock \
--name jenkins \
jenkinsci/casc-jenkins:lts
