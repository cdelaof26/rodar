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
* Los microservicios de impuestos, pagos y notificaciones serán tratados como componentes simulados que siempre tendrán respuestas fijas _favorables_
* Todas las bases de datos serán SQL
* Solo se implementa el flujo básico como se detalla en los diagramas de secuencia

## Documentos

En el directorio ```src``` se encuentran los diagramas de secuencia y modelo de dominio
que pueden renderizarse con PlantUML y, además, los casos de uso.

Por otra parte, en ```rendered```, se encuentra la arquitectura del sistema y 
los diagramas anteriores en formato ```svg```.

## Ejecución

La información necesaria para poner en marcha ```rodar```, se encuentrá en el fichero
[src/README.md](src/README.md)


## Microservicios y endpoints

<details>
    <summary><b>Provider management microservice (pmmicro)</b></summary>

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
    <summary><b>Vehicles search microservice (vsmicro)</b></summary>

<pre>
    POST /vehicles
    GET  /vehicles?location=&startDate=&endDate=&types=&prices=&currency=
    GET  /vehicles/{vehicleId}
</pre>

<b>Nota</b>: la operación POST corresponde al endpoint privado que accede 
Vehicles microservice a través de un ```Message Broker``` para nuevas 
entradas. Por simplificar la implementación, se utiliza directamente.
</details>


<details>
    <summary><b>Vehicles inventory microservice (vimicro)</b></summary>

<pre>
    POST /vehicles
        Puede crear una nueva entrada de inventario y registra las placas de un vehículo

    GET  /vehicles  # Testing
        Obtiene todos los inventarios

    GET  /vehicles/{vehicleId}?startDate=&endDate=
        Obtiene la cantidad de vehículos disponibles en un inventario dado un rango de fechas
        - Casos de uso: 1, 2

    PUT  /vehicles/{vehicleId}
        Actualiza el estado 'in_use' y/o agrega fechas/usuario a un vehículo
        - Casos de uso: 3
</pre>
</details>


<details>
    <summary><b>Taxes microservice (tmicro)</b></summary>

<pre>
    GET /currencies/{c}/rates?to=&amount=&location=
    GET /vehicle/rate/{vehicleId}?location=
</pre>
</details>


<details>
    <summary><b>Reservation microservice (remicro)</b></summary>

<pre>
    POST /reservations
    GET  /reservations/{reservationId}
</pre>
</details>


<details>
    <summary><b>Payments microservice (pmicro)</b></summary>

<pre>
    POST /payments
    GET  /payments?status=
    PUT  /payments
</pre>
</details>


## Historial de cambios

### v0.0.5-1 Datos de prueba para ```pmmicro``` y ```vimicro```
- En ```vimicro``` ahora la base de datos se encarga de la creación 
  del inventario cuando se intenta insertar un nuevo vehículo y este no existe

### v0.0.5 Implementación faltante del microservicio ```vimicro```
- **TODO**: Testing
- ~~**TODO**: Add sample records in the db~~

### v0.0.3-1 Implementación faltante del microservicio ```pmmicro```
- **TODO**: Testing

### v0.0.2 Microservicio ```pmmicro```
- Creación del ```docker-compose.yml```

### v0.0.1 Proyecto inicial
