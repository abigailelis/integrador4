# Microservicio de Administración - API REST

Este microservicio proporciona endpoints para la gestión administrativa del sistema de monopatines, incluyendo manejo de tarifas, reportes de monopatines, facturación y gestión de usuarios.

## Endpoints

### Gestión de Tarifas

#### Crear Nueva Tarifa


http POST /admin Content-Type: application/json
```
{ 
    "tipo_tarifa": "NORMAL", 
    "monto": 150.00 
}
``` 
**Respuesta exitosa (200 OK)**
```
{ 
    "id": 1, 
    "tipo_tarifa": "NORMAL", 
    "monto": 150.00 
}
``` 
**Respuesta error (409 Conflict)**

```
{ 
    "error": "Conflicto de datos", 
    "mensaje": "Ya existe una tarifa con el tipo: NORMAL" 
}
``` 

#### Actualizar Tarifa Existente

http PUT /admin/{id} Content-Type: application/json
```
{ 
    "tipo_tarifa": "NORMAL", 
    "monto": 200.00 
}
``` 
**Respuesta exitosa (200 OK)**

```
{ 
    "id": 1, 
    "tipo_tarifa": "NORMAL",
    "monto": 200.00 
}
``` 

#### Ajustar Precios de Tarifas

http PUT /admin Content-Type: application/json
```
{ "tarifa": 
    { 
        "id": 1, 
        "monto": 180.00 
    }, 
    "modificationDate": "2025-07-01" 
}
``` 

### Reportes de Monopatines

#### Generar Reporte de Monopatines

http GET /admin/monopatines/reporte?seePause=true

**Respuesta exitosa (200 OK)**

#### Obtener Monopatines con Más Viajes
```
http GET /admin/monopatines/masViajes?tripCount=10&date=2025
``` 
**Respuesta exitosa (200 OK)**

### Facturación

#### Obtener Facturación por Rango de Meses

http GET /admin/viajes/facturacion?startDate=2025-01&endDate=2025-06

**Respuesta exitosa (200 OK)**
```
{ 
    "periodoInicio": "2025-01", 
    "periodoFin": "2025-06", 
    "montoTotal": 50000.00, 
    "cantidadViajes": 300 
}
``` 

### Gestión de Usuarios

#### Actualizar Estado de Usuario

http PATCH /admin/usuarios/{id} Content-Type: application/json
```
{ 
    "activo": false 
}
``` 
**Respuesta exitosa (200 OK)**
```
{ 
    "id": 1, 
    "nombre": "Juan", 
    "activo": false 
}
``` 

#### Obtener Usuarios Frecuentes

http GET /admin/usuarios/frecuentes?type=MONTH&startDate=2025-01-01&endDate=2025-12-31

**Respuesta exitosa (200 OK)**

## Códigos de Estado

- `200 OK`: Solicitud exitosa
- `201 Created`: Recurso creado exitosamente
- `400 Bad Request`: Solicitud inválida
- `404 Not Found`: Recurso no encontrado
- `409 Conflict`: Conflicto con recurso existente
- `500 Internal Server Error`: Error del servidor

## Formatos

- Las fechas deben enviarse en formato ISO: `YYYY-MM-DD`
- Los montos monetarios utilizan punto decimal
- Las coordenadas geográficas se expresan como tuplas `(latitud, longitud)`
- Los tiempos se expresan en formato `HH:mm:ss`

## Notas Adicionales

- Todas las respuestas incluyen el header `Content-Type: application/json`
- Los errores incluyen un objeto JSON con los campos `error` y `mensaje`
- Las operaciones de actualización requieren autenticación
- Los rangos de fechas son inclusivos
