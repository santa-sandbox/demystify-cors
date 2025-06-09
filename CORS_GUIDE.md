# 🔍 CORS Troubleshooting Guide

A comprehensive guide to understanding and debugging Cross-Origin Resource Sharing (CORS) issues in web applications.

## 📚 What is CORS?

Cross-Origin Resource Sharing (CORS) is a security mechanism implemented by web browsers that restricts web pages from making requests to a different domain, protocol, or port than the one serving the web page. This is known as the **Same-Origin Policy**.

### Same-Origin Policy Examples

| Origin A | Origin B | Same Origin? | Reason |
|----------|----------|--------------|---------|
| `http://localhost:5173` | `http://localhost:8080` | ❌ No | Different ports |
| `https://example.com` | `http://example.com` | ❌ No | Different protocols |
| `https://api.example.com` | `https://example.com` | ❌ No | Different subdomains |
| `https://example.com/api` | `https://example.com/app` | ✅ Yes | Same origin, different paths |

## 🚨 Common CORS Error Messages

### Browser Console Errors

```bash
Access to fetch at 'http://localhost:8080/api/delivery/status/123'
from origin 'http://localhost:5173' has been blocked by CORS policy:
No 'Access-Control-Allow-Origin' header is present on the requested resource.
```

```bash
Access to fetch at 'http://localhost:8080/api/delivery/status/123'
from origin 'http://localhost:5173' has been blocked by CORS policy:
The request client is not a secure context and the resource is on a secure origin.
```

```bash
Access to fetch at 'http://localhost:8080/api/delivery/status/123'
from origin 'http://localhost:5173' has been blocked by CORS policy:
Request header field 'authorization' is not allowed by Access-Control-Allow-Headers
in preflight response.
```

## 🔧 CORS Configuration in Spring Boot

### Basic CORS Configuration

```java
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
```

### Controller-Level CORS

```java
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class DeliveryController {

    @GetMapping("/api/delivery/status/{trackingId}")
    public TrackingResultResponse trackDelivery(@PathVariable String trackingId) {
        // Implementation
    }
}
```

### Method-Level CORS

```java
@RestController
public class DeliveryController {

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/api/delivery/status/{trackingId}")
    public TrackingResultResponse trackDelivery(@PathVariable String trackingId) {
        // Implementation
    }
}
```

## 🛠️ Debugging CORS Issues

### Step 1: Check Browser Developer Tools

1. Open browser DevTools (F12)
2. Go to **Network** tab
3. Look for failed requests with CORS errors
4. Check the **Response Headers** for CORS-related headers

### Step 2: Inspect CORS Headers

**Required Response Headers:**
```
Access-Control-Allow-Origin: http://localhost:5173
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS
Access-Control-Allow-Headers: Content-Type, Authorization
Access-Control-Allow-Credentials: true
```

### Step 3: Understanding Preflight Requests

For complex requests (non-simple requests), browsers send a preflight OPTIONS request:

**Preflight Request:**
```http
OPTIONS /api/delivery/status/123 HTTP/1.1
Origin: http://localhost:5173
Access-Control-Request-Method: POST
Access-Control-Request-Headers: content-type, authorization
```

**Preflight Response:**
```http
HTTP/1.1 200 OK
Access-Control-Allow-Origin: http://localhost:5173
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS
Access-Control-Allow-Headers: content-type, authorization
Access-Control-Max-Age: 3600
```

## 🔍 Testing CORS

### Using curl

```bash
# Test basic CORS
curl -H "Origin: http://localhost:5173" \
     -H "Access-Control-Request-Method: GET" \
     -X OPTIONS \
     http://localhost:8080/api/delivery/status/123

# Test actual request
curl -H "Origin: http://localhost:5173" \
     -X GET \
     http://localhost:8080/api/delivery/status/123
```

### Using Bruno/Postman

1. Set the `Origin` header to `http://localhost:5173`
2. Make the request to your backend
3. Check response headers for CORS configuration

## ⚠️ Common CORS Mistakes

### 1. Wildcard with Credentials

