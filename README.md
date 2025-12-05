# Reviews Service - GastroReview

Microservicio GraphQL con seguridad JWT e integración con Azure Cognitive Services.

## 🚀 Tecnologías

- Java 21
- Spring Boot 3.3.3
- Spring for GraphQL
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL (Neon)
- Azure Text Analytics
- Azure Content Moderator
- Spring Cloud Netflix Eureka Client
- Spring Cloud OpenFeign
- Maven

## 📦 Compilar

```bash
mvn clean package -DskipTests
```

## ▶️ Ejecutar Localmente

```bash
mvn spring-boot:run
```

## 🌐 Puerto

Este servicio corre en el puerto **8083**.

## 🔧 Configuración para Render

### Build Command
```
mvn clean package -DskipTests
```

### Start Command
```
java -jar target/*.jar
```

### Variables de Entorno
```
DATABASE_URL=jdbc:postgresql://ep-crimson-waterfall-a5g2m7gj.us-east-2.aws.neon.tech:5432/neondb?sslmode=require
DATABASE_USERNAME=neondb_owner
DATABASE_PASSWORD=npg_VkPsxdU17tEG
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=https://gastroreview-eureka.onrender.com/eureka/
JWT_SECRET=mySecretKeyForJWTTokenGenerationThatIsAtLeast256BitsLong
AZURE_TEXT_ANALYTICS_KEY=your_key_here
AZURE_TEXT_ANALYTICS_ENDPOINT=https://your-resource.cognitiveservices.azure.com/
AZURE_CONTENT_MODERATOR_KEY=your_key_here
AZURE_CONTENT_MODERATOR_ENDPOINT=https://your-resource.cognitiveservices.azure.com/
JAVA_OPTS=-Xmx512m -Xms256m
PORT=8083
```

## 🔍 GraphQL Endpoints

- `/graphql` - Endpoint GraphQL
- `/graphiql` - Interfaz GraphiQL (UI interactiva)

### Queries
```graphql
query {
  reviews(restaurantId: "id") {
    id
    rating
    comment
    sentimentScore
    sentimentLabel
  }
}
```

### Mutations
```graphql
mutation {
  createReview(input: {
    restaurantId: "id"
    rating: 5
    comment: "Excelente!"
  }) {
    id
    sentimentScore
  }
}
```

## 📊 Base de Datos

Tablas gestionadas:
- `reviews`
- `review_images`
- `review_audios`
- `favorite_restaurants`
- `favorite_reviews`
- `alerts`
- `notifications`

## 🧠 Azure Cognitive Services

### Text Analytics
- Análisis de sentimiento en reseñas
- Detección de idioma
- Extracción de frases clave

### Content Moderator
- Moderación de contenido ofensivo
- Filtrado de spam
- Clasificación de contenido

## 🔗 Comunicación

Usa Feign Clients para comunicarse con:
- Users Service
- Restaurants Service

## 📝 Notas

- Requiere Azure Cognitive Services configurado
- Implementa GraphQL + seguridad JWT
- Se registra automáticamente en Eureka
- Analiza sentimiento de cada reseña automáticamente

## 🔗 Enlaces

- [Spring for GraphQL](https://spring.io/projects/spring-graphql)
- [Azure Cognitive Services](https://azure.microsoft.com/services/cognitive-services/)
- [GraphQL](https://graphql.org/)
