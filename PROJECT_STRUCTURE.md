# 📁 Project Structure Documentation

This document provides a comprehensive overview of the Demystify CORS project structure, explaining the purpose and contents of each directory and file.

## 🏗️ Root Directory Structure

```
demystify-cors/
├── spring-boot/              # Backend API service
├── svelte/                   # Frontend web application
├── bruno/                    # API testing collection
├── README.md                 # Main project documentation
├── CORS_GUIDE.md             # CORS troubleshooting guide
├── PROJECT_STRUCTURE.md      # This file
├── setup.sh                  # Development setup script
├── .gitignore                # Git ignore patterns
└── .gitattributes            # Git file attributes
```

## 🍃 Spring Boot Backend (`/spring-boot`)

The backend is a Spring Boot application providing REST API endpoints for package tracking.

```
spring-boot/
├── src/
│   ├── main/
│   │   ├── java/blog/natta/santa/cors/
│   │   │   ├── CorsApplication.java                # Main Spring Boot application
│   │   │   ├── config/
│   │   │   │   └── CorsConfig.java                 # CORS configuration
│   │   │   └── delivery/
│   │   │       ├── endpoint/
│   │   │       │   └── DeliveryController.java     # REST controller
│   │   │       └── response/
│   │   │           └── TrackingResultResponse.java # Response DTO
│   │   └── resources/
│   │       ├── application.yaml                    # Application configuration
│   │       └── banner.txt                          # Custom ASCII banner
│   └── test/                                       # Test classes (Spring Boot generated)
├── target/                                         # Compiled classes and artifacts
├── .mvn/                                           # Maven wrapper configuration
├── pom.xml                                         # Maven dependencies and configuration
├── mvnw                                            # Maven wrapper script (Unix)
├── mvnw.cmd                                        # Maven wrapper script (Windows)
└── HELP.md                                         # Spring Boot generated help
```

### Key Backend Files

#### `CorsApplication.java`
Main Spring Boot application entry point with `@SpringBootApplication` annotation.

#### `CorsConfig.java`
Configures CORS settings to allow cross-origin requests from the frontend:
- Allows requests from `http://localhost:5173`
- Maps to `/api/**` endpoints
- Demonstrates proper CORS configuration

#### `DeliveryController.java`
REST controller providing the tracking API:
- `GET /api/delivery/status/{trackingId}` - Returns package tracking information
- Returns JSON response with tracking details

#### `TrackingResultResponse.java`
Data Transfer Object (DTO) representing tracking response:
- Uses Java records for immutable data
- Builder pattern for object construction
- Field validation

#### `application.yaml`
Application configuration:
- Custom banner configuration
- Can be extended with environment-specific settings

#### `pom.xml`
Maven configuration including:
- Spring Boot 3.5.0
- Java 24 target
- GraalVM native compilation support
- Web starter dependencies

## 🎨 SvelteKit Frontend (`/svelte`)

The frontend is a SvelteKit application with TypeScript and TailwindCSS.

```
svelte/
├── src/
│   ├── routes/
│   │   ├── +layout.svelte                   # Global layout component
│   │   ├── +page.svelte                     # Main tracking page
│   │   ├── rates/                           # Shipping rates page (placeholder)
│   │   ├── support/                         # Support page (placeholder)
│   │   └── track/                           # Track page (placeholder)
│   ├── lib/                                 # Reusable components and utilities
│   ├── app.html                             # HTML template
│   ├── app.css                              # Global styles
│   └── app.d.ts                             # TypeScript declarations
├── static/                                  # Static assets
├── node_modules/                            # npm dependencies
├── .svelte-kit/                             # SvelteKit build artifacts
├── package.json                             # npm configuration and scripts
├── pnpm-lock.yaml                           # pnpm lockfile
├── svelte.config.js                         # SvelteKit configuration
├── vite.config.ts                           # Vite build configuration
├── tsconfig.json                            # TypeScript configuration
├── tailwind.config.js                       # TailwindCSS configuration (if present)
├── eslint.config.js                         # ESLint configuration
├── .prettierrc                              # Prettier formatting rules
├── .prettierignore                          # Prettier ignore patterns
├── .npmrc                                   # npm configuration
├── .gitignore                               # Git ignore patterns
├── README.md                                # SvelteKit README
└── LICENSE                                  # License file
```

