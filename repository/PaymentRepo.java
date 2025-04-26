package com.examly.springapp.repository;

import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Payment;
import java.util.*;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaymentCode(String paymentCode);

    // Find all payments by username
    List<Payment> findByUsername(String username);

    // Get sum of all payments
    @Query("SELECT SUM(p.totalPayment) FROM Payment p")
    BigDecimal getTotalPayments();
}
