package blog.natta.santa.cors.delivery.endpoint;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DeliveryController.class)
class DeliveryEndpointIntTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void Given_TrackingId_When_CallEndpoint_trackDelivery_Then_ReturnTrackingResult() throws Exception {
        // Arrange
        var fakeTrackingId = "123456789";

        // Act & Assert
        mockMvc.perform(get("/api/delivery/status/{trackingId}", fakeTrackingId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.trackingId").value(fakeTrackingId))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("ok"));
    }
}
