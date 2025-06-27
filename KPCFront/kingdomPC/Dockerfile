#Build

FROM node:18 AS builder
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build


#nginx
FROM nginx:alpine
COPY --from=builder /app/build /usr/share/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
