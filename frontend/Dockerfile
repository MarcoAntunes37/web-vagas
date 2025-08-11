FROM node:22-alpine AS build

WORKDIR /app

COPY . .

RUN npm install && npm run build

FROM nginx:alpine

RUN apk add --no-cache bash

COPY --from=build /app/dist/frontend /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]