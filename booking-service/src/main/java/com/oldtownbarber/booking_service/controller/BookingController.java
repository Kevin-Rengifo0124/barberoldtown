package com.oldtownbarber.booking_service.controller;

import com.oldtownbarber.booking_service.domain.BookingStatus;
import com.oldtownbarber.booking_service.dto.*;
import com.oldtownbarber.booking_service.mapper.BookingMapper;
import com.oldtownbarber.booking_service.model.Booking;
import com.oldtownbarber.booking_service.model.SalonReport;
import com.oldtownbarber.booking_service.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Bookings", description = "API para la gestión de reservas de servicios")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @Operation(summary = "Crear una nueva reserva", description = "Crea una nueva reserva para un cliente en un salón específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o franja horaria no disponible",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Salón no encontrado",
                    content = @Content)
    })
    public ResponseEntity<Booking> createBooking(
            @RequestParam @Parameter(description = "ID del salón", required = true) Long salonId,
            @RequestBody @Parameter(description = "Datos de la reserva", required = true) BookingRequest bookingRequest) throws Exception {

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
    @Operation(summary = "Obtener reservas por cliente", description = "Obtiene todas las reservas realizadas por el cliente autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas obtenidas exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado",
                    content = @Content)
    })
    public ResponseEntity<Set<BookingDTO>> getBookingByCustomer() {
        List<Booking> bookings = bookingService.getBookingsByCustomer(1L);
        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    @GetMapping("/salon")
    @Operation(summary = "Obtener reservas por salón", description = "Obtiene todas las reservas de un salón específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas obtenidas exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Salón no encontrado",
                    content = @Content)
    })
    public ResponseEntity<Set<BookingDTO>> getBookingBySalon() {
        List<Booking> bookings = bookingService.getBookingsBySalon(1L);
        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings) {
        return bookings.stream().map(BookingMapper::toDto).collect(Collectors.toSet());
    }

    @GetMapping("/{bookingId}")
    @Operation(summary = "Obtener reserva por ID", description = "Obtiene los detalles de una reserva específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada",
                    content = @Content)
    })
    public ResponseEntity<BookingDTO> getBookingById(
            @PathVariable @Parameter(description = "ID de la reserva", required = true) Long bookingId) throws Exception {
        Booking booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(BookingMapper.toDto(booking));
    }

    @PutMapping("/{bookingId}/status")
    @Operation(summary = "Actualizar estado de reserva", description = "Actualiza el estado de una reserva (PENDING, CONFIRMED, CANCELLED)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Estado inválido",
                    content = @Content)
    })
    public ResponseEntity<BookingDTO> updateBookingStatus(
            @PathVariable @Parameter(description = "ID de la reserva", required = true) Long bookingId,
            @RequestParam @Parameter(description = "Nuevo estado de la reserva", required = true) BookingStatus bookingStatus) throws Exception {
        Booking booking = bookingService.updateBooking(bookingId, bookingStatus);
        return ResponseEntity.ok(BookingMapper.toDto(booking));
    }

    @GetMapping("/slots/salon/{salonId}/date/{date}")
    @Operation(summary = "Obtener franjas horarias reservadas", description = "Obtiene todas las franjas horarias ya reservadas para un salón en una fecha específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Franjas horarias obtenidas exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingSlotDTO.class))),
            @ApiResponse(responseCode = "404", description = "Salón no encontrado",
                    content = @Content)
    })
    public ResponseEntity<List<BookingSlotDTO>> getBookedSlot(
            @PathVariable @Parameter(description = "ID del salón", required = true) Long salonId,
            @RequestParam(required = false) @Parameter(description = "Fecha para consultar disponibilidad (formato: YYYY-MM-DD)") LocalDate date) throws Exception {
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
    @Operation(summary = "Obtener reporte del salón", description = "Obtiene un reporte detallado con estadísticas de reservas y ganancias del salón")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SalonReport.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Salón no encontrado",
                    content = @Content)
    })
    public ResponseEntity<SalonReport> getSalonReport() throws Exception {
        SalonReport salonReport = bookingService.getSalonReport(1L);
        return ResponseEntity.ok(salonReport);
    }
}