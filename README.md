# Spring Boot : Application PetClinic - Laboratorio N° 11

## Guía de Ejecución de Pruebas Unitarias

Para compilar y ejecutar la suite completa de pruebas unitarias conectadas a la base de datos H2:

```bash
./mvnw clean test
```

### Ejecución de Pruebas Individuales por Caso:
- **Caso 1 (Vets):** `./mvnw test -Dtest=VetServiceTest`
- **Caso 2 (Owners):** `./mvnw test -Dtest=OwnerServiceTest`
- **Caso 3 (Specialties):** `./mvnw test -Dtest=SpecialtyServiceTest`
- **Caso 4 (Visits):** `./mvnw test -Dtest=VisitServiceTest`
- **Caso 5 (Types):** `./mvnw test -Dtest=TypeServiceTest`
- **Caso 6 (VetSpecialties):** `./mvnw test -Dtest=VetSpecialtyServiceTest`