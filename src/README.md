# Proyectos

En este directorio se encuentran todos los proyectos que conforman los diferentes 
microservicios de Rodar.

* Provider management microservice
    * ```pmmicro``` es el microservicio para la gestión de los proveedores de vehículos.
    * Puerto expuesto: 8080

# Requisitos y configuración

Para el levantamiento del sistema, o de microservicios individuales, se require de Docker.

Todas las bases de datos utilizan el mismo usuario/contraseña, por facilitar las cosas,
que deben definirse como variables de entorno al levantar el sistema con ```docker compose```,

```bash
export DB_USER=myuser
export DB_PASSWORD=mypass
```

Además es necesario configurar Maven para que utilice el archivo ```application-template.properties``` 
para cada proyecto o ejecutar el script ```rename_props.sh``` que renombra 
cada ```application-template.properties``` a ```application.properties```,

```bash
chmod +x rename_props.sh
./rename_props.sh
```

# Ejecución de microservicios individuales

Los diferentes microservicios se pueden arrancar de forma individual a través de la 
construcción de su imagen, por ejemplo con ```pmmicro```,

```bash
# Build con docker
docker build --build-arg ARTIFACT=pmmicro-0.0.2.jar \
    --tag src-pmmicro --file backend.Dockerfile pmmicro

# Levanta el contenedor, donde,
#   server-domain: es la IP o dominio del servidor con la base de datos
#
docker run --detach --name pmmicro src-pmmicro \
    --env "SPRING_DATASOURCE_URL=jdbc:mariadb://server-domain:3306/pmdb?useSSL=false&serverTimezone=UTC" \
    --env "SPRING_DATASOURCE_USERNAME=${DB_USER}" \
    --env "SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD}" \
    --network pmmicro-net \
    --publish 3306:3306
```

# Ejecución completa

La ejecución del sistema en su totalidad, incluida la construcción de las imagenes, 
se realiza con ```docker compose``` de la siguiente forma,

```bash
docker compose up --detach
```

# Swagger

Para cada microservicio, se puede acceder a Swagger a través de la URL,

```
http://container-ip:exposed-port/api/v1/swagger-ui/index.html
```

# SonarQube

```bash
# Levanta un contenedor (embedded database con persistencia en volumen)
docker run --name sonar-inst \
    --volume sonar-data:/opt/sonarqube/data \
    --publish 9000:9000 --detach sonarqube:community
```

```bash
# Ejecuta el analisís con Maven
#
mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
  -Dsonar.projectKey=PROJECT_KEY \
  -Dsonar.projectName='PROJECT_NAME' \
  -Dsonar.host.url=http://CONTAINER-IP:9000 \
  -Dsonar.token=TOKEN
```
