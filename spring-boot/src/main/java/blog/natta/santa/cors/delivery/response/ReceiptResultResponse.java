package blog.natta.santa.cors.delivery.response;

import java.util.Objects;

public record ReceiptResultResponse(
        String id,
        float deliveryFee,
        float tax,
        float total
) {
    public static class Builder {
        private String id;
        private Float deliveryFee;
        private Float tax;
        private Float total;

        public ReceiptResultResponse.Builder id(String id) {
            this.id = id;
            return this;
        }

        public ReceiptResultResponse.Builder deliveryFee(Float deliveryFee) {
            this.deliveryFee = deliveryFee;
            return this;
        }

        public ReceiptResultResponse.Builder tax(Float tax) {
            this.tax = tax;
            return this;
        }

        public ReceiptResultResponse.Builder total(Float total) {
            this.total = total;
            return this;
        }

        public ReceiptResultResponse build() {
            validateRequiredFields();
            return new ReceiptResultResponse(
                    id, deliveryFee, tax, total
            );
        }

        private void validateRequiredFields() {
            Objects.requireNonNull(id, "id must not be null");
            Objects.requireNonNull(deliveryFee, "status must not be null");
            Objects.requireNonNull(tax, "title must not be null");
            Objects.requireNonNull(total, "location must not be null");
        }
    }

    public static ReceiptResultResponse.Builder builder() {
        return new ReceiptResultResponse.Builder();
    }
}
