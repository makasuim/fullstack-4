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

## Conclusión

La arquitectura basada en microservicios permite construir un sistema flexible, escalable y mantenible. La separación de responsabilidades, junto con el uso de tecnologías modernas, facilita la evolución del sistema y su adaptación a nuevas necesidades.






## actualizacion readme 




# ⚙️ Innovatech - Infraestructura de Backend (Monorepo de Microservicios)

## 📝 Descripción del Proyecto
Innovatech es una plataforma basada en una **Arquitectura de Microservicios** desacoplada, escalable y de alta disponibilidad. Este repositorio centraliza la lógica del servidor a través de una estructura de Monorepo, conteniendo los dos servicios nucleares que resuelven el ciclo de autenticación, seguridad perimetral y la gestión de la persistencia de perfiles de usuario.

La infraestructura se conecta de manera directa con un **API Gateway (KrakenD)** y un **BFF (Backend For Frontend)** en el cliente para garantizar el correcto enrutamiento, seguridad por ocultación y mitigación de políticas de CORS.

---

## 🏗️ Arquitectura del Repositorio y Componentes

El proyecto backend está estructurado de forma unificada utilizando **Maven Multi-Module** o empaquetamiento independiente con herramientas de automatización comunes:

* **📁 auth/**: Microservicio encargado del registro de usuarios, encriptación de credenciales en base de datos, validación de identidades y la generación/firma de tokens **JWT (JSON Web Tokens)** bajo un control de acceso basado en roles (RBAC). Desarrollado en Java 21 con Spring Boot 3 y Spring Security.
* **📁 perfil/**: Microservicio encargado de la gestión de los recursos del perfil del usuario (actualizaciones parciales de nombres y almacenamiento/procesamiento de avatares serializados). Implementa métodos HTTP semánticos como `PATCH`. Desarrollado en Java 21 con Spring Boot 3.
* **📦 Base de Datos (MySQL)**: Motor relacional hospedado en la nube de **Railway**. Cada microservicio apunta a su propio esquema aislado de datos para garantizar el bajo acoplamiento y el cumplimiento estricto de las propiedades **ACID** (Atomicidad, Consistencia, Aislamiento y Durabilidad).

---

## 🛠️ Matriz Tecnológica del Backend

| Tecnología / Herramienta | Versión | Propósito / Justificación de Ingeniería |
| :--- | :--- | :--- |
| **Java JDK** | 21 (LTS) | Entorno de ejecución de largo soporte. Ofrece optimizaciones en rendimiento de memoria y concurrencia. |
| **Spring Boot** | 3.x | Framework robusto enfocado en la configuración automática y el despliegue ágil de servicios corporativos REST. |
| **Spring Security** | 3.x | Capa de seguridad encargada del filtrado de peticiones, intercepción de tokens JWT y protección de endpoints. |
| **Spring Data JPA** | 3.x | Abstracción de persistencia mediante Hibernate (ORM) para mapear las clases de Java con las tablas de MySQL. |
| **MySQL** | 8.x | Motor de base de datos relacional para mantener la consistencia e integridad de los datos de usuarios. |
| **KrakenD** | 2.x | API Gateway encargado de la orquestación, seguridad perimetral y unificación de endpoints públicos. |
| **Springdoc OpenAPI (Swagger)** | 3.x | Motor de documentación dinámica encargado de exponer los contratos de las APIs en tiempo real para el Frontend. |

---

## 📂 Explicación de los Archivos Raíz del Repositorio

Si la comisión evaluadora consulta sobre la responsabilidad de los archivos de la raíz, las definiciones técnicas son:

* **`pom.xml`**: Archivo de configuración de **Maven (Project Object Model)**. Centraliza la gestión de dependencias, las versiones de los frameworks (Spring Boot 3, Spring Security, Swagger) y define las directivas de compilación de los módulos.
* **`docker-compose.yml`**: Archivo de orquestación de contenedores de Docker. Permite levantar e interconectar con un solo comando todo el ecosistema local del backend (Bases de datos MySQL de prueba, instancias de KrakenD y los microservicios empaquetados).
* **`mvnw` y `mvnw.cmd`**: **Maven Wrapper** (para Linux/macOS y Windows respectivamente). Permite ejecutar comandos de Maven (`clean`, `install`, `package`) garantizando que todo el equipo de desarrollo use exactamente la misma versión de Maven sin necesidad de instalarla manualmente en el sistema operativo local.
* **`.github/workflows/`**: Directorio que almacena los archivos de configuración YAML para la automatización de **CI/CD (Integración Continua / Despliegue Continuo)** mediante GitHub Actions, permitiendo compilar y testear el código automáticamente ante cada commit.
* **`.gitignore` y `.gitattributes`**: Archivos de control de versiones. `.gitignore` evita subir carpetas pesadas o privadas (como `/target`, archivos `.env` o credenciales) al repositorio público de GitHub, mientras que `.gitattributes` normaliza el comportamiento de los saltos de línea del código entre diferentes sistemas operativos.

---

## 📁 Estructura Interna Estándar de los Servicios

Tanto la carpeta `auth` como `perfil` organizan el código interno de Java bajo el patrón de diseño clásico de **Arquitectura en Capas**:
