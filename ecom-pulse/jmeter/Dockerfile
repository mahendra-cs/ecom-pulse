FROM openjdk:17-jdk-alpine

# Install JMeter
RUN apk --no-cache add curl unzip && \
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
CMD ["jmeter", "-n", "-t", "/jmeter/create_order.jmx", "-l", "/jmeter/results/results.jtl"]
