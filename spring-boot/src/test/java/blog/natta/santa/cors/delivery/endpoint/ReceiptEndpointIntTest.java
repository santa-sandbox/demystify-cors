package blog.natta.santa.cors.delivery.endpoint;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class ReceiptEndpointIntTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void Given_TrackingId_When_CallEndpoint_queryReceipt_Then_ReturnReceiptResult() throws Exception {
        // Arrange
        var fakeTrackingId = "123456789";

        // Act & Assert
        mockMvc.perform(get("/api/v2/receipt/{trackingId}", fakeTrackingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(fakeTrackingId))
                .andExpect(jsonPath("$.deliveryFee").value(2450))
                .andExpect(jsonPath("$.tax").value(120))
                .andExpect(jsonPath("$.total").value(2560));
    }

    @Test
    void Given_BadOrigin_When_CalEndpoint_api_Then_GlobalCorsCauseException() throws Exception {
        var fakeTrackingId = "123456789";
        var response = mockMvc.perform(options("/api/v2/receipt/{trackingId}", fakeTrackingId)
                        .header("Access-Control-Request-Method", "GET")
                        .header("Origin", "http://www.iambadorigin.com"))
                .andExpect(status().isForbidden())
                .andReturn();

        String content = response.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(content, equalTo("Invalid CORS request"));
    }
}
