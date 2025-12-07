@echo off
setlocal enabledelayedexpansion

echo ================================================
echo Starting Reviews Service on port 8083
echo Using Neon Database
echo Endpoints: http://localhost:8083/graphql
echo           http://localhost:8083/graphiql
echo ================================================

java -jar target\reviews-service-1.0.0.jar ^
  --server.port=8083 ^
  --DATABASE_URL="jdbc:postgresql://ep-patient-credit-adceyqvj-pooler.c-2.us-east-1.aws.neon.tech:5432/neondb?sslmode=require&channelBinding=require" ^
  --DATABASE_USERNAME=neondb_owner ^
  --DATABASE_PASSWORD=npg_GduJi8PA2vTb ^
  --jwt.secret=mySecretKeyForJWTTokenGenerationThatIsAtLeast256BitsLong ^
  --eureka.client.enabled=false %*
