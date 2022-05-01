# API

## Run locally

### Using a Docker instance of OrientDB and an IDE

1. Run the OrientDB instance:

> docker run -d -p 2424:2424 -p 2480:2480 -e ORIENTDB_ROOT_PASSWORD=dev_password65 --name orientdb-products orientdb

2. Run the application as usual inside the IDE of your choice, you will need to reset the container or not recreate the data & schema after each run.

### Using Docker compose

You can use Docker compose like this, you need to have build the Docker image first :

> docker-compose --env-file ./config/.env.dev up -d

I am not using Docker compose in production, but you could define another environment file and use those commands :

> docker-compose --env-file ./config/.env.prod up -d
> docker-compose --env-file ./config/.env.test up -d

You can tear down the Docker compose container:

> docker-compose --env-file ./config/.env.dev down -d

It is also possible to push using Docker compose:

> docker login etc
> docker-compose push

## Build JAR

You can use the usual Gradle build tasks.

To make a Jar:

> gradle api:jar

To make a fatJar, I am using [shadow](https://github.com/johnrengelman/shadow), the command is:

> gradle api:shadowJar

## Build Docker

Once you have build the JAR, you can create the Docker image:

I am storing the data at the same level as the app, but Docker cannot access an upper ('..') directory, so we copy it here, first:

> cp -r ../../products-data products-data

Build the Image using the correct build args:

> docker build --tag localhost:5000/products-api:0.0.1 --build-arg VERSION=0.0.1 --build-arg SRC_DATA_FOLDER=products-data --build-arg WORKING_DIR=/usr/local/share/java/products-api .

Or in one go by separating the two commands by "&&".

> cp -r ../../products-data products-data && docker build --tag etc...

Most IDEs allow to run the build with a prompt for the arguments, also.

## Deploy

The submodule [deploy](../deploy/README.md) is used to deploy the web application to a VPS server.

It also contains a Pod definition.
