FROM jenkins/jenkins:lts

# Switch to root to install Maven
USER root

# Install Maven
RUN apt-get update && apt-get install -y maven

# Install Docker Compose (using apt for docker-compose-plugin)
RUN apt-get update && apt-get install -y docker-compose-plugin

# Install Docker client and add jenkins user to docker group
RUN apt-get update && apt-get install -y docker.io
RUN usermod -aG docker jenkins

# Switch back to jenkins user
USER jenkins

# Install required plugins
COPY --chown=jenkins:jenkins plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt