# Installation Guide

## Have Docker Installed?

TBD

## Have Node and JDK Installed?

Open up two terminals on either Command Prompt or Powershell if you are on Windows, one for the server and the other for the client. 

Run the following command on the server terminal:

```bash
git clone https://github.com/smm2005/Movie-Catalog.git
dos2unix Movie-Catalog/server/certsgen.sh
cd Movie-Catalog/server
bash certsgen.sh
./mvnw spring-boot:run
```

Run the following command on the client terminal:

```bash
cd Movie-Catalog/client
npm install
npm run dev
```