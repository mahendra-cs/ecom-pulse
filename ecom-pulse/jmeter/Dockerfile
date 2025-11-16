FROM eclipse-temurin:11-jdk-slim

# Install JMeter
RUN apt-get update && apt-get install -y curl unzip && \
    mkdir /jmeter && \
    cd /jmeter && \
    curl -L -o apache-jmeter.tgz https://archive.apache.org/dist/jmeter/binaries/apache-jmeter-5.4.1.tgz && \
    tar -xzf apache-jmeter.tgz && \
    rm apache-jmeter.tgz

# Add JMeter to the PATH
ENV PATH="/jmeter/apache-jmeter-5.4.1/bin:${PATH}"

# Copy the JMeter test plan
COPY create_order.jmx /jmeter/

# Run the JMeter test
COPY run-jmeter.sh /jmeter/
RUN chmod +x /jmeter/run-jmeter.sh
CMD ["/jmeter/run-jmeter.sh"]
