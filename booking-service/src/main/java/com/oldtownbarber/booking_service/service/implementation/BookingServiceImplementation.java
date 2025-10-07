package com.oldtownbarber.booking_service.service.implementation;

import com.oldtownbarber.booking_service.domain.BookingStatus;
import com.oldtownbarber.booking_service.dto.BookingRequest;
import com.oldtownbarber.booking_service.dto.SalonDTO;
import com.oldtownbarber.booking_service.dto.ServiceDTO;
import com.oldtownbarber.booking_service.dto.UserDTO;
import com.oldtownbarber.booking_service.model.Booking;
import com.oldtownbarber.booking_service.model.SalonReport;
import com.oldtownbarber.booking_service.repository.BookingRepository;
import com.oldtownbarber.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookingServiceImplementation implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingRequest booking, UserDTO user, SalonDTO salon, Set<ServiceDTO> serviceDTOSet) {
        // Calcula la duración total de todos los servicios en el conjunto serviceDTOSet
        // 1. Convierte el Set<ServiceDTO> en un Stream para procesamiento funcional.
        // 2. Para cada ServiceDTO, obtiene su duración usando getDuration().
        // 3. Convierte los valores a un IntStream (flujos de enteros).
        // 4. Suma todos los valores del IntStream y asigna el resultado a totalDuration.
        int totalDuration = serviceDTOSet.stream().mapToInt(ServiceDTO::getDuration).sum();

        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);
        return null;
    }

    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        return List.of();
    }

    @Override
    public List<Booking> getBookingsBySalon(Long salonId) {
        return List.of();
    }

    @Override
    public Booking getBookingById(Long id) {
        return null;
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus bookingStatus) {
        return null;
    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate date, Long salonId) {
        return List.of();
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        return null;
    }
}
