package com.lodge_treasury.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "income_transactions")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class IncomeTransactions extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", unique = true, nullable = false)
    private Integer transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mason_id", nullable = false)
    private Mason mason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concept", nullable = false)
    private IncomeConcepts incomeConcept;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "payment_method", nullable = false, length = 30)
    private String paymentMethod;

    @Column(name = "receipt_number", nullable = false, length = 30)
    private String receiptNumber;

    @Column(name = "notes")
    private String notes;
}
