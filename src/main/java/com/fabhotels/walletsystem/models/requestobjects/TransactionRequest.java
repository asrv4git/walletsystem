package com.fabhotels.walletsystem.models.requestobjects;

import com.fabhotels.walletsystem.models.entity.Transaction;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TransactionRequest {

    @Min(value = 1, message = "The value must be 1 or more")
    private double transactionAmount;

    @NotNull
    private Transaction.TransactionType transactionType;

    @Email
    private String transferToTheUserAccountWithEmail;

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

    public String getTransferToTheUserAccountWithEmail() {
        return transferToTheUserAccountWithEmail;
    }

    public void setTransferToTheUserAccountWithEmail(String transferToTheUserAccountWithEmail) {
        this.transferToTheUserAccountWithEmail = transferToTheUserAccountWithEmail;
    }
}
