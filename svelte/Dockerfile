# Multi-stage build for SvelteKit application
FROM node:22-alpine AS builder

# Install pnpm
RUN npm install -g pnpm

# Set working directory
WORKDIR /app

# Copy package files
COPY package.json pnpm-lock.yaml ./

# Install dependencies
RUN pnpm install --frozen-lockfile

# Copy source code
COPY . .

# Build the application
RUN pnpm build

# Runtime stage
FROM node:22-alpine AS runtime

# Install pnpm
RUN npm install -g pnpm

# Create non-root user
RUN addgroup -g 10001 svelte && adduser -u 10001 -G svelte -s /bin/sh -D svelte

# Set working directory
WORKDIR /app

# Copy package files
COPY package.json pnpm-lock.yaml ./

# Install only production dependencies
RUN pnpm install --prod --frozen-lockfile

# Copy built application from builder stage
COPY --from=builder /app/build build/
COPY --from=builder /app/package.json .

# Change ownership to non-root user
RUN chown -R svelte:svelte /app

# Switch to non-root user
USER svelte

# Expose port
EXPOSE 5173

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:5173/ || exit 1

# Set environment variables
ENV NODE_ENV=production
ENV HOST=0.0.0.0
ENV PORT=5173

# Start the application
CMD ["node", "build"]
