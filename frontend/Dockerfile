FROM node:lts AS development


WORKDIR /root

COPY public/ public/
COPY src/ src/
COPY package.json package.json

RUN npm install


EXPOSE 3000
ENTRYPOINT [ "npm", "start" ]
