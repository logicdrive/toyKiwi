FROM python:3


WORKDIR /root

COPY app/ ./app/
COPY envs/development.env ./.flaskenv

COPY requirements.txt ./requirements.txt
RUN pip install --no-cache-dir -r requirements.txt


EXPOSE 8085

CMD python -m flask run