```java
// ❌ WRONG - Cannot use wildcard with credentials
registry.addMapping("/api/**")
        .allowedOrigins("*")
        .allowCredentials(true);

// ✅ CORRECT - Specify exact origins when using credentials
registry.addMapping("/api/**")
        .allowedOrigins("http://localhost:5173", "https://myapp.com")
        .allowCredentials(true);
```

### 2. Missing OPTIONS Method

```java
// ❌ WRONG - Missing OPTIONS for preflight
.allowedMethods("GET", "POST", "PUT", "DELETE")

// ✅ CORRECT - Include OPTIONS for preflight
.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
```

### 3. Incorrect Origin Format

```java
// ❌ WRONG - Missing protocol
.allowedOrigins("localhost:5173")

// ❌ WRONG - Trailing slash
.allowedOrigins("http://localhost:5173/")

// ✅ CORRECT - Proper format
.allowedOrigins("http://localhost:5173")
```

## 🌍 Production CORS Configuration

### Environment-Based Configuration

```java
@Configuration
public class CorsConfig {

    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}
```

**application.yaml:**
```yaml
app:
  cors:
    allowed-origins:
      - "http://localhost:5173"      # Development
      - "https://myapp.com"          # Production
      - "https://staging.myapp.com"  # Staging
```

## 🧪 CORS Testing Scenarios

### Scenario 1: Development Environment
- **Frontend**: `http://localhost:5173`
- **Backend**: `http://localhost:8080`
- **Expected**: Should work with proper CORS config

### Scenario 2: Different Ports
- **Frontend**: `http://localhost:3000`
- **Backend**: `http://localhost:8080`
- **Expected**: CORS error without proper configuration

### Scenario 3: HTTPS vs HTTP
- **Frontend**: `https://localhost:5173`
- **Backend**: `http://localhost:8080`
- **Expected**: Mixed content error + CORS issues

### Scenario 4: Production
- **Frontend**: `https://myapp.com`
- **Backend**: `https://api.myapp.com`
- **Expected**: Should work with proper CORS config

## 🔒 Security Considerations

### Don't Use Wildcards in Production

```java
// ❌ DANGEROUS - Too permissive
.allowedOrigins("*")

// ✅ SECURE - Specific origins only
.allowedOrigins("https://myapp.com", "https://admin.myapp.com")
```

### Limit HTTP Methods

```java
// ❌ TOO BROAD - Allows all methods
.allowedMethods("*")

// ✅ SPECIFIC - Only needed methods
.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
```

### Be Careful with Headers

```java
// ❌ POTENTIALLY RISKY - Allows any header
.allowedHeaders("*")

// ✅ MORE SECURE - Specific headers
.allowedHeaders("Content-Type", "Authorization", "X-Requested-With")
```

## 🚀 Quick Fixes

### Fix 1: Enable CORS for All Origins (Development Only)

```java
@Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**").allowedOrigins("*");
        }
    };
}
```

### Fix 2: Add CORS Headers Manually

```java
@RestController
public class DeliveryController {

    @GetMapping("/api/delivery/status/{trackingId}")
    public ResponseEntity<TrackingResultResponse> trackDelivery(
            @PathVariable String trackingId) {

        TrackingResultResponse response = // ... get response

        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:5173")
                .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
                .body(response);
    }
}
```

### Fix 3: Proxy Configuration (Frontend)

**Vite Configuration (vite.config.ts):**
```typescript
export default defineConfig({
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '/api')
      }
    }
  }
});
```

## 📋 CORS Checklist

- [ ] Backend allows the frontend's origin
- [ ] All required HTTP methods are allowed
- [ ] All required headers are allowed
- [ ] OPTIONS method is included for preflight requests
- [ ] Credentials setting matches frontend requirements
- [ ] No wildcard (*) with credentials in production
- [ ] CORS headers are present in actual responses
- [ ] Preflight responses return 200 OK status

## 🔗 Useful Resources

- [MDN CORS Documentation](https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS)
- [Spring Boot CORS Reference](https://spring.io/guides/gs/rest-service-cors/)
- [CORS Tester Tool](https://cors-test.codehappy.dev/)
- [Chrome DevTools Network Tab](https://developers.google.com/web/tools/chrome-devtools/network)

---

Remember: CORS is a browser security feature, not a server security feature. It protects users, not servers! 🛡️
