package blog.natta.santa.cors.delivery.endpoint;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blog.natta.santa.cors.TrackingResultResponse;

@RestController
@RequestMapping(
        value = "/api/delivery",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class DeliveryController {
    @GetMapping(value = "/status/{trackingId}")
    public TrackingResultResponse trackDelivery(@PathVariable String trackingId) {
        return new TrackingResultResponse(trackingId, "success", "ok");
    }
}
