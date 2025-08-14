-- Drop foreign key constraint from booking.review_id (adjust constraint name as per your DB)
ALTER TABLE booking
    DROP FOREIGN KEY FK_BOOKING_ON_REVIEW;

-- Drop the old review_id column from booking
ALTER TABLE booking
    DROP COLUMN review_id;

-- Add booking_id column to review (change table name if different)
ALTER TABLE booking_review
    ADD COLUMN booking_id BIGINT NOT NULL;

-- Add foreign key constraint to review.booking_id referencing booking.id
ALTER TABLE booking_review
    ADD CONSTRAINT FK_REVIEW_BOOKING
        FOREIGN KEY (booking_id)
            REFERENCES booking(id);
