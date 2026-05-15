# Casos de uso

Estos casos de uso se basan en la plantilla de _Alistair Cockburn_, 
el documento ```TR.96.03a``` del 26 de octubre de 1998 versión 2.

<details>
	<summary><b>Buscar vehículos en multiples zonas con conversión de moneda y disponibilidad</b></summary>

**Objetivo**

Permitir que un usuario busque vehículos disponibles en múltiples zonas geográficas, convertir precios automáticamente a la moneda del usuario, validar disponibilidad y presentar resultados ordenados por relevancia, precio y disponibilidad.

**Nivel**

Primario

**Actor principal**

Usuario (cliente que busca vehículos para alquilar)

**Actores secundarios**

* Microservicio Vehicles search microservice
* Microservicio Vehicles inventory microservice
* Microservicio Taxes microservice

**Precondiciones**

* El usuario ha iniciado sesión
* El usuario ha proporcionado parámetros de búsqueda: ubicación de recogida, rango de fechas, rango de precio (opcional)
* El servicio de conversión de moneda está disponible
* La moneda del usuario está configurada en su perfil o detectada por geolocalización
* Los datos de disponibilidad en el Vehicles inventory microservice están sincronizados

**Garantías de éxito (Postcondiciones)**

* Se devuelve una lista de vehículos disponibles que coinciden con los criterios
* Todos los precios se han convertido a la moneda del usuario
* La disponibilidad mostrada es precisa para las fechas solicitadas
* Los resultados están ordenados por relevancia, precio y disponibilidad
* Se incluye información de: modelo, marca, tipo, precio convertido, ubicación, proveedor y disponibilidad
* El usuario puede filtrar y ordenar los resultados
* Se registra la búsqueda para análisis posterior


**Escenario principal de éxito (o flujo básico)**

1. El usuario accede a la página de búsqueda
2. El usuario selecciona ubicación de recogida (ej: "Madrid")
3. El usuario selecciona rango de fechas (ej: 1-5 de mayo 2026)
4. El usuario selecciona filtros opcionales (tipo de vehículo, rango de precio por día)
5. El usuario envía la búsqueda
6. El Vehicles search microservice recibe la consulta y detecta la moneda del usuario (EUR) y zona geográfica (España)
7. El Vehicles search retorna una lista de candidatos (ej: 150 SUVs en Madrid)
8. El Vehicles inventory microservice valida disponibilidad real para cada vehículo en las fechas especificadas
9. Se filtra la lista a solo vehículos disponibles (ej: 87 vehículos disponibles)
10. Para cada vehículo disponible, el Taxes microservice busca el precio base en la moneda original del proveedor (EUR, USD, GBP, etc.) y convierte todos los precios a la moneda del usuario
11. El Taxes microservice también retorna impuestos aplicables en la zona de recogida
12. El Taxes microservice calcula precio final por día: (precio base × días + impuestos) / días
13. Se ordenan resultados por precio ascendente
14. Se aplican filtros de rango de precio y se eliminan los resultados fuera del rango
15. Se retorna lista final de vehículos con información: marca, modelo, tipo, precio/día en EUR, ubicación exacta, nombre proveedor y disponibilidad

**Extensiones (o flujos alternativos)**

  5.  El usuario busca en múltiples zonas geográficas (Madrid, Barcelona, Valencia)
- El sistema ejecuta búsquedas en paralelo para cada zona, combina resultados y ordena globalmente por precio. Muestra a qué ciudad pertenece cada resultado.


  7.  No hay vehículos disponibles en la ubicación solicitada
El sistema sugiere ubicaciones alternativas cercanas (radio de 25 km) o amplía el rango de fechas. Muestra vehículos disponibles en zonas cercanas.


  8.  Algunos vehículos muestran disponibilidad diferente entre el índice y el inventario real
- El sistema usa la información más reciente del inventario real. Si hay discrepancia, actualiza el índice de búsqueda automáticamente.


  14.  El usuario aplica múltiples filtros simultáneamente (ej: marca + tipo + rango precio)
- El sistema procesa filtros de forma progresiva: primero por disponibilidad, luego marca, tipo, y finalmente rango de precio.


  15.  La búsqueda retorna más de 100 vehículos
