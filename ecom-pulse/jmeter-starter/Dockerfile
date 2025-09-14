FROM python:3.9-slim
WORKDIR /app
COPY . .
RUN pip install Flask docker-compose flasgger
CMD ["python", "app.py"]