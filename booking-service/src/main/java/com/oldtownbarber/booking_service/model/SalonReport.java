package com.oldtownbarber.booking_service.model;

import lombok.Data;

@Data
public class SalonReport {

    private Long salonId;

    private String salonName;

    private Double totalEarnings;

    private Integer totalBookings;

    private Integer canceledBookings;

    private Double totalRefund;
}
