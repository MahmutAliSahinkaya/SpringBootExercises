package com.atm.repository;

import com.atm.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByBankUserId(Long id);


    @Query(value = "SELECT * FROM account WHERE balance = (SELECT MAX(balance) FROM account)",nativeQuery = true)
    Account findUserWithHighestBalance();

    Query createQuery(String selectAFromAccountA, Class<Account> accountClass);
}
