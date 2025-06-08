package blog.natta.santa.cors.delivery.response;

import java.util.Objects;

public record TrackingResultResponse(
        String id,
        String status,
        String title,
        String location,
        String timestamp,
        String estimatedDelivery
) {
    public static class Builder {
        private String id;
        private String status;
        private String title;
        private String location;
        private String timestamp;
        private String estimatedDelivery;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder estimatedDelivery(String estimatedDelivery) {
            this.estimatedDelivery = estimatedDelivery;
            return this;
        }

        public TrackingResultResponse build() {
            validateRequiredFields();
            return new TrackingResultResponse(
                    id, status, title,
                    location, timestamp, estimatedDelivery
            );
        }

        private void validateRequiredFields() {
            Objects.requireNonNull(id, "id must not be null");
            Objects.requireNonNull(status, "status must not be null");
            Objects.requireNonNull(title, "title must not be null");
            Objects.requireNonNull(location, "location must not be null");
            Objects.requireNonNull(timestamp, "timestamp must not be null");
            Objects.requireNonNull(estimatedDelivery, "estimatedDelivery must not be null");
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
