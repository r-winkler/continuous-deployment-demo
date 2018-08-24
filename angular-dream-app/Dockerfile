##### Stage 1
FROM node:latest as node
LABEL author="René Winkler"
WORKDIR /app
COPY package.json package.json
RUN npm install
COPY . .
RUN npm run build -- --prod

##### Stage 2
FROM nginx:alpine
ARG PROJECT_NAME_FRONTEND
VOLUME /var/cache/nginx
COPY --from=node /app/dist/${PROJECT_NAME_FRONTEND} /usr/share/nginx/html
COPY ./config/nginx.conf /etc/nginx/conf.d/default.conf


# docker build --build-arg PROJECT_NAME_FRONTEND=angular-dream-app -t nginx-angular .
# docker run -p 8082:80 nginx-angular
