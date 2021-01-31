package com.fabhotels.walletsystem.service;

import com.fabhotels.walletsystem.models.dto.TransactionDto;
import com.fabhotels.walletsystem.models.requestobjects.TransactionRequest;

import java.util.Set;

public interface TransactionService {
    Set<TransactionDto> getAllTransactionsById(Long userId);

    void performTransaction(TransactionRequest transactionRequest);
}
