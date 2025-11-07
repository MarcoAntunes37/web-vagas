FROM node:22-alpine AS build

WORKDIR /app

COPY . .

RUN npm install && npm run build

FROM nginx:alpine

COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

COPY /nginx/nginx.conf /etc/nginx/nginx.conf

COPY /nginx/mime.types /etc/nginx/mime.types

EXPOSE 80 443

CMD ["nginx", "-g", "daemon off;"]