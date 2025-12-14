FROM node:24.9.0-alpine

WORKDIR /app/client

COPY . .
RUN npm install
EXPOSE 5000

ENTRYPOINT ["npm", "run", "dev", "--host", "0.0.0.0"]