package com.uber.UberAuthService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Booking extends BaseModel {

    @Enumerated(value = EnumType.STRING) // Default would be EnumType.ORDINAL, i.e. tiny int.
    private BookingStatus bookingStatus;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date endTime;

    private Double totalDistance;

    @ManyToOne(fetch = FetchType.LAZY) // The Default fetch mode for MANY to ONE and MANY to MANY is EAGER.
    private Driver driver; // Many side has a foreign key

    @ManyToOne(fetch = FetchType.LAZY)
    private Passenger passenger;

}
/*
    The booking table is going to act as a through table, as it provides many to many relationship between passanger and
    driver.
*/