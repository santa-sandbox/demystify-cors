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
        assertThat(result.id(), equalTo(fakeTrackingId));
        assertThat(result.status(), equalTo("processing"));
        assertThat(result.title(), equalTo("Parcel is being processed"));
        assertThat(result.location(), equalTo("Aichi"));
        assertThat(result.timestamp(), equalTo("2025-06-19 18:00:00"));
        assertThat(result.estimatedDelivery(), equalTo("2025-06-25 19:00:00"));
    }
}