package com.oldtownbarber.booking_service.service;

import com.oldtownbarber.booking_service.domain.BookingStatus;
import com.oldtownbarber.booking_service.dto.BookingRequest;
import com.oldtownbarber.booking_service.dto.SalonDTO;
import com.oldtownbarber.booking_service.dto.ServiceDTO;
import com.oldtownbarber.booking_service.dto.UserDTO;
import com.oldtownbarber.booking_service.model.Booking;
import com.oldtownbarber.booking_service.model.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking createBooking(BookingRequest booking, UserDTO user, SalonDTO salon, Set<ServiceDTO> serviceDTOSet);

    List<Booking> getBookingsByCustomer(Long customerId);

    List<Booking> getBookingsBySalon(Long salonId);

    Booking getBookingById(Long id);

    Booking updateBooking(Long bookingId, BookingStatus bookingStatus);

    List<Booking> getBookingsByDate(LocalDate date, Long salonId);

    SalonReport getSalonReport(Long salonId);
}
