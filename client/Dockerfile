FROM node:24-alpine

WORKDIR /app/client

COPY package*.json .
RUN npm install
COPY . .
EXPOSE 80

ENTRYPOINT ["npm", "run", "dev"]