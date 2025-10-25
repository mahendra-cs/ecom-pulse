FROM jenkins/jenkins:lts

# Switch to root to install Maven
USER root

# Install Maven
RUN apt-get update && apt-get install -y maven

# Install Docker Compose
RUN apt-get update && apt-get install -y python3-pip
RUN pip install docker-compose

# Switch back to jenkins user
USER jenkins

# Install required plugins
COPY --chown=jenkins:jenkins plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt