package blog.natta.santa.cors.delivery.endpoint;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class ReceiptControllerTest {

    ReceiptController subjectOfTest = new ReceiptController();

    @Test
    void Given_TrackingId_When_queryReceipt_Then_ReturnReceiptResult() {
        // Arrange
        var fakeTrackingId = "123456789";

        // Act
        var result = subjectOfTest.queryReceipt(fakeTrackingId);

        // Assert
        assertThat(result.id(), equalTo(fakeTrackingId));
        assertThat(result.deliveryFee(), equalTo(2450.0f));
        assertThat(result.tax(), equalTo(120.0f));
        assertThat(result.total(), equalTo(2560.0f));
    }
}