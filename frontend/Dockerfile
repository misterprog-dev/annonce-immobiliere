FROM node:12.14-alpine AS build
WORKDIR /usr/src/app
COPY package.json ./
RUN apk add --update python make g++\
    && rm -rf /var/cache/apk/*  \
    && npm install

# apt-get update \
#     && apt-get install python2-minimal \
#     && apt-get install build-essential \
#     &&

COPY . .
RUN npm run build

FROM nginx:1.17.1-alpine
COPY default.conf /etc/nginx/conf.d/default.conf
COPY --from=build /usr/src/app/dist/annonceimmobiliere /usr/share/nginx/html
EXPOSE 80

