# Deployment

I am using a VPS to deploy the web application.

This module allow to do this simply.

To use the same system, you need to have setup server:
* [podman](https://podman.io/) installed 
* [Docker registry](https://docs.docker.com/registry/) installed

Locally, you need:
* Docker installed
* The docker daemon exposed without TLS

You can then fill in the environment files:
* [secret application.conf](secret/secret-configuration.example.api.conf) : contains the API secret configuration
* [pod file](pod/products-app.pod.example.yml) : the definition of the pod
* [Nginx API](nginx/products-api-nginx.example.conf) : the Nginx site configuration for the API
* [Nginx Web](nginx/products-web-nginx.example.conf) : the Nginx site configuration for the web-site

## Running it

You dont need to build the Docker images, the CLI will do that for you.

You need to run it inside the IDE of your choice for now.
