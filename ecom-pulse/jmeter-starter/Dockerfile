FROM python:3.9-slim
WORKDIR /app
COPY . .
RUN pip install Flask docker-compose
CMD ["python", "app.py"]