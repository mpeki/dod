# ---------- Builder stage ----------
# Pin the exact Node patch you’ve standardised on
ARG NODE_VERSION=24.3.0
FROM node:${NODE_VERSION}-alpine AS builder

# Faster, repeatable installs
WORKDIR /app
COPY package*.json ./
RUN npm ci

# Copy the rest only after deps are cached
COPY . .
RUN npm run build   # outputs static files in /app/build

# ---------- Runtime stage ----------
FROM nginx:1.27.1-alpine

# Clean default HTML & copy our build
RUN rm -rf /usr/share/nginx/html/*
COPY --from=builder /app/build /usr/share/nginx/html
COPY --from=builder /app/nginx-custom.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
HEALTHCHECK CMD wget -qO- http://localhost:80 || exit 1
CMD ["nginx","-g","daemon off;"]
