package com.oldtownbarber.booking_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingRequest {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Set<Long> serviceIds;
}
