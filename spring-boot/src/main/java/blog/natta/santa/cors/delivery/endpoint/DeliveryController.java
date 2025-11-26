package blog.natta.santa.cors.delivery.endpoint;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blog.natta.santa.cors.delivery.response.TrackingResultResponse;

@RestController
@RequestMapping(
        value = "/api/v1/delivery",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class DeliveryController {
  @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(value = "/status/{trackingId}")
    public TrackingResultResponse trackDelivery(@PathVariable String trackingId) {
        return TrackingResultResponse.builder()
                .id(trackingId)
                .status("processing")
                .title("Parcel is being processed")
                .location("Aichi")
                .timestamp("2025-06-19 18:00:00")
                .estimatedDelivery("2025-06-25 19:00:00")
                .build();
    }
}
