FROM node:18-alpine AS builder

WORKDIR /app

COPY package*.json .
RUN npm install --omit=dev
RUN pwd
COPY . .
RUN npm run build --development
RUN ls -alt build

FROM nginx:1.21.0-alpine

RUN rm -rf /usr/share/nginx/html/

COPY --from=builder /app/build/ /usr/share/nginx/html
COPY --from=builder /app/nginx-custom.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
# Start nginx
CMD ["nginx", "-g", "daemon off;"]
