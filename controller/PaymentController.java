package com.examly.springapp.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Payment;
import com.examly.springapp.service.PaymentServiceImpl;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentServiceImpl paymentService;
    
    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping()
    public ResponseEntity<Payment> addPayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.savePayment(payment); // Save payment to DB
        return ResponseEntity.status(201).body(savedPayment); // Respond with saved payment
    }


    // Get payments by username
    @GetMapping("/user/{username}")
    public ResponseEntity<List<Payment>> getPaymentsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(paymentService.getPaymentsByUsername(username));
    }

    // Get total sum of all payments
    @GetMapping("/sum")
    public ResponseEntity<BigDecimal> getTotalPayments() {
        return ResponseEntity.ok(paymentService.getTotalPayments());
    }
}
