package com.fabhotels.walletsystem.service;

import com.fabhotels.walletsystem.dao.WalletDao;
import com.fabhotels.walletsystem.exceptions.InsufficientBalanceException;
import com.fabhotels.walletsystem.models.dto.TransactionDto;
import com.fabhotels.walletsystem.models.entity.Transaction;
import com.fabhotels.walletsystem.models.entity.Wallet;
import com.fabhotels.walletsystem.models.requestobjects.TransactionRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    WalletDao walletDao;

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    /**
     * get all transaction performed in a users wallet
     *
     * @param userId : userId of user whose transaction summary to be fetched
     * @return the {@link Set<TransactionDto>}
     */
    @Override
    @Transactional
    public Set<TransactionDto> getAllTransactionsById(Long userId) {
        Set<Transaction> allTransactions = userService.getUserById(userId).getWallet().getTransactions();
        Set<TransactionDto> allTransactionDto = allTransactions.stream()
            .map(transaction -> modelMapper.map(transaction,TransactionDto.class))
            .collect(Collectors.toSet());
        return allTransactionDto;
    }

    /**
     * perform transaction in a users wallet
     *
     * @param  transactionRequest {@link TransactionRequest } : userId of user whose transaction summary to be fetched
     * @throws InsufficientBalanceException {@code 400 (Bad Request)} if insufficient balance in wallet to perform transaction
     */
    @Override
    @Transactional
    public void performTransaction(TransactionRequest transactionRequest) {
        Optional<Wallet> optionalWallet = Optional.of(userService.getUserById(transactionRequest.getUserId()).getWallet());
        Wallet wallet = optionalWallet.get();

        log.info("New transaction begins for user with userId: "+transactionRequest.getUserId());
        //check if transaction amount is valid for debit transactions
        if(transactionRequest.getTransactionType().equals(Transaction.TransactionType.DEBIT) &&
                wallet.getWalletBalance()<transactionRequest.getTransactionAmount()) {
            log.info("Transaction failed-Insufficient balance in the wallet of user with id: " + transactionRequest.getUserId() + " for transaction to happen");
            throw new InsufficientBalanceException("Insufficient balance in wallet_id: " + transactionRequest.getUserId()
                    + " for transaction to happen");
        }

        //perform transaction

        //credit
        if(transactionRequest.getTransactionType().equals(Transaction.TransactionType.CREDIT))
            wallet.setWalletBalance(wallet.getWalletBalance()+transactionRequest.getTransactionAmount());

        //debit
        else
            wallet.setWalletBalance(wallet.getWalletBalance()-transactionRequest.getTransactionAmount());

        //update transaction ledger
        wallet.getTransactions().add(new Transaction(wallet,transactionRequest.getTransactionAmount(),
                Timestamp.from(Instant.now()),transactionRequest.getTransactionType()));

        walletDao.save(wallet);

        log.info("Transaction completed successfully for user with userId: "+transactionRequest.getUserId());
    }
}