- El sistema implementa paginación mostrando 20 resultados por página. Permite al usuario ir a otras páginas o refinar búsqueda.
</details>



<details>
	<summary><b>Reservar vehículo con cálculo de precio final</b></summary>

**Objetivo**

Permitir que un usuario reserve un vehículo específico por un periodo determinado, calculando el precio final incluyendo impuestos y tarifas, y validando la disponibilidad para generar una confirmación de reserva con desglose de precios.

**Nivel**

Primario

**Actor principal**

Usuario (cliente que desea reservar un vehículo)

**Actores secundarios**

* Microservicio Vehicles inventory microservice
* Microservicio Taxes microservice

**Precondiciones**
* El usuario ha iniciado sesión en el sistema
* El usuario ha visualizado los detalles de un vehículo específico
* El vehículo existe en el catálogo y tiene al menos una unidad disponible
* El Taxes microservice está accesible y contiene reglas de impuestos para la zona geográfica
* Las fechas de reserva solicitadas son válidas (fecha final posterior a fecha inicial)

**Garantías de éxito (Postcondiciones)**

* Se ha creado una reservación
* El precio final ha sido calculado incluyendo base diaria, impuestos y tarifas de servicio
* El usuario recibe confirmación inmediata con desglose detallado de precios
* Se decrementa la cantidad disponible del vehículo para las fechas seleccionadas
* La reserva se registra en el historial del usuario
* El usuario recibe notificación por correo electrónico ó SMS con los detalles de la reserva y próximos pasos

**Escenario principal de éxito (o flujo básico)**

1. El usuario selecciona las fechas de inicio y fin de la reserva en el detalle del vehículo 
2. El usuario confirma la intención de reservar 
3. El Reservation microservice valida que las fechas sean válidas y que no se supere el máximo de días permitido 
   * La cantidad de días máximo permitido es de 30 
4. El Reservation microservice consulta al Vehicles inventory microservice la disponibilidad real del vehículo para esas fechas específicas 
5. El Vehicles inventory microservice confirma disponibilidad
6. El Reservation microservice calcula el número de días de renta 
6. El Reservation microservice consulta al Taxes microservice el precio base diario del vehículo y las reglas de impuestos aplicables según ubicación y zona 
7. El Taxes microservice retorna el desglose: precio por día, impuestos y tarifas de servicio 
8. El Reservation microservice calcula el precio total (base × días + impuestos + tarifas)
9. Se crea el registro de reserva en la base de datos con estado "pending_payment"
10. El sistema genera un documento de confirmación con número de reserva, detalles del vehículo y desglose de precios 
11. El Reservation microservice muestra al usuario (a través del front-end o aplicación móvil) la confirmación en pantalla con opción de proceder al pago

**Extensiones (o flujos alternativos)**

4.  El vehículo no está disponible para las fechas solicitadas
- El sistema notifica al usuario que el vehículo no está disponible, sugiere fechas alternativas disponibles o vehículos similares.
 

5. El vehículo es reservado por otro usuario justo cuando se intenta confirmar
 - El sistema detecta el conflicto, cancela la operación, notifica al usuario y ofrece vehículos alternativos similares.

7.  El Taxes microservice no responde o está no disponible
- El sistema cancela la operación y notifica al usuario.

12.  Fallo en envío de notificación
- El reintento se programa automáticamente, pero la reserva se considera válida y el usuario puede proceder al pago.
</details>


<details>
	<summary><b>Procesar pago y actualizar inventario</b></summary>

**Objetivo**

Procesar el pago de una reserva de vehículo, validar la transacción, actualizar el inventario de disponibilidad y notificar al usuario sobre el estado del pago y la confirmación de su reserva.

**Nivel**

Primario

**Actor principal**

Usuario (cliente que ha reservado un vehículo)

**Actores secundarios**

* Proveedor de pago (pasarela de pago externa)
* Microservicio Payments microservice
* Microservicio Vehicles inventory microservice
* Microservicio Notifications microservice

**Precondiciones**

* El usuario ha iniciado sesión en el sistema
* El usuario tiene una reserva confirmada en estado "pending_payment"
* El sistema tiene acceso válido a la pasarela de pago configurada
* La información del vehículo y su disponibilidad existe en el inventario

**Garantías de éxito (Postcondiciones)**

