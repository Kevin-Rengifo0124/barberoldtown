package com.oldtownbarber.booking_service.controller;

import com.oldtownbarber.booking_service.domain.BookingStatus;
import com.oldtownbarber.booking_service.dto.*;
import com.oldtownbarber.booking_service.mapper.BookingMapper;
import com.oldtownbarber.booking_service.model.Booking;
import com.oldtownbarber.booking_service.model.SalonReport;
import com.oldtownbarber.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestParam Long salonId, @RequestBody BookingRequest bookingRequest) throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(salonId);
        salonDTO.setOpenTime(LocalTime.now());
        salonDTO.setCloseTime(LocalTime.now().plusHours(12));


        Set<ServiceDTO> services = new HashSet<>();
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(1L);
        serviceDTO.setPrice(399);
        serviceDTO.setDuration(45);
        serviceDTO.setName("Corte de pelo para hombres");
        services.add(serviceDTO);

        Booking booking = bookingService.createBooking(bookingRequest, userDTO, salonDTO, services);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingByCustomer() {

        List<Booking> bookings = bookingService.getBookingsByCustomer(1L);

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingBySalon() {

        List<Booking> bookings = bookingService.getBookingsBySalon(1L);

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings) {

        return bookings.stream().map(booking -> {
            return BookingMapper.toDto(booking);
        }).collect(Collectors.toSet());
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long bookingId) throws Exception {

        Booking booking = bookingService.getBookingById(bookingId);

        return ResponseEntity.ok(BookingMapper.toDto(booking));
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(@PathVariable Long bookingId, @RequestParam BookingStatus bookingStatus) throws Exception {

        Booking booking = bookingService.updateBooking(bookingId, bookingStatus);

        return ResponseEntity.ok(BookingMapper.toDto(booking));
    }

    @GetMapping("/slots/salon/{salonId}/date/{date}")
    public ResponseEntity<List<BookingSlotDTO>> getBookedSlot(@PathVariable Long salonId, @RequestParam(required = false) LocalDate date) throws Exception {

        List<Booking> bookings = bookingService.getBookingsByDate(date, salonId);
        List<BookingSlotDTO> bookingSlotDTOS = bookings.stream().map(booking -> {
            BookingSlotDTO slotDTO = new BookingSlotDTO();
            slotDTO.setStartTime(booking.getStartTime());
            slotDTO.setEndTime(booking.getEndTime());
            return slotDTO;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(bookingSlotDTOS);
    }

    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport() throws Exception {

        SalonReport salonReport = bookingService.getSalonReport(1L);
        return ResponseEntity.ok(salonReport);
    }

}
