# Notas sobre el proyecto

En general, en el ```Vehicle (search) microservice``` se registran los vehículos por tipo,
mientras que el ```Vehicle inventory microservice``` lleva cada vehículo de forma individual.

Por ejemplo, supón que un proveedor tiene 100 coches Hyundai loniq 6 2025 (identicos), 
a los 100 vehículos se le registrará en el ```Vehicle (search) microservice``` bajo el 
identificador ```1``` junto a las caracteristicas de estos, modelo, marca y tipo.

Por otro lado, en el ```Vehicle inventory microservice```, se registrarán 
cada una de las placas, en que fechas estará en uso un vehículo en particular y si ya esta 
en uso y por quién.
