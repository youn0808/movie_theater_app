FROM python:3.9

COPY . /app

RUN ["python3", "-m", "pip", "install", "-r", "/app/requirements.txt"]
ENTRYPOINT ["python3", "./app/main.py"]