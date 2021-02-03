package com.fabhotels.walletsystem.service;

import com.fabhotels.walletsystem.dao.UserDao;
import com.fabhotels.walletsystem.dao.WalletDao;
import com.fabhotels.walletsystem.exceptions.InsufficientBalanceException;
import com.fabhotels.walletsystem.models.dto.TransactionDto;
import com.fabhotels.walletsystem.models.entity.Transaction;
import com.fabhotels.walletsystem.models.entity.User;
import com.fabhotels.walletsystem.models.entity.Wallet;
import com.fabhotels.walletsystem.models.requestobjects.TransactionRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WalletServiceImpl implements WalletService  {

    private static final Logger log = LoggerFactory.getLogger(WalletServiceImpl.class);


    @Autowired
    WalletDao walletDao;

    @Autowired
    UserDao userDao;

    @Autowired
    ModelMapper modelMapper;

    /**
     * get all transaction performed in a users wallet
     *
     * @param userName : userName of user whose transaction summary to be fetched
     * @return the {@link Set<TransactionDto>}
     */
    @Override
    public Set<TransactionDto> getAllTransactionInAUsersWallet(String userName) {
        User user = userDao.findUserByUsername(userName).get();
        Wallet wallet = walletDao.findWalletByWalletOwner(user);
        return wallet.getTransactions().stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDto.class))
                .collect(Collectors.toSet());
    }

    /**
     * perform transaction in a users wallet
     * @param userName: userName of user who initiated this transaction
     * @param  transactionRequest {@link TransactionRequest } : transactionRequest
     * @throws InsufficientBalanceException {@code 400 (Bad Request)} if insufficient balance in wallet to perform transaction
     * @throws ResponseStatusException {@code 404 not found} if user is not found
     */
    @Override
    @Transactional
    public void performTransaction(String userName, TransactionRequest transactionRequest) {
        Optional<User> optUser = userDao.findUserByUsername(userName);
        if(optUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No user found for username: "+userName);
        User user = optUser.get();
        Wallet wallet = walletDao.findWalletByWalletOwner(user);

        log.info("New transaction begins in the wallet of user with userName: "+userName);

        //perform transaction

        //Add
        if(transactionRequest.getTransactionType().equals(Transaction.TransactionType.ADD)) {
            wallet.setWalletBalance(
                    wallet.getWalletBalance() + transactionRequest.getTransactionAmount()
            );
            //update transaction ledger
           wallet.getTransactions().add(new Transaction(wallet,transactionRequest.getTransactionAmount(),
                    Timestamp.from(Instant.now()),transactionRequest.getTransactionType()));
           wallet.setTransactions(wallet.getTransactions());

           walletDao.save(wallet);
        }

        //Transfer
        else {
            //check if transaction amount is valid for debit transactions
                if (wallet.getWalletBalance() < transactionRequest.getTransactionAmount())
                throw new InsufficientBalanceException("Transaction failed-Insufficient balance in the wallet of user with username: "
                        + userName + " for transaction to happen");

                Optional<User> optCreditToTheWalletOfUser = userDao.findUserByEmail(transactionRequest
                        .getTransferToTheUserAccountWithEmail());

                //check if beneficiary User exists
                if (optCreditToTheWalletOfUser.isEmpty())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user with email: "
                            + transactionRequest.getTransferToTheUserAccountWithEmail());

                //debit from source wallet first
                wallet.setWalletBalance(wallet.getWalletBalance() - transactionRequest.getTransactionAmount());

                //update ledger of source wallet
                wallet.getTransactions().add(new Transaction(wallet, transactionRequest.getTransactionAmount(),
                        Timestamp.from(Instant.now()), transactionRequest.getTransactionType()));
                wallet.setTransactions(wallet.getTransactions());

                Wallet beneficiaryUserWallet = optCreditToTheWalletOfUser.get().getWallet();

                //credit amount to the beneficiary wallet
                beneficiaryUserWallet.setWalletBalance(
                        beneficiaryUserWallet.getWalletBalance() + transactionRequest.getTransactionAmount()
                );

                //update ledger of beneficiary wallet
                beneficiaryUserWallet.getTransactions().add(new Transaction(beneficiaryUserWallet, transactionRequest.getTransactionAmount(),
                        Timestamp.from(Instant.now()), Transaction.TransactionType.ADD));

                beneficiaryUserWallet.setTransactions(beneficiaryUserWallet.getTransactions());

                walletDao.save(beneficiaryUserWallet);
        }

        log.info("Transaction completed successfully for user with username: "+userName);
    }

    /**
     * perform transaction in a users wallet
     * @param userName: userName of user who's wallet balance needs to be fetched
     * @throws ResponseStatusException {@code 404 not found} if user is not found
     */
    @Override
    public Double getCurrentBalanceInUserAccount(String userName) {
        Optional<User> optUser = userDao.findUserByUsername(userName);
        if(optUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No user found for username: "+userName);
        return walletDao.findWalletByWalletOwner(optUser.get()).getWalletBalance();
    }
}

