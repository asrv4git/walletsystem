package com.fabhotels.walletsystem.models.dto;

import com.fabhotels.walletsystem.models.entity.Transaction;

import java.sql.Timestamp;

public class TransactionDto {

    private double transactionAmount;
    private Timestamp transactionTimestamp;
    private Transaction.TransactionType transactionType;

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Timestamp getTransactionTimestamp() {
        return transactionTimestamp;
    }

    public void setTransactionTimestamp(Timestamp transactionTimestamp) {
        this.transactionTimestamp = transactionTimestamp;
    }

    public Transaction.TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Transaction.TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionDto that = (TransactionDto) o;

        if (Double.compare(that.transactionAmount, transactionAmount) != 0) return false;
        if (transactionTimestamp != null ? !transactionTimestamp.equals(that.transactionTimestamp) : that.transactionTimestamp != null)
            return false;
        return transactionType == that.transactionType;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(transactionAmount);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (transactionTimestamp != null ? transactionTimestamp.hashCode() : 0);
        result = 31 * result + (transactionType != null ? transactionType.hashCode() : 0);
        return result;
    }
}
