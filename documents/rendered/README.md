# Elementos arquitectónicos

![](./Arquitectura.drawio.svg)

En la arquitectura se observan diferentes elementos,

* Rectángulos con esquinas redondeadas doble:
  * Microservicio con mediana cantidad de réplicas
* Rectángulos con esquinas redondeadas triple:
  * Microservicio con alta cantidad de réplicas
* Dos cilindros: 
  * Bases de datos con mediana cantidad de réplicas
* Tres cilindros: 
  * Bases de datos con alta cantidad de réplicas
* Rectángulo con dos sobres en color azul:
  * MessageBroker
* Círculo con un cuadrado que apunta a tres cuadrados 
más pequeños:
  * Balanceador de carga
