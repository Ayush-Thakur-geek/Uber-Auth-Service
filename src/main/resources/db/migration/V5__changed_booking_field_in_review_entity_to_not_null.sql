ALTER TABLE booking_review
ADD CONSTRAINT uc_booking_review_booking UNIQUE (booking_id);

ALTER TABLE booking_review
MODIFY booking_id BIGINT NOT NULL;