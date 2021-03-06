package com.fabhotels.walletsystem.models.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity(name = "TRANSACTION")

public class Transaction {

    public enum TransactionType{
        ADD,TRANSFER
    }

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "transaction_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "20454623"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "TRANSACTION_ID")
    private long transactionId;

    @ManyToOne
    @JoinColumn(name = "WALLET_ID")
    @NotNull
    private Wallet wallet;

    @NotNull
    @Column(name = "AMOUNT")
    private double transactionAmount;

    @Column(name = "TIMESTAMP")
    @NotNull
    private Timestamp transactionTimestamp;

    @Column(name = "TRANSACTION_TYPE")
    @NotNull
    private TransactionType transactionType;

    public Transaction() {
    }

    public Transaction(Wallet wallet, double transactionAmount, Timestamp transactionTimestamp, TransactionType transactionType){
        this.wallet = wallet;
        this.transactionAmount = transactionAmount;
        this.transactionTimestamp = transactionTimestamp;
        this.transactionType = transactionType;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (transactionId != that.transactionId) return false;
        if (Double.compare(that.transactionAmount, transactionAmount) != 0) return false;
        if (wallet != null ? !wallet.equals(that.wallet) : that.wallet != null) return false;
        if (transactionTimestamp != null ? !transactionTimestamp.equals(that.transactionTimestamp) : that.transactionTimestamp != null)
            return false;
        return transactionType == that.transactionType;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (transactionId ^ (transactionId >>> 32));
        result = 31 * result + (wallet != null ? wallet.hashCode() : 0);
        temp = Double.doubleToLongBits(transactionAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (transactionTimestamp != null ? transactionTimestamp.hashCode() : 0);
        result = 31 * result + (transactionType != null ? transactionType.hashCode() : 0);
        return result;
    }
}
