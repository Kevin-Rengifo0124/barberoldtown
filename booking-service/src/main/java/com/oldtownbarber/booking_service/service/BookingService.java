package com.oldtownbarber.booking_service.service;

import com.oldtownbarber.booking_service.dto.BookingRequest;
import com.oldtownbarber.booking_service.dto.SalonDTO;
import com.oldtownbarber.booking_service.dto.ServiceDTO;
import com.oldtownbarber.booking_service.dto.UserDTO;
import com.oldtownbarber.booking_service.model.Booking;

import java.util.Set;

public interface BookingService {

    Booking createBooking(BookingRequest booking, UserDTO user, SalonDTO salon, Set<ServiceDTO> serviceDTOSet);
}
