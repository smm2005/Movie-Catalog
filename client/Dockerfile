FROM node:24.9.0-alpine

WORKDIR /app/client

COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 5000

CMD ["npm", "run", "dev"]