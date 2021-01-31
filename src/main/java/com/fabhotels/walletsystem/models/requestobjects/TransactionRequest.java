package com.fabhotels.walletsystem.models.requestobjects;

import com.fabhotels.walletsystem.models.entity.Transaction;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TransactionRequest {
    @NotNull
    private Long userId;

    @Min(value = 1, message = "The value must be 1 or more")
    private double transactionAmount;

    @NotNull
    private Transaction.TransactionType transactionType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Transaction.TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Transaction.TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
