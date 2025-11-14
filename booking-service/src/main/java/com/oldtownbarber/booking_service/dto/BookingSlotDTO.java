package com.oldtownbarber.booking_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BookingSlotDTO {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
