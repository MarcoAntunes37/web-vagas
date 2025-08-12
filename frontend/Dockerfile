FROM node:22-alpine AS build

WORKDIR /app

COPY . .

RUN npm install && npm run build

FROM nginx:alpine

COPY --from=builder /app/dist/login-page/browser /usr/share/nginx/html

COPY /flashvagas/docker/nginx.conf /etc/nginx/nginx.conf

COPY /flashvagas/docker/mime.types /etc/nginx/mime.types

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]