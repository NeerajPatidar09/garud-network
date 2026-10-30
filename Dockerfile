# Build stage for Java backend
FROM maven:3.9.6-eclipse-temurin-17 AS builder-backend

WORKDIR /app

# Copy backend project files
COPY server/pom.xml .
COPY server/src ./src

# Build the backend
RUN mvn clean package -DskipTests

# Build stage for Node.js frontend
FROM node:20.11.0-alpine AS builder-frontend

WORKDIR /app

# Copy frontend project files
COPY client/package*.json ./
COPY client/vite.config.ts .
COPY client/tailwind.config.ts .
COPY client/postcss.config.js .
COPY client/src ./src

# Install dependencies and build
RUN npm install
RUN npm run build

# Production stage
FROM eclipse-temurin:17-jre-alpine

# Install tini for proper signal handling
RUN apk add --no-cache tini

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod \
    JAVA_OPTS="--add-opens=java.base/java.lang=ALL-UNNAMED"

# Copy backend build output
COPY --from=builder-backend /app/target/garud-network-operations-*.jar /app/app.jar

# Copy frontend build output
COPY --from=builder-frontend /app/dist /app/frontend

# Copy application configuration
COPY server/src/main/resources/application.yml /app/config/

# Expose ports
EXPOSE 8080 5173

# Set entrypoint with tini for proper signal handling
ENTRYPOINT ["/sbin/tini", "--"]
CMD ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar & nginx -g 'daemon off;'"]