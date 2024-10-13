
# SuresAdmin Backend

<p align="center">
  <img src="https://user-images.githubusercontent.com/your-image-path/logo.png" alt="SuresAdmin Logo" width="200">
</p>

<p align="center">
  <strong>Backend para la gestión de productos, clientes y cotizaciones</strong>
</p>

<p align="center">
  <a href="https://github.com/JesusAnayaMtz/suresadmin/issues">
    <img src="https://img.shields.io/github/issues/JesusAnayaMtz/suresadmin" alt="Issues">
  </a>
  <a href="https://github.com/JesusAnayaMtz/suresadmin/network">
    <img src="https://img.shields.io/github/forks/JesusAnayaMtz/suresadmin" alt="Forks">
  </a>
  <a href="https://github.com/JesusAnayaMtz/suresadmin/stargazers">
    <img src="https://img.shields.io/github/stars/JesusAnayaMtz/suresadmin" alt="Stars">
  </a>
  <a href="https://github.com/JesusAnayaMtz/suresadmin/blob/master/LICENSE">
    <img src="https://img.shields.io/github/license/JesusAnayaMtz/suresadmin" alt="License">
  </a>
</p>

---

## 📝 Descripción

**SuresAdmin Backend** es una API RESTful desarrollada en **Spring Boot**, que proporciona la lógica de negocio para el sistema de administración comercial **SuresAdmin**. Ofrece servicios para gestionar productos, clientes y cotizaciones, y se integra con el frontend basado en **React**.

### ✨ Características Principales

- **Gestión de Productos**: CRUD completo para productos con carga de imágenes.
- **Gestión de Clientes**: CRUD para clientes.
- **Gestión de Cotizaciones**: Control de cotizaciones, aplicación de descuentos e impuestos.
- **Notificaciones**: Envío de alertas para inventario bajo y límites de crédito.

---

## 📦 Instalación

1. Clona el repositorio:

   ```bash
   git clone https://github.com/JesusAnayaMtz/suresadmin.git
   ```

2. Configura la base de datos en el archivo `application.properties`.

3. Ejecuta el proyecto con **Maven**:

   ```bash
   mvn spring-boot:run
   ```

---

## 🌐 Endpoints del API

Aquí algunos de los endpoints disponibles:

| Método | Endpoint              | Descripción                               |
|--------|-----------------------|-------------------------------------------|
| GET    | `/api/productos`       | Obtener lista de productos                |
| POST   | `/api/productos`       | Crear un nuevo producto                   |
| GET    | `/api/clientes`        | Obtener lista de clientes                 |
| POST   | `/api/cotizaciones`    | Crear una nueva cotización                |
| GET    | `/api/notificaciones`  | Obtener notificaciones de inventario      |

---

## 📂 Estructura del Proyecto

```bash
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/suresadmin/
│   │   └── resources/
│   ├── test/
└── pom.xml
```

---

## 🤝 Contribución

¡Contribuciones son bienvenidas! Sigue estos pasos para colaborar:

1. Realiza un fork del proyecto.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y commitea (`git commit -am 'Agrega nueva funcionalidad'`).
4. Sube los cambios (`git push origin feature/nueva-funcionalidad`).
5. Crea un Pull Request.

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo [LICENSE](./LICENSE) para más detalles.

<p align="center">
  Creado por <a href="https://github.com/JesusAnayaMtz">Jesus Anaya</a>
</p>
