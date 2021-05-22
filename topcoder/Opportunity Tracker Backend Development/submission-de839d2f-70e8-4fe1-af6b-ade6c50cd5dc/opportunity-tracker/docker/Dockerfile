# Use the base image with Node.js
FROM node:14-alpine

# Set working directory. Paths will be relative this WORKDIR.
WORKDIR /usr/src/app

# Install the dependencies from package.json
COPY package*.json ./
RUN npm install

# Copy source files from host computer to the container
COPY . .

ENTRYPOINT [ "npm" ]