### Key Frontend Files

#### `+page.svelte`
Main application page featuring:
- Package tracking form
- Real-time status display
- Progress timeline visualization
- Error handling and loading states
- Responsive design with TailwindCSS
- Japanese-inspired UI theme

#### `package.json`
Project configuration including:
- SvelteKit and Svelte 5 dependencies
- TypeScript support
- TailwindCSS for styling
- ESLint and Prettier for code quality
- Development scripts

#### `svelte.config.js`
SvelteKit configuration:
- Adapter settings
- Preprocessor configuration
- Build options

#### `vite.config.ts`
Vite build tool configuration:
- SvelteKit plugin
- TailwindCSS integration
- Development server settings

## 🧪 Bruno API Testing (`/bruno`)

API testing collection for the delivery tracking endpoints.

```
bruno/
└── cors-demystify/
    ├── bruno.json                           # Collection configuration
    └── Tracking-Status.bru                  # API test requests
```

### Bruno Files

#### `bruno.json`
Collection metadata and configuration.

#### `Tracking-Status.bru`
Test request for the tracking API:
- GET request to `/api/delivery/status/{trackingId}`
- Example tracking ID: `SHM192992838JP`
- Proper headers configuration

## 📝 Documentation Files

### `README.md`
Comprehensive project documentation including:
- Project overview and features
- Installation and setup instructions
- Running instructions for both applications
- API documentation
- CORS configuration examples
- Learning objectives

### `CORS_GUIDE.md`
Detailed CORS troubleshooting guide covering:
- CORS fundamentals and concepts
- Common error messages and solutions
- Spring Boot CORS configuration examples
- Debugging techniques
- Security considerations
- Production best practices

### `PROJECT_STRUCTURE.md`
This file - detailed project structure documentation.

## 🛠️ Development Scripts

### `setup.sh`
Automated development environment setup script:
- Prerequisites checking (Java, Node.js, pnpm)
- Backend and frontend dependency installation
- Development script generation
- Comprehensive setup validation

## 🚀 Application Flow

### 1. Backend API Flow
```
Request → CorsConfig → DeliveryController → TrackingResultResponse → JSON Response
```

### 2. Frontend Flow
```
User Input → Fetch API → CORS Request → Backend API → Response Processing → UI Update
```

### 3. CORS Flow
```
Frontend (5173) → Preflight OPTIONS → Backend (8080) → CORS Headers → Actual Request
```

## 🎯 Key Learning Areas

### CORS Demonstration
- **Same-origin policy**: Different ports (5173 vs 8080)
- **CORS configuration**: Spring Boot CORS setup
- **Preflight requests**: OPTIONS method handling
- **Security considerations**: Origin restrictions

### Modern Web Development
- **Full-stack architecture**: SvelteKit + Spring Boot
- **Type safety**: TypeScript throughout
- **Modern CSS**: TailwindCSS utility classes
- **Build tools**: Vite for frontend, Maven for backend
- **Package management**: pnpm for frontend, Maven for backend

### Development Practices
- **Clean architecture**: Separated concerns
- **Error handling**: Graceful degradation
- **Responsive design**: Mobile-first approach
- **Code quality**: ESLint, Prettier, TypeScript
- **Testing**: Bruno API collection

## 🔧 Customization Points

### Backend
- Add more endpoints in `DeliveryController`
- Extend CORS configuration in `CorsConfig`
- Add database integration
- Implement authentication/authorization

### Frontend
- Add new routes in `src/routes/`
- Create reusable components in `src/lib/`
- Extend styling with TailwindCSS
- Add state management (if needed)

### Development
- Modify `setup.sh` for additional tools
- Add Docker configuration
- Implement CI/CD pipelines
- Add automated testing

---

This structure provides a solid foundation for learning CORS concepts while demonstrating modern full-stack development practices. 🚀
