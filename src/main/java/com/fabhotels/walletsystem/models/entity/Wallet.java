package com.fabhotels.walletsystem.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity(name="WALLET")
public class Wallet {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "wallet_sequence"),
                    @Parameter(name = "initial_value", value = "1002000"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "WALLET_ID", updatable = false, nullable = false)
    private Long walletId;

    @Column(name="WALLET_BALANCE", nullable = false)
    private double walletBalance = 0.0;

    @OneToOne
    //set the name of foreign key
    @JoinColumn(name = "WALLET_USER_ID",nullable = false)
    @JsonIgnore
    private User walletOwner;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(name = "transactions_in_wallet", joinColumns = @JoinColumn
            (name = "transaction_id"), inverseJoinColumns = @JoinColumn(name = "wallet_id"))
    private Set<Transaction> transactions = new LinkedHashSet<>();

    public Long getWalletId() {
        return walletId;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public User getWalletOwner() {
        return walletOwner;
    }

    public void setWalletOwner(User walletOwner) {
        this.walletOwner = walletOwner;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "walletId=" + walletId +
                ", walletBalance=" + walletBalance +
                ", walletOwner=" + walletOwner.getUsername() +
                '}';
    }
}
