package com.oldtownbarber.payment_service.service;

import com.oldtownbarber.payment_service.domain.PaymentMethod;
import com.oldtownbarber.payment_service.payload.dto.BookingDTO;
import com.oldtownbarber.payment_service.payload.dto.UserDTO;
import com.oldtownbarber.payment_service.model.PaymentOrder;
import com.oldtownbarber.payment_service.payload.response.PaymentLinkResponse;
import com.razorpay.PaymentLink;

public interface PaymentService {

    PaymentLinkResponse createOrder(UserDTO userDTO, BookingDTO bookingDTO, PaymentMethod paymentMethod);

    PaymentOrder getPaymentOrderById(Long id);

    PaymentOrder getPaymentOrderByPaymentId(String paymentId);

    PaymentLink createRazorpayPaymentLink(UserDTO userDTO, Long amount, Long orderId);
    
    String createStripePaymentLink(UserDTO userDTO, Long amount, Long orderId);
}

