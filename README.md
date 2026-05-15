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


## Microservicios y endpoints

<details>
    <summary><b>Provider management microservice (pmmicro)</b></summary>

<pre>
    POST /providers
    GET  /providers/{providersId}
    GET  /providers  # Testing
</pre>
</details>


<details>
    <summary><b>Vehicles microservice (vsmicro)</b></summary>

<pre>
    POST /vehicles
    GET  /vehicles?location=&startDate=&endDate=&types=&prices=&currency=
    GET  /vehicles/{vehicleId}
</pre>

<b>Nota</b>: las operaciones GET pertenecen al Vehicles search microservice, 
por simplificar la implementación, los dos se unifican en uno
</details>


<details>
    <summary><b>Vehicles inventory microservice (vimicro)</b></summary>

<pre>
    POST /vehicles
    GET  /vehicles/{vehicleId}?startDate=&endDate=
    PUT  /vehicles/{vehicleId}
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

### v0.0.2 Microservicio ```pmmicro```
- Creación del ```docker-compose.yml```

### v0.0.1 Proyecto inicial
