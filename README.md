# 🐾 Spring Boot : Application PetClinic — Laboratorio N° 11

## 📋 Información del Laboratorio
* **Institución:** Tecsup
* **Curso:** Construcción y Pruebas de Software — 4 C24
* **Laboratorio:** Laboratorio N° 11 — *Pruebas Unitarias con Base de Datos*
* **Docente:** Jean Marco Miranda Llanos
* **Alumno:** Follano Barrientos, Gersi Alexander
* **Grupo:** E (Desarrollo Individual)
* **Repositorio GitHub:** https://github.com/Alehfb/lab11-petclinic

---

## 🎯 Objetivo del Laboratorio
Desarrollar e integrar pruebas unitarias con persistencia en base de datos H2 en memoria para una aplicación Spring Boot 3.5, utilizando JUnit 5 y Spring Data JPA, siguiendo las buenas prácticas de la arquitectura por capas y la gestión colaborativa con Git.

---

## 📂 Casos Desarrollados

| Caso | Entidad / Tabla | Servicio | Clase de Pruebas | Casos de Prueba Implementados |
| :--- | :--- | :--- | :--- | :--- |
| **Caso 1** | `vets` | `VetService` | `VetServiceTest` | Búsqueda por ID, búsqueda por apellido, búsqueda de activos, creación, actualización, borrado, manejo de `VetNotFoundException`. |
| **Caso 2** | `owners` | `OwnerService` | `OwnerServiceTest` | Búsqueda por ID, búsqueda por apellido, búsqueda por ciudad, creación, actualización, borrado, manejo de `OwnerNotFoundException`. |
| **Caso 3** | `specialties` | `SpecialtyService` | `SpecialtyServiceTest` | Búsqueda por ID, búsqueda por nombre, búsqueda por consultorio (`office`), creación, actualización, borrado, manejo de `SpecialtyNotFoundException`. |
| **Caso 4** | `visits` | `VisitService` | `VisitServiceTest` | Búsqueda por ID, búsqueda por mascota (`petId`), búsqueda por veterinario (`vetId`), creación, actualización, borrado, manejo de `VisitNotFoundException`. |
| **Caso 5** | `types` | `TypeService` | `TypeServiceTest` | Búsqueda por ID, búsqueda por nombre, filtrado de activos, creación, actualización, borrado, manejo de `TypeNotFoundException`. |
| **Caso 6** | `vet_specialties` | `VetSpecialtyService` | `VetSpecialtyServiceTest` | Búsqueda por clave compuesta `(vetId, specialtyId)`, especialidades por veterinario, veterinarios por especialidad, asignación, actualización, eliminación de relación, manejo de `VetSpecialtyNotFoundException`. |

---

## 🚀 Comandos de Ejecución

### Ejecución de la Suite Completa
Para compilar el proyecto y ejecutar la totalidad de pruebas unitarias:
```bash
./mvnw clean test
```
*(En Windows PowerShell: `.\mvnw.cmd test`)*

### Ejecución de Pruebas por Caso Específico
```bash
# Caso 1 - Veterinarios
.\mvnw.cmd test -Dtest=VetServiceTest

# Caso 2 - Dueños
.\mvnw.cmd test -Dtest=OwnerServiceTest

# Caso 3 - Especialidades
.\mvnw.cmd test -Dtest=SpecialtyServiceTest

# Caso 4 - Visitas
.\mvnw.cmd test -Dtest=VisitServiceTest

# Caso 5 - Tipos de Mascotas
.\mvnw.cmd test -Dtest=TypeServiceTest

# Caso 6 - Especialidades de Veterinarios
.\mvnw.cmd test -Dtest=VetSpecialtyServiceTest
```

---

## 🌿 Flujo Git y Resolución de Conflictos

### Ramas de Desarrollo
* `main`: Rama principal protegida para versiones estables.
* `feature/caso1-vets`: Implementación del Caso 1 (CRUD y pruebas de `vets`).
* `feature/caso2-owners`: Implementación del Caso 2 (CRUD y pruebas de `owners`).
* `feature/caso3-specialties`: Implementación del Caso 3 (CRUD y pruebas de `specialties`).
* `feature/caso4-visits`: Implementación del Caso 4 (CRUD y pruebas de `visits`).
* `feature/caso5-types`: Implementación del Caso 5 (CRUD y pruebas de `types`).
* `feature/caso6-vet-specialties`: Implementación del Caso 6 (CRUD y pruebas de `vet_specialties`).
* `feature/docs-readme`: Rama de documentación técnica.

### Conflicto Resuelto (Criterio de Evaluación)
Durante la integración de la rama `feature/docs-readme` hacia `main`, se produjo un conflicto de merge en `README.md` debido a modificaciones simultáneas en las secciones iniciales del archivo. El conflicto fue resuelto manualmente integrando tanto la información institucional del alumno como las guías detalladas de ejecución de pruebas y el resumen arquitectónico.

---

## 📊 Historial de Commits
Para verificar el árbol gráfico de Git con todas las ramas y fusiones:
```bash
git log --all --graph --oneline
```
