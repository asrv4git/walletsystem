package com.fabhotels.walletsystem.service;

import com.fabhotels.walletsystem.models.dto.TransactionDto;
import com.fabhotels.walletsystem.models.requestobjects.TransactionRequest;

import java.util.Set;

public interface WalletService {
    Set<TransactionDto> getAllTransactionInAUsersWallet(String userName);
    void performTransaction(String userName, TransactionRequest transactionRequest);
    Double getCurrentBalanceInUserAccount(String userName);
}
