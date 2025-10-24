# Ecom-Pulse: Microservice Simulation for AI-Driven Issue Detection

Ecom-Pulse is a simulated e-commerce microservice application designed to generate realistic metrics and logs. This project serves as a practical environment for demonstrating an AI-powered issue detection system across distributed microservices.

## Project Overview

Ecom-Pulse mimics a basic e-commerce workflow, involving order creation, inventory management, and payment processing. Each core function is handled by a dedicated microservice, allowing for independent development, deployment, and scaling. The services are instrumented to expose Prometheus metrics, enabling comprehensive monitoring and data collection for AI analysis.

## Microservices

### 1. Order Service (`order-service`)
-   **Description:** Handles the creation and processing of customer orders.
-   **Port:** `8080`
-   **Key Functionality:** Receives order requests, interacts with the Inventory and Payment services to fulfill orders.
-   **Swagger UI:** `http://localhost:8080/swagger-ui.html`

### 2. Inventory Service (`inventory-service`)
-   **Description:** Manages product stock and reserves items for orders.
-   **Port:** `8081`
-   **Key Functionality:** Reserves inventory based on order requests.
-   **Swagger UI:** `http://localhost:8081/swagger-ui.html`

### 3. Payment Service (`payment-service`)
-   **Description:** Processes payments for orders.
-   **Port:** `8082`
-   **Key Functionality:** Simulates payment gateway interactions.
-   **Swagger UI:** `http://localhost:8082/swagger-ui.html`

## Swagger API Documentation

All microservices in this project include Swagger UI for API documentation and testing. You can access the Swagger UI for each service at the following URLs:

-   **Order Service:** `http://localhost:8080/swagger-ui.html`
-   **Inventory Service:** `http://localhost:8081/swagger-ui.html`
-   **Payment Service:** `http://localhost:8082/swagger-ui.html`

## Chaos Simulation API

To test the resilience and observability of the system, the `inventory-service` and `payment-service` include a Chaos Simulation API. This API allows you to simulate scenarios with increased latency and error rates.

### How to Use

You can enable and disable the chaos simulation by sending POST requests to the `/chaos/enable` and `/chaos/disable` endpoints on each service.

**To enable a 500ms delay and a 20% error rate on the `inventory-service`:**
```bash
curl -X POST -H "Content-Type: application/json" \
-d '{"latency": 500, "errorRate": 0.2}' \
http://localhost:8081/chaos/enable
```

**To disable the simulation on the `inventory-service`:**
```bash
curl -X POST http://localhost:8081/chaos/disable
```

You can use the same commands for the `payment-service` by changing the port to `8082`.

**Note:** You will need to rebuild the services for these changes to be available.

## Testing with JMeter

The project includes a JMeter test plan (`jmeter/create_order.jmx`) to simulate user traffic and generate requests to the microservices. This helps in creating a dynamic environment for collecting performance metrics and logs.

## Running Load Tests with JMeter

The `jmeter-starter` service provides an API to control and execute JMeter load tests. The tests will run continuously until explicitly stopped.

### API Endpoints

The `jmeter-starter` API is accessible at `http://localhost:5001`.

#### 1. Set the Number of Users

Sets the number of concurrent users (threads) for the JMeter test.

*   **Endpoint:** `POST /set-users`
*   **Request Body:** `{"num_users": <integer>}`

**Example:**
```bash
curl -X POST -H "Content-Type: application/json" \
-d '{"num_users": 10}' \
http://localhost:5001/set-users
```

#### 2. Set Throughput (Users per Second)

Sets the target throughput for the JMeter test in users per second.

*   **Endpoint:** `POST /set-throughput`
*   **Request Body:** `{"users_per_second": <number>}`

**Example:**
```bash
curl -X POST -H "Content-Type: application/json" \
-d '{"users_per_second": 5}' \
http://localhost:5001/set-throughput
```

#### 3. Start the JMeter Test

Starts the JMeter test in the background. The test will run continuously until stopped.

*   **Endpoint:** `POST /start-jmeter`

**Example:**
```bash
curl -X POST http://localhost:5001/start-jmeter
```

#### 4. Stop the JMeter Test

Stops any currently running JMeter test.

*   **Endpoint:** `POST /stop-jmeter`

**Example:**
```bash
curl -X POST http://localhost:5001/stop-jmeter
```

### Using the `run-jmeter-test.sh` Script

A convenient shell script `run-jmeter-test.sh` is provided to simplify the process of running a load test. This script takes the number of users as an argument, sets the number of users, and starts the JMeter test.

