ALTER TABLE passenger
    ADD email VARCHAR(255) NOT NULL UNIQUE,
    ADD password VARCHAR(255) NOT NULL,
    DROP phone_number,
    ADD phone_number VARCHAR(255) NOT NULL UNIQUE
;