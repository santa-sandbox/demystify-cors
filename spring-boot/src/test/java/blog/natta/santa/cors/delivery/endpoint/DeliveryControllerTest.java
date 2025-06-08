package blog.natta.santa.cors.delivery.endpoint;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class DeliveryControllerTest {

    DeliveryController subjectOfTest = new DeliveryController();

    @Test
    void Given_TrackingId_When_trackDelivery_Then_ReturnTrackingResult() {
        // Arrange
        var fakeTrackingId = "123456789";

        // Act
        var result = subjectOfTest.trackDelivery(fakeTrackingId);

        // Assert
        assertThat(result.trackingId(), equalTo(fakeTrackingId));
        assertThat(result.status(), equalTo("success"));
        assertThat(result.message(), equalTo("ok"));
    }
}