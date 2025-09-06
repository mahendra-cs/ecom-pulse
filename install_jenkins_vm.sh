#!/bin/bash

# Update package list
sudo apt update -y

# --- Install Docker if not installed ---
if ! command -v docker &> /dev/null
then
    echo "Docker not found, installing..."
    sudo apt install -y ca-certificates curl gnupg lsb-release
    sudo mkdir -p /etc/apt/keyrings
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
    echo \
      "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
      $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
    sudo apt update -y
    sudo apt install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin
    
    # Add current user to docker group to run docker commands without sudo
    sudo usermod -aG docker $USER
    echo "Docker installed. Please log out and log back in for docker group changes to take effect, or run 'newgrp docker'."
    # For the script to continue, we'll use sudo for docker commands or assume newgrp docker is run.
    # In a real scenario, you might want to exit and re-run the script after re-login.
fi

# --- Create directories for Jenkins data and JCasC config on the host ---
JENKINS_HOME_DIR="/var/jenkins_home"
CASC_CONFIGS_DIR="/var/jenkins_casc_configs"
PLUGINS_DIR="/var/jenkins_plugins"
sudo mkdir -p ${JENKINS_HOME_DIR}
sudo mkdir -p ${CASC_CONFIGS_DIR}
sudo mkdir -p ${PLUGINS_DIR}

# Set appropriate permissions for Jenkins home (important for Docker volume)
sudo chown -R 1000:1000 ${JENKINS_HOME_DIR} # Jenkins user ID is typically 1000 inside the container

# --- Create plugins.txt for JCasC plugin installation ---
echo "configuration-as-code" | sudo tee ${PLUGINS_DIR}/plugins.txt > /dev/null

# --- Download jenkins.yaml from GitHub to the host JCasC config directory ---
JENKINS_YAML_URL="https://raw.githubusercontent.com/mahendra-cs/ecom-pulse/main/jenkins-setup/jenkins.yaml"
sudo curl -o ${CASC_CONFIGS_DIR}/jenkins.yaml "${JENKINS_YAML_URL}"

# --- Run Jenkins in a Docker container ---
# Stop and remove any existing Jenkins container
sudo docker stop jenkins &> /dev/null
sudo docker rm jenkins &> /dev/null

echo "Pulling Jenkins Docker image..."
sudo docker pull jenkins/jenkins:lts

echo "Running Jenkins Docker container..."
sudo docker run \
  -d \
  -p 8080:8080 \
  -p 50000:50000 \
  -v ${JENKINS_HOME_DIR}:/var/jenkins_home \
  -v ${CASC_CONFIGS_DIR}:/usr/share/jenkins/ref/casc_configs \
  -v ${PLUGINS_DIR}/plugins.txt:/usr/share/jenkins/ref/plugins.txt \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -e CASC_JENKINS_CONFIG=/usr/share/jenkins/ref/casc_configs/jenkins.yaml \
  --name jenkins \
  --restart unless-stopped \
  jenkins/jenkins:lts

echo "Jenkins Docker container started."
echo "You can access Jenkins at http://YOUR_VM_IP:8080"
echo "To get the initial admin password, run: sudo docker logs jenkins"