* El pago ha sido procesado exitosamente en la pasarela de pago
* La reserva cambia de estado "pending_payment" a "confirmed"
* El inventario de disponibilidad del vehículo se ha decrementado
* El usuario recibe notificaciones por correo electrónico ó SMS con confirmación de pago, estado de reserva y detalles de la renta
* Los datos de la transacción quedan registrados en el historial de pagos

**Escenario principal de éxito (o flujo básico)**

1. El usuario accede a la sección de pagos pendientes 
2. El usuario selecciona la reserva a pagar 
3. El usuario completa la información de pago (tarjeta de crédito, PayPal o transferencia bancaria)
4. El usuario confirma el pago 
5. El Payments microservice envía la solicitud de pago a la pasarela de pago 
6. La pasarela de pago válida la transacción y retorna confirmación
7. El Payments microservice actualiza el estado de pago a "completed"
8. Se notifica al Vehicles inventory microservice que disminuya la disponibilidad del vehículo 
8. El Notifications microservice envía notificación de cobro exitoso al usuario (correo ó SMS)
9. La reserva se confirma y el usuario recibe detalles de la renta

**Extensiones (o flujos alternativos)**

6a.  La pasarela de pago rechaza la transacción
- El sistema notifica al usuario del rechazo, proporciona motivo del fallo y ofrece opción de reintentar con otro método de pago. La reserva permanece en estado “pending_payment".


6b.  Timeout en la comunicación con pasarela de pago
- El sistema registra la operación como pendiente, reintentar automáticamente hasta 3 veces, y si falla, notificar al usuario para que lo intente nuevamente.


8.  El vehículo ya no está disponible en inventario
- Cancelar el pago, revertir la transacción, cambiar reserva a "cancelled" y notificar al usuario ofreciendo alternativas.


9.  Fallo en el envío de notificaciones
- El sistema encola los intentos de envío y los reintenta periódicamente hasta 24 horas.

</details>



<details>
	<summary><b>Generar reporte de analíticas quincenal</b></summary>

**Objetivo**

Recopilar datos de uso y disponibilidad de vehículos durante un periodo quincenal y generar reportes analíticos que permitan al proveedor visualizar métricas de desempeño, ocupación y rentabilidad de su flota.

**Nivel**

Primario

**Actor principal**

Proveedor (propietario de vehículos en la plataforma)

**Actores secundarios**

* Microservicio Reservation microservice
* Microservicio Vehicles inventory microservice
* Proceso batch

**Precondiciones**

* El proveedor ha iniciado sesión en el sistema
* Es inicio de mes o día 15
* Existen datos de reservaciones y actualizaciones de inventario en el periodo a reportar
* Los microservicios de reservaciones e inventario han registrado datos de uso

**Garantías de éxito (Postcondiciones)**

* El reporte quincenal ha sido generado y almacenado
* El proveedor puede acceder al reporte a través de
* El reporte contiene estadísticas de ocupación, ingresos, vehículos más rentables y disponibilidad
* Los datos históricos quedan registrados en la base de datos de documentos para análisis futuro
* El reporte está disponible en formato descargable (PDF)

**Escenario principal de éxito (o flujo básico)**

1. El proceso batch se ejecuta automáticamente en las fechas establecidas 
2. Se agregan los datos de todas las reservaciones completadas durante el periodo 
3. Se calculan métricas: total de reservas, días de ocupación, ingresos totales, precio promedio por día, tasa de ocupación por vehículo 
4. Se identifican los vehículos más y menos rentables 
5. Se genera un documento de reporte con gráficos 
6. El proveedor accede a la sección de analíticas y descarga el reporte

**Extensiones (o flujos alternativos)**

2, 3.  Falta data de reservaciones o inventario
- El proceso batch registra advertencias, genera reporte parcial con datos disponibles y notifica al proveedor sobre los periodos incompletos.

4.  Se detectan anomalías en los datos (ej: reserva sin fin de fecha)
- El sistema marca esos registros como inconsistentes, excluye del cálculo de métricas y genera un reporte de datos anomalía para revisión.

6.  El proveedor solicita reporte de periodo personalizado
- El sistema permite generar reportes bajo demanda para rangos de fechas específicos, no solo periodos quincenales.

</details>
