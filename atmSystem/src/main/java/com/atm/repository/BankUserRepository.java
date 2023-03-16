package com.atm.repository;

import com.atm.model.BankUser;
import jakarta.persistence.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankUserRepository extends JpaRepository<BankUser, Long> {
    Optional<BankUser> findBankUserByUserName(String username);
    Optional<BankUser> findBankUserByUserNameAndPassword(String username, String password);
    Optional<BankUser> findBankUserById(Long id);


    Query createQuery(String selectBuFromBankUserBu, Class<BankUser> bankUserClass);

}
