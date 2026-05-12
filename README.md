# ⚙️ Innovatech - Infraestructura de Backend 

##  1. Descripción del Proyecto
Innovatech es una plataforma empresarial diseñada bajo una **Arquitectura de Microservicios** desacoplada, escalable y de alta disponibilidad. Para responder con precisión y máxima calidad a los requerimientos y alcances acordados con la cátedra, el desarrollo del sistema se ha centralizado estratégicamente en la gestión de identidades, seguridad perimetral y administración de perfiles de usuario. 

La solución resuelve de manera robusta el ciclo completo de autenticación de accesos mediante tokens seguros y la persistencia de datos personales de los usuarios, interactuando de forma armónica con un API Gateway y un entorno frontend moderno. El sistema está diseñado bajo estándares de la ingeniería de software para garantizar el bajo acoplamiento, la seguridad por ocultación de infraestructura y la resiliencia ante fallos.

---
##  2. Arquitectura del Sistema

La solución se basa en una arquitectura de microservicios desacoplada. Cada componente cumple una función específica dentro del ecosistema, comunicándose mediante APIs REST sobre canales seguros utilizando mensajería en formato JSON.

### Componentes Principales

#### A. Frontend & BFF (Backend For Frontend)
* **Tecnologías:** Next.js 14 / 15 y TypeScript.
* **Responsabilidad:** No solo renderiza la interfaz gráfica y gestiona el estado reactivo global mediante **Zustand** en el cliente; además, ejecuta un servidor Node.js en segundo plano que actúa como un **BFF (`[...path]/route.ts`)**. Este componente intercepta las llamadas locales del navegador para mitigar las restricciones de CORS y enmascarar las rutas de producción del backend.

#### B. API Gateway (KrakenD)
* **Tecnologías:** KrakenD 2.x.
* **Responsabilidad:** Centraliza y unifica todas las solicitudes legítimas enviadas desde la capa cliente. Actúa como el Proxy Inverso perimetral del ecosistema, encargándose de la ocultación de la infraestructura interna, la orquestación de llamadas y el enrutamiento dinámico de tráfico hacia los microservicios en la nube.

#### C. Microservicios (Backend)
Desarrollados de forma autónoma en Java 21 utilizando el framework **Spring Boot 3.x**:
* **Auth Service:** Gestiona el ciclo de autenticación, validación de identidades, encriptación de credenciales y la generación, firma y emisión de tokens de seguridad **JWT** bajo el modelo de Control de Acceso Basado en Roles (RBAC).
* **Perfil Service:** Administra los recursos del perfil de usuario, procesando de manera óptima las solicitudes de lectura y actualizaciones parciales mediante el método HTTP `PATCH`.

#### D. Base de Datos
* **Tecnologías:** MySQL 8.x (Hospedado en la nube de **Railway**).
* **Responsabilidad:** Siguiendo los principios de diseño de sistemas distribuidos, cada microservicio interactúa de forma aislada con su propio esquema de base de datos MySQL, garantizando el bajo acoplamiento y el cumplimiento de las propiedades **ACID** (Atomicidad, Consistencia, Aislamiento y Durabilidad).

#### E. Infraestructura y Despliegue
* **Tecnologías:** Docker y Docker Compose para el empaquetado y la orquestación de servicios en entornos locales y entornos de producción basados en contenedores independientes.

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

