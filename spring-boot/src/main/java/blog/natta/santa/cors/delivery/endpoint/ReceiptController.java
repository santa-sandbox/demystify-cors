package blog.natta.santa.cors.delivery.endpoint;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blog.natta.santa.cors.delivery.response.ReceiptResultResponse;

@RestController
@RequestMapping(
        value = "/api/v2/receipt",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class ReceiptController {
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(value = "/{trackingId}")
    public ReceiptResultResponse queryReceipt(@PathVariable String trackingId) {
        return ReceiptResultResponse.builder()
                .id(trackingId)
                .deliveryFee(2450.0f)
                .tax(120.0f)
                .total(2560.0f)
                .build();
    }
}
