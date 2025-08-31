ARG NODE_VERSION=24.0.2-alpine

FROM node:${NODE_VERSION} AS builder
WORKDIR /app
COPY . /app
RUN npm install -g @angular/cli
RUN npm install
CMD ["ng", "serve", "--host", "0.0.0.0"]