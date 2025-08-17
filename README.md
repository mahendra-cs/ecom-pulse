# Ecom-Pulse: Microservice Simulation for AI-Driven Issue Detection

Ecom-Pulse is a simulated e-commerce microservice application designed to generate realistic metrics and logs. This project serves as a practical environment for demonstrating an AI-powered issue detection system across distributed microservices.

## Project Overview

Ecom-Pulse mimics a basic e-commerce workflow, involving order creation, inventory management, and payment processing. Each core function is handled by a dedicated microservice, allowing for independent development, deployment, and scaling. The services are instrumented to expose Prometheus metrics, enabling comprehensive monitoring and data collection for AI analysis.

## Microservices

### 1. Order Service (`order-service`)
-   **Description:** Handles the creation and processing of customer orders.
-   **Port:** `8080`
-   **Key Functionality:** Receives order requests, interacts with the Inventory and Payment services to fulfill orders.

### 2. Inventory Service (`inventory-service`)
-   **Description:** Manages product stock and reserves items for orders.
-   **Port:** `8081`
-   **Key Functionality:** Reserves inventory based on order requests.

### 3. Payment Service (`payment-service`)
-   **Description:** Processes payments for orders.
-   **Port:** `8082`
-   **Key Functionality:** Simulates payment gateway interactions.

## Testing with JMeter

The project includes a JMeter test plan (`jmeter/create_order.jmx`) to simulate user traffic and generate requests to the microservices. This helps in creating a dynamic environment for collecting performance metrics and logs.

## Monitoring with Prometheus

Prometheus is configured to scrape metrics from all microservices. These metrics provide real-time insights into the health and performance of each service, forming the basis for AI-driven anomaly detection.

-   **Prometheus Dashboard:** Accessible at `http://localhost:9090`

## Visualization with Grafana

Grafana is integrated to visualize the metrics collected by Prometheus. It provides dashboards for monitoring service performance, identifying trends, and pinpointing potential issues.

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
    docker-compose up --build -d
    ```
    This command will:
    -   Build Docker images for all microservices.
    -   Start the Order, Inventory, and Payment services.
    -   Start Prometheus for metrics collection.
    -   Start Grafana for metrics visualization.
    -   Start JMeter for traffic simulation.

3.  **Access Dashboards:**
    -   Prometheus: `http://localhost:9090`
    -   Grafana: `http://localhost:3000`

## Deployment to AWS EC2 using Cloud-Init

For automated deployment to an AWS EC2 instance, you can leverage Cloud-Init with the provided `cloud-init-deploy.sh` script. This script automates the installation of Docker and Docker Compose, clones the repository, and starts the services.

**How to use `cloud-init-deploy.sh`:**

1.  **Launch an EC2 Instance:**
    *   Choose an **Amazon Linux 2 AMI** (or a compatible Linux distribution where `yum` and `sudo` commands work as expected).
    *   In the "Configure Instance Details" step, expand "Advanced Details".
    *   Paste the entire content of `cloud-init-deploy.sh` into the "User data" text area.
2.  **Configure Security Group:**
    *   Ensure your EC2 instance's security group allows inbound traffic on the following ports:
        *   `22` (SSH)
        *   `8080`, `8081`, `8082` (Microservices)
        *   `9090` (Prometheus)
        *   `3000` (Grafana)
3.  **Launch and Monitor:**
    *   Launch the instance. Cloud-Init will execute the script on first boot.
    *   Monitor the instance's system logs (via EC2 console) to track the script's progress.
    *   Once the script completes, your services should be running and accessible via the instance's public IP on the configured ports.

**Important Considerations:**

*   **IAM Role:** For enhanced security, consider attaching an IAM role to your EC2 instance with minimal necessary permissions, rather than relying on SSH keys for Git cloning if your repository were private.
*   **`docker-compose` Version:** The script installs Docker Compose version `1.29.2`. You might want to update this to the latest stable version by changing the URL in the script.
*   **User:** The script assumes the default user is `ec2-user` for adding to the `docker` group. Adjust `usermod -a -G docker ec2-user` if your AMI uses a different default user.



