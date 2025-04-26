package com.examly.springapp.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import com.examly.springapp.model.Payment;
import com.examly.springapp.repository.PaymentRepo;

@Service
public class PaymentServiceImpl {
    
    private final PaymentRepo paymentRepository;

    public PaymentServiceImpl(PaymentRepo paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getPaymentsByUsername(String username) {
        return paymentRepository.findByUsername(username);
    }

    public BigDecimal getTotalPayments() {
        return paymentRepository.getTotalPayments();
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment); // Persist payment
    }
}
