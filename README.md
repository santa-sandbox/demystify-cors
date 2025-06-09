# 🚀 Demystify CORS - Cross-Origin Resource Sharing Demo

> ## Disclaimers
>
> README is generated from Claude Sonnet AI, and reviewed by me.
> Please note that this project is for blog purposes only.

A comprehensive demonstration project showcasing Cross-Origin Resource Sharing (CORS) concepts using a modern full-stack application. This project features a Spring Boot backend API and a SvelteKit frontend, designed to help developers understand how CORS works in real-world applications.

## 🎯 Project Overview

This project implements Spring Boot & Svelte and demo with story of tracking system called "シャムネコ (SHAMU Cat) Delivery" to demonstrate CORS configuration and behavior. The application consists of:

- **Backend**: Spring Boot REST API (Java 24) providing delivery tracking endpoints
- **Frontend**: SvelteKit application (TypeScript) with TailwindCSS for the user interface
- **API Testing**: Bruno collection for testing API endpoints

### Key Features

- 📦 Package tracking system with real-time status updates
- 🔒 CORS configuration demonstration between different origins
- 🎨 Modern, responsive UI with Japanese-inspired design
- 🧪 API testing suite with Bruno
- 📱 Mobile-friendly interface
- ⚡ GraalVM native compilation support

## 🏗️ Architecture

```
demystify-cors/
│   # Backend API (Port 8080)
├── spring-boot/
│   ├── src/main/java/
│   │   └── blog/natta/santa/cors/
│   │       ├── CorsApplication.java
│   │       ├── config/
│   │       │   │   # CORS configuration
│   │       │   └── CorsConfig.java
│   │       └── delivery/
│   │           ├── endpoint/
│   │           │   └── DeliveryController.java
│   │           └── response/
│   │               └── TrackingResultResponse.java
│   └── pom.xml
│   # Frontend (Port 5173)
├── svelte/
│   ├── src/
│   │   ├── routes/
│   │   │   └── +page.svelte
│   │   └── lib/
│   └── package.json
│   # API testing collection
└── bruno/
    └── cors-demystify/
        └── Tracking-Status.bru
```

## 🚀 Getting Started

### Prerequisites

- **Java 24** (for Spring Boot backend)
- **Node.js 18+** (for SvelteKit frontend)
- **pnpm** (package manager for frontend)
- **Maven** (included via wrapper)

### Installation & Setup

1. **Clone the repository**
   ```bash
   git clone git@github.com:santa-sandbox/demystify-cors.git
   cd demystify-cors
   ```

2. **Backend Setup (Spring Boot)**
   ```bash
   cd spring-boot

   # Run in development mode
   ./mvnw spring-boot:run

   # Or build and run
   ./mvnw clean package
   java -jar target/cors-0.0.1-SNAPSHOT.jar
   ```

3. **Frontend Setup (SvelteKit)**
   ```bash
   cd svelte

   # Install dependencies
   pnpm install

   # Run development server
   pnpm dev
   ```

4. **Access the application**
   - Frontend: http://localhost:5173
   - Backend API: http://localhost:8080

## 🏃‍♂️ Running the Applications

### Spring Boot Backend

```bash
cd spring-boot

# Build for production
./mvnw clean package

# Build native binary (GraalVM required)
./mvnw clean package -Pnative

# Run native binary
./target/cors
```

**Backend runs on:** `http://localhost:8080`

### SvelteKit Frontend

```bash
cd svelte

# Build for production
pnpm build

# Preview production build
pnpm preview
```

#### Dev Mode

**Frontend runs on:** `http://localhost:5173`

#### Preview Mode

**Frontend runs on:** `http://localhost:4173`

## 🔍 API Endpoints

### Delivery Tracking API

- **GET** `/api/delivery/status/{trackingId}`
  - Returns tracking information for a given tracking ID
  - Example: `GET http://localhost:8080/api/delivery/status/SHAMU001`

#### Response Format

```json
{
  "id": "SHAMU001",
  "status": "processing",
  "title": "Parcel is being processed",
  "location": "Aichi",
  "timestamp": "2025-06-19 18:00:00",
  "estimatedDelivery": "2025-06-25 19:00:00"
}
```

## 🛡️ CORS Configuration

The project demonstrates CORS configuration in the Spring Boot backend:

```java
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:5173");
            }
        };
    }
}
```

### Key CORS Concepts Demonstrated

- **Same-Origin Policy**: Understanding browser security restrictions
- **Cross-Origin Requests**: Frontend (port 5173) calling backend (port 8080)
- **CORS Headers**: Proper configuration of allowed origins
- **Preflight Requests**: Handling OPTIONS requests for complex requests

## 🧪 Testing

### Using Bruno API Client

1. Install [Bruno](https://usebruno.com/)
2. Open the collection in `bruno/cors-demystify/`
3. Run the `Tracking-Status` request to test the API

### Manual Testing

1. Start both backend (port 8080) and frontend (port 5173)
2. Visit http://localhost:5173
3. Enter a tracking number (e.g., "SHAMU001", "TEST123")
4. Observe the CORS request in browser dev tools

### CORS Testing Scenarios

- ✅ **Allowed Origin**: Frontend (localhost:5173) → Backend (localhost:8080)
- ❌ **Blocked Origin**: Try accessing from different port/domain
- 🔍 **Browser Dev Tools**: Inspect CORS headers and preflight requests

## 🛠️ Development

### Project Structure

- **Spring Boot**: Clean architecture with controller, service, and configuration layers
- **SvelteKit**: Modern Svelte 5 with TypeScript and TailwindCSS
- **Responsive Design**: Mobile-first approach with TailwindCSS
- **Error Handling**: Comprehensive error states and user feedback

### Key Technologies

**Backend:**
- Spring Boot 3.5
- Java 24 - JDK-GraalVM CE
- Maven
- GraalVM Native Image support

**Frontend:**
- SvelteKit 2.x
- Svelte 5
- TypeScript
- TailwindCSS 4.x
- Vite

## 🎨 UI Features

- **Japanese-inspired Design**: シャムネコ (SHAMU Cat) themed interface
- **Real-time Tracking**: Live package status updates
- **Progress Timeline**: Visual delivery progress indicator
- **Responsive Layout**: Works on desktop and mobile devices
- **Loading States**: Smooth loading animations and feedback
- **Error Handling**: User-friendly error messages

## 🐳 Native Compilation

The Spring Boot application supports GraalVM native compilation:

```bash
cd spring-boot

# Build native image (requires GraalVM)
./mvnw clean package -Pnative

# Run native binary
./target/cors
```

## 📚 Learning Objectives

This project helps you understand:

1. **CORS Fundamentals**: Same-origin policy and cross-origin requests
2. **CORS Configuration**: Proper setup in Spring Boot applications
3. **Frontend-Backend Communication**: Making API calls across different origins
4. **Modern Web Architecture**: Full-stack development with modern tools
5. **Error Handling**: Graceful handling of CORS-related errors

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is open source and available under the MIT License.

## 🔗 Useful Links

- [Spring Boot CORS Documentation](https://spring.io/guides/gs/rest-service-cors/)
- [MDN CORS Guide](https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS)
- [SvelteKit Documentation](https://kit.svelte.dev/)
- [Bruno API Client](https://usebruno.com/)

---

Built with ❤️ to demystify CORS for developers worldwide 🌍
