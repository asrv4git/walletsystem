package com.fabhotels.walletsystem.dao;

import com.fabhotels.walletsystem.models.entity.Transaction;
import com.fabhotels.walletsystem.models.entity.User;
import com.fabhotels.walletsystem.models.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface WalletDao extends JpaRepository<Wallet, Long> {
    @Query("select w.transactions from #{#entityName} as w where w.walletId = :walletId")
    Set<Transaction> getAllTransactionsByWalletId(@Param("walletId") Long walletId);

    Wallet findWalletByWalletOwner(User user);
}
