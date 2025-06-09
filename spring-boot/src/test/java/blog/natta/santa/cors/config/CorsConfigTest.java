package blog.natta.santa.cors.config;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

class CorsConfigTest {
    private final CorsConfig mCorsConfig = new CorsConfig();

    @Test
    void When_AddCorsMappings_Then_ReturnSuccess() {
        // Arrange
        final CorsRegistry corsRegistry = new CorsRegistry();

        // Act & Assert
        assertDoesNotThrow(mCorsConfig::corsConfigurer);
    }
}