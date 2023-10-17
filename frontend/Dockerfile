FROM node:18.16.1-alpine AS builder

WORKDIR /app

COPY package*.json ./
RUN npm --version && npm install
RUN pwd
COPY . ./
RUN npm run build

FROM nginx:1.25.2-alpine

RUN rm -rf /usr/share/nginx/html/

COPY --from=builder /app/build/ /usr/share/nginx/html
COPY --from=builder /app/nginx-custom.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
# Start nginx
CMD ["nginx", "-g", "daemon off;"]
