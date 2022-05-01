
## Docker compose

TODO : # Check if need to specify network https://github.com/devops-java/docker-compose-1/blob/main/docker-compose.yml

TODO : check secrets https://stackoverflow.com/questions/22651647/docker-and-securing-passwords

TODO : check why i cant pass ORIENTDB_ROOT_PASSWORD: '${ORIENTDB_ROOT_PASSWORD}'

## API

// https://www.baeldung.com/java-jsonnode-get-keys
// https://github.com/forward/sql-parser
// https://github.com/JavaScriptor/js-sql-parser

## Deploy

### Nginx

// TODO : use a container
// Dont know if container will have access to local machine port used by other containers
// Check out localhost network (not the best for security)
// Check out port-forwarding maybe (I dont think it's made for that)
// Or equivalent of host.docker.internal in podman
// Or https://stackoverflow.com/questions/66290862/kubernetes-pod-talking-to-a-localhost-port
//      headless service & endpoint
// Or
//      --network slirp4netns:allow_host_loopback=true
//      Can access via 10.0.2.2 after
// See https://kubernetes.io/docs/tasks/network/customize-hosts-file-for-pods/
// See https://github.com/containers/podman-compose/issues/316
// See running pod avec network option


