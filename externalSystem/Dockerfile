FROM python:3

WORKDIR /root


RUN apt update
RUN apt install ffmpeg -y


COPY app/ app/
COPY envs/development.env .flaskenv

COPY requirements.txt requirements.txt
RUN pip install --no-cache-dir -r requirements.txt


EXPOSE 8085

ENTRYPOINT python -m flask run --host=0.0.0.0
