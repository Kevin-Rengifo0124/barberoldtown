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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImplementation implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingRequest booking, UserDTO user, SalonDTO salon, Set<ServiceDTO> serviceDTOSet) throws Exception {
        // Calcula la duración total de todos los servicios en el conjunto serviceDTOSet
        // 1. Convierte el Set<ServiceDTO> en un Stream para procesamiento funcional.
        // 2. Para cada ServiceDTO, obtiene su duración usando getDuration().
        // 3. Convierte los valores a un IntStream (flujos de enteros).
        // 4. Suma todos los valores del IntStream y asigna el resultado a totalDuration.
        int totalDuration = serviceDTOSet.stream().mapToInt(ServiceDTO::getDuration).sum();

        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

        Boolean isSlotAvailable = isTimeSlotAvailable(salon, bookingStartTime, bookingEndTime);

        int totalPrice = serviceDTOSet.stream().mapToInt(ServiceDTO::getPrice).sum();

        Set<Long> idList = serviceDTOSet.stream().map(ServiceDTO::getId).collect(Collectors.toSet());

        Booking newBooking = new Booking();
        newBooking.setCustomerId(user.getId());
        newBooking.setSalonId(salon.getId());
        newBooking.setServiceIds(idList);
        newBooking.setStatus(BookingStatus.PENDING);
        newBooking.setStartTime(bookingStartTime);
        newBooking.setEndTime(bookingEndTime);
        newBooking.setTotalPrices(totalPrice);

        return bookingRepository.save(newBooking);
    }

    public Boolean isTimeSlotAvailable(SalonDTO salonDTO, LocalDateTime bookingStartTime, LocalDateTime bookingEndTime) throws Exception {
        List<Booking> existingBookings = getBookingsBySalon(salonDTO.getId());
        LocalDateTime salonOpenTime = salonDTO.getOpenTime().atDate(bookingStartTime.toLocalDate());
        LocalDateTime salonCloseTime = salonDTO.getCloseTime().atDate(bookingStartTime.toLocalDate());

        // Validar horario del salón
        if (bookingStartTime.isBefore(salonOpenTime) || bookingEndTime.isAfter(salonCloseTime)) {

            throw new Exception("La reserva debe realizarse dentro del horario laboral del salón.");
        }
        // Validar conflictos con reservas existentes
        for (Booking existingBooking : existingBookings) {
            LocalDateTime existingBookingStartTime = existingBooking.getStartTime();
            LocalDateTime existingBookingEndTime = existingBooking.getEndTime();

            // Coincidencia exacta
            if (bookingStartTime.isBefore(existingBookingEndTime) && bookingEndTime.isAfter(existingBookingStartTime)) {

                throw new Exception("La franja horaria no está disponible, elija otra hora.");
            }

            if (bookingStartTime.isEqual(existingBookingStartTime) || bookingEndTime.isEqual(existingBookingEndTime)) {

                throw new Exception("No se puede registrar la reserva: el horario elegido ya está ocupado. Intente con otra hora.");
            }
        }

        return true;
    }

    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingsBySalon(Long salonId) {
        return bookingRepository.findBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(Long id) throws Exception {
        Booking booking = bookingRepository.findById(id).orElse(null);

        if (booking == null) {
            throw new Exception("No se ha encontrado ninguna reserva.");
        }

        return booking;
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus bookingStatus) throws Exception {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(bookingStatus);
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate date, Long salonId) {
        List<Booking> allBoakings = getBookingsBySalon(salonId);

        if (date == null) {
            return allBoakings;
        }

        return allBoakings.stream().filter(booking -> isSameData(booking.getStartTime(), date)).collect(Collectors.toList());
    }

    private boolean isSameData(LocalDateTime dateTime, LocalDate date) {
        return dateTime.toLocalDate().isEqual(date);
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        List<Booking> bookings = getBookingsBySalon(salonId);

        int totalEarnings = bookings.stream().mapToInt(Booking::getTotalPrices).sum();

        Integer totalBooking = bookings.size();

        List<Booking> cancelledBookings = bookings.stream().
                filter(booking -> booking.getStatus().
                        equals(BookingStatus.CANCELLED)).collect(Collectors.toList());

        Double totalRefund = cancelledBookings.stream().mapToDouble(Booking::getTotalPrices).sum();

        SalonReport salonReport = new SalonReport();
        salonReport.setSalonId(salonId);
        salonReport.setCanceledBookings(cancelledBookings.size());
        salonReport.setTotalBookings(totalEarnings);
        salonReport.setTotalEarnings(totalEarnings);
        salonReport.setTotalRefund(totalRefund);
        salonReport.setTotalBookings(totalBooking);
        return salonReport;
    }
}
