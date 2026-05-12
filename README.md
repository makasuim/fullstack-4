# Sistema de Gestión de Proyectos - Innovatech

## Descripción

Innovatech es una plataforma de gestión de proyectos basada en arquitectura de microservicios. Permite administrar tareas, usuarios, recursos y métricas, mejorando la organización del trabajo y la visibilidad del estado de los proyectos.

El sistema está diseñado para ser escalable, seguro y fácil de mantener.

---

## Arquitectura

La solución se basa en microservicios desplegados en un entorno orquestado. Cada componente cumple una función específica y se comunica mediante APIs REST sobre HTTPS utilizando JSON.

### Componentes principales

#### Frontend
- Desarrollado con Next.js 14 y TypeScript  
- Permite la interacción de los usuarios con el sistema  
- Consume los servicios backend mediante HTTPS  

#### API Gateway
- Implementado con KrakenD  
- Centraliza las solicitudes del cliente  
- Valida tokens JWT  
- Enruta las peticiones a los microservicios  

#### Microservicios (Backend)
Desarrollados en Java 21 con Spring Boot:

- **Project Service**
  - Gestión de proyectos y tareas  

- **Resource Service**
  - Gestión de recursos y disponibilidad  

- **Auth Service**
  - Autenticación de usuarios  
  - Generación de JWT  
  - Control de acceso basado en roles (RBAC)  

- **Analytics Service**
  - Generación de métricas e indicadores (KPI)  

#### Base de datos
- PostgreSQL 16  
- Cada microservicio posee su propia base de datos  

#### Contenedores y Orquestación
- Docker para empaquetar los servicios  
- Kubernetes para despliegue, escalabilidad y alta disponibilidad  

---

## Seguridad

El sistema implementa:

- Autenticación mediante JWT  
- Autorización basada en roles (RBAC)  
- Validación de tokens en el API Gateway  
- Comunicación segura mediante HTTPS  

---

## Tecnologías utilizadas

| Tecnología     | Versión |
|----------------|--------|
| Java           | 21 (LTS) |
| Spring Boot    | 3.x |
| PostgreSQL     | 16 |
| KrakenD        | 2.x |
| Next.js        | 14 |
| TypeScript     | 5.x |
| Docker         | Última |
| Kubernetes     | Última |

---

## Funcionamiento general

1. El usuario interactúa con el frontend  
2. Las solicitudes se envían al API Gateway  
3. El Gateway valida el token JWT  
4. La solicitud se redirige al microservicio correspondiente  
5. El microservicio procesa la información y responde en formato JSON  

---

## Características del sistema

- Arquitectura desacoplada  
- Escalabilidad horizontal  
- Alta disponibilidad  
- Seguridad centralizada  
- Resiliencia ante fallos  

---

## Estructura de los microservicios

Cada microservicio sigue una estructura estándar:

- Controller: manejo de solicitudes HTTP  
- Service: lógica de negocio  
- Repository: acceso a datos  
- Entity: modelo de base de datos  
- DTO: transferencia de datos  

---

## Diagrama de Contenedores

>
> <img width="1919" height="2925" alt="Diagrama de contenedores  (1)" src="https://github.com/user-attachments/assets/82afb61c-b1fd-4fb1-9d3b-dd2d795040c3" />


---

---

## Patrones de Diseño Implementados en el Backend

Para garantizar el cumplimiento de los estándares de la ingeniería de software, el bajo acoplamiento, la seguridad perimetral y la óptima gestión de recursos en el servidor, el backend de **Innovatech** implementa de forma estricta los siguientes patrones de diseño:

### 1. Patrón Singleton / Instancia Única (Creacional - Catálogo GoF)
* **Implementación en el Código:** Se utiliza de manera nativa a través del motor de Inversión de Control (IoC) y la inyección de dependencias de Spring Boot. Al declarar las clases con las anotaciones `@RestController` y `@Service` (como en `AuthController` y `PerfilService`), Spring las registra y gestiona en su contenedor como *Spring Beans* bajo el alcance de **Singleton por defecto**.
* **Justificación de Ingeniería:** Garantiza que **exista una única instancia en memoria** de cada controlador y clase de servicio durante todo el ciclo de vida de la aplicación. Si múltiples usuarios interactúan con el microservicio de perfiles simultáneamente de forma concurrente, el servidor en Railway no duplica los objetos en la memoria RAM; utiliza la misma instancia única, optimizando drásticamente el consumo de recursos de infraestructura y previniendo fugas de memoria (*Memory Leaks*).

### 2. Patrón DTO - Data Transfer Object / Objeto de Transferencia de Datos (Arquitectura Enterprise)
* **Implementación en el Código:** Se encuentra estructurado dentro de los paquetes `dto/` de ambos microservicios (por ejemplo: `LoginRequestDTO`, `RegistroResponseDTO`, `PerfilPATCHDTO`). Consiste en clases planas de Java (POJOs o Java Records) que carecen por completo de lógica de negocio.
* **Justificación de Ingeniería:** Establece un contrato de software seguro encargado únicamente de empaquetar, agrupar y transportar los datos serializados en formato JSON a través de la red en una sola llamada HTTP. Su uso es crítico para **aislar por completo la capa de persistencia de la capa de presentación**, evitando exponer directamente las entidades relacionales de la base de datos (`@Entity`) al cliente frontend. Esto protege campos altamente sensibles (como contraseñas con Hash/BCrypt) y reduce el tamaño de la carga útil (*Payload*) enviada por internet.


## Conclusión

La arquitectura basada en microservicios permite construir un sistema flexible, escalable y mantenible. La separación de responsabilidades, junto con el uso de tecnologías modernas, facilita la evolución del sistema y su adaptación a nuevas necesidades.