**Usage:**

```bash
bash run-jmeter-test.sh <num_users>
```

**Example:**

To run a load test with 20 users, you can use the following command:

```bash
bash run-jmeter-test.sh 20
```

## Monitoring and Logging

The Ecom-Pulse application includes a comprehensive monitoring and logging stack, providing deep insights into the health and performance of the microservices. This stack is built on industry-standard tools: Prometheus, Grafana, Loki, and Promtail.

### Monitoring with Prometheus

Prometheus is a powerful open-source monitoring and alerting toolkit that collects and stores its metrics as time series data. It is configured to scrape metrics from all microservices, which are exposed via the Spring Boot Actuator.

-   **Prometheus Dashboard:** Accessible at `http://localhost:9090`

### Log Aggregation with Loki and Promtail

Loki is a horizontally scalable, highly available, multi-tenant log aggregation system inspired by Prometheus. It is designed to be very cost-effective and easy to operate.

Promtail is the agent that ships logs from the local machine to the Loki instance. It is configured to discover and tail logs from all the Docker containers running on the host.

The logging pipeline works as follows:
1.  The microservices write logs to `stdout` and `stderr`.
2.  The Docker logging driver captures these logs.
3.  Promtail scrapes the logs from Docker, adds labels (such as the container name), and sends them to Loki.
4.  Loki indexes the metadata (labels) and stores the compressed log data.

### Visualization with Grafana

Grafana is an open-source platform for monitoring and observability that allows you to query, visualize, alert on, and understand your metrics and logs no matter where they are stored.

Grafana is pre-configured with two data sources:
-   **Prometheus:** For visualizing the application metrics.
-   **Loki:** For querying and visualizing the application logs.

A pre-built dashboard is included to provide a unified view of both metrics and logs, allowing for easy correlation between them.

-   **Grafana Dashboard:** Accessible at `http://localhost:3000`
    -   **Default Credentials:** `admin`/`admin` (you will be prompted to change the password on first login)

## How to Run the Project

To get Ecom-Pulse up and running, ensure you have Docker and Docker Compose installed on your system.

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/mahendra-cs/ecom-pulse.git
    cd ecom-pulse
    ```
2.  **Build and start the services:**
    ```bash
    docker-compose -f ecom-pulse/docker-compose.yml up --build -d
    ```
    This command will:
    -   Build Docker images for all microservices.
    -   Start the Order, Inventory, and Payment services.
    -   Start Prometheus for metrics collection.
    -   Start Grafana for metrics visualization.
    -   Start Loki and Promtail for log aggregation.
    -   Start JMeter for traffic simulation.

3.  **Access Dashboards:**
    -   Prometheus: `http://localhost:9090`
    -   Grafana: `http://localhost:3000`

## CI/CD with Jenkins

This project includes a `Jenkinsfile` to automate the build, test, and deployment of the application. The Jenkins setup is defined in the `jenkins-setup` directory.

### Jenkins Setup

The `jenkins-setup` directory contains:
-   `Jenkinsfile`: The Jenkins pipeline definition.
-   `jenkins.yaml`: A configuration file for Jenkins Configuration as Code (JCasC), which automatically creates the pipeline job in Jenkins.

### Running Jenkins

You can start a pre-configured Jenkins instance using the `azure-deploy.sh` script, or by running Jenkins in Docker and pointing it to the `jenkins.yaml` file.

## Deployment to Azure using cloud-init

For automated deployment to an Azure VM, you can use the provided scripts.

### Deploying the Application (`azure-deploy-app.sh`)

The `azure-deploy-app.sh` script automates the deployment of the Ecom-Pulse application on a Debian/Ubuntu-based Azure VM.

**How to use `azure-deploy-app.sh`:**

1.  **Create a new Linux Azure VM.**
2.  In the "Advanced" tab, paste the content of `azure-deploy-app.sh` into the "User data" text area.
3.  Configure the network security group to allow inbound traffic on the application ports.

### Deploying Jenkins (`azure-deploy.sh`)

The `azure-deploy.sh` script automates the setup of a pre-configured Jenkins instance on a Debian/Ubuntu-based Azure VM.

**How to use `azure-deploy.sh`:**

1.  **Create a new Linux Azure VM.**
2.  In the "Advanced" tab, paste the content of `azure-deploy.sh` into the "User data" text area.
3.  Configure the network security group to allow inbound traffic on port `8080` for the Jenkins UI.