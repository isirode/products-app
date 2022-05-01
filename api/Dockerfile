FROM openjdk:16-alpine3.13

# TODO : run the Java application as a service ? Or use Docker / Pod restart

ARG VERSION
ARG SRC_DATA_FOLDER
ARG WORKING_DIR

WORKDIR ${WORKING_DIR}

COPY /build/libs/api-${VERSION}-all.jar api-all.jar

# TODO : Make "products-data" an arg
COPY ${SRC_DATA_FOLDER} products-data

# TODO : make it configurable or make it not overridable
ENV DATA_FOLDER_PATH=${WORKING_DIR}/products-data

# TODO : it would be great to indicate the version but ARG not usable in CMD and it would not make sense for ENV
CMD ["java", "-jar", "api-all.jar"]