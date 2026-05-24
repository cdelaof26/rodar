# Rodar

Rodar es una prueba de concepto de un sistema de alquiler de coches con arquitectura basada 
en microservicios, donde cada microservicio ofrece una API RESTful.

El objetivo es implementar tres casos de uso, utilizando SpringBoot como back-end, con 
front-end escrito en React+Vue y TailwindCSS, desplegado en contenedores Docker y con 
algunas limitaciones en el alcance.

## Limitaciones

Para simplificar el desarrollo, 
* No se implementan API Gateway, MessageBroker, réplicas ni balanceadores de carga
* El front-end provider se unifica con el UI
* Los microservicios de impuestos/pagos ~~y notificaciones~~ serán tratados como componentes simulados que siempre tendrán respuestas fijas _favorables_
* Todas las bases de datos serán SQL
* Solo se implementa el flujo básico como se detalla en los diagramas de secuencia
* No se implementa nada relacionado con la geolocalización

## Documentos

En el directorio ```src``` se encuentran los diagramas de secuencia y modelo de dominio
que pueden renderizarse con PlantUML y, además, los casos de uso.

Por otra parte, en ```rendered```, se encuentra la arquitectura del sistema y 
los diagramas anteriores en formato ```svg```.

**Nota**: _Los diagramas de secuencia son ligeramente diferentes en algunos flujos a los entregados._

## Ejecución

La información necesaria para poner en marcha ```rodar```, se encuentrá en el fichero
[src/README.md](src/README.md)


## Microservicios y endpoints

<details>
    <summary><b>Provider management microservice (pmmicro) since v0.0.2</b></summary>

#### Dependencias

- N/A

#### Endpoints

<pre>
    POST /providers
        Crea un nuevo proveedor

    GET  /providers/{providersId}
        Obtiene la información de un proveedor
        - Casos de uso: 1

    GET  /providers  # Testing
        Obtiene todos los proveedores
</pre>
</details>


<details>
    <summary><b>Vehicles search microservice (vsmicro) since v0.0.9</b></summary>

#### Dependencias

- [x] pmmicro
- [x] vimicro
- [x] tmicro

#### Endpoints

<pre>
    ~~POST /vehicles~~

    GET  /vehicles?location=&startDate=&endDate=&types=&prices=&currency=
        Obtiene una lista de vehículos dados ciertos parámetros de busqueda
        - Caso de uso: 1

    GET  /vehicles/{vehicleId}
        Obtiene los detalles de un vehículo en particular
        - Caso de uso: 2
</pre>

<b>Nota</b>: la operación POST corresponde al endpoint privado que accede 
Vehicles microservice a través de un ```Message Broker``` para nuevas 
entradas. Por simplificar la implementación, se utiliza directamente.
</details>


<details>
    <summary><b>Vehicles inventory microservice (vimicro) since v0.0.4</b></summary>

#### Dependencias

- N/A

#### Endpoints

<pre>
    POST /vehicles
        Puede crear una nueva entrada de inventario y registra las placas de un vehículo

    GET  /vehicles?startDate=&endDate=
        Obtiene todos los vehículos disponibles dado un rango de fechas
        - Casos de uso: 1

    GET  /vehicles/{vehicleId}?startDate=&endDate=
        Obtiene los datos de disponibilidad un vehículo
        - Casos de uso: 2

    PUT  /vehicles/{vehicleId}
        Actualiza el estado 'in_use' y/o agrega fechas/usuario a un vehículo
        - Casos de uso: 3
</pre>
</details>


<details>
    <summary><b>Taxes microservice (tmicro) since v0.0.6</b></summary>

#### Dependencias

- N/A

#### Endpoints

<pre>
    GET /currencies/{c}/rates?to=&amount=&location=
        Realiza la conversión de una divisa a otra
        - Casos de uso: 1, 2

    GET /vehicle/rate/{vehicleId}?location=
        Se eliminó este endpoint a favor de la utilización del anterior
</pre>
</details>


<details>
    <summary><b>Reservation microservice (remicro) since v0.1.1</b></summary>

#### Dependencias

- [x] pmmicro
- [x] vimicro
- [x] tmicro
- [x] pmicro (UC2)
- [x] vsmicro

#### Endpoints

<pre>
    GET  /reservations  # Testing
        Obtiene todas las reservaciones

    POST /reservations
        Crea una nueva reservación
        - Casos de uso: 2 [Depende de pmicro]

    GET  /reservations/{reservationId}
        - Casos de uso: 2 y 3
</pre>
</details>


<details>
    <summary><b>Payments microservice (pmicro) since v0.1.0</b></summary>

#### Dependencias

- [x] vimicro
- [x] remicro (UC3)
- [ ] ~~nmicro~~

#### Endpoints

<pre>
    POST /payments
        Crea un nuevo registro de pago
        - Casos de uso: 2

    GET  /payments?userId=&status=
        Obtiene los registros de pagos de un usuario
        - Casos de uso: 3 [Depende de remicro]

    PUT  /payments
        Realiza la transacción del cobro y actualiza el estado de un pago
        - Casos de uso: 3
</pre>
</details>


## Historial de cambios

### v0.1.2 Implementación faltante de los microservicios ```pmicro``` y ```remicro```
- Se agregaron datos de prueba

### v0.1.1 Microservicio ```remicro```
- **TODO**: Clean up
    - Make clear the separation between input and output DTO

### v0.1.0 Microservicio ```pmicro```

### v0.0.9 Microservicio ```vsmicro```
- **TODO**: Add pagination params and navigability links across all microservices

### v0.0.8 Logging y actualización de lógica
- Se eliminaron los ficheros de log4j.properties
- Se agrega variable de entorno para configurar el nivel de logging de los servicios
- Se cambia la lógica de búsqueda del caso de uso 1:
  Ahora se consultan primero los vehículos con disponibilidad y luego se 
  obtienen sus datos.

### v0.0.7 Microservicio ```vsmicro```

### v0.0.6 Microservicio ```tmicro```

### v0.0.5-1 Datos de prueba para ```pmmicro``` y ```vimicro```
- En ```vimicro``` ahora la base de datos se encarga de la creación 
  del inventario cuando se intenta insertar un nuevo vehículo y este no existe

### v0.0.5 Implementación faltante del microservicio ```vimicro```
- ~~**TODO**: Add sample records in the db~~

### v0.0.3-1 Implementación faltante del microservicio ```pmmicro```
- **TODO**: Testing across all microservices

### v0.0.2 Microservicio ```pmmicro```
- Creación del ```docker-compose.yml```

### v0.0.1 Proyecto inicial
