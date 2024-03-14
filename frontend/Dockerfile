FROM node:19.9.0-alpine AS builder

WORKDIR /app

COPY package*.json ./
RUN npm --version && npm install
RUN pwd
COPY . ./
RUN npm run build

FROM nginx:1.25.3-alpine

RUN rm -rf /usr/share/nginx/html/

COPY --from=builder /app/build/ /usr/share/nginx/html
COPY --from=builder /app/nginx-custom.conf /etc/nginx/conf.d/default.conf

EXPOSE 80 8081
# Start nginx
CMD ["nginx", "-g", "daemon off;"]
