FROM nginx:alpine

WORKDIR /usr/share/nginx/html

RUN rm -rf ./*

COPY dist ./

# TODO : maybe use sites-enabled and do not map port 80
COPY ./nginx-static.conf /etc/nginx/nginx.conf
