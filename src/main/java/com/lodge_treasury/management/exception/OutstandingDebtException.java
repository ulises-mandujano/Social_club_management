package com.lodge_treasury.management.exception;

import java.math.BigDecimal;

public class OutstandingDebtException extends RuntimeException {
    private final BigDecimal debtAmount;

    public OutstandingDebtException(Integer masonId, BigDecimal debtAmount) {
        super (String.format("Mason with ID %d has outstanding debt of %s. Cannot restore.", masonId, debtAmount));
        this.debtAmount = debtAmount;
    }
}
