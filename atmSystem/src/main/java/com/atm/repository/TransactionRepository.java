package com.atm.repository;

import com.atm.model.Account;
import com.atm.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = """
            SELECT DATE(timestamp) as date
            FROM transaction
            GROUP BY date
            ORDER BY COUNT(*) DESC
            LIMIT 1;
            """, nativeQuery = true)
    Date getDateWithMostTransactions();

    @Query(value = """
            SELECT account_id
            FROM (
              SELECT account_id, COUNT(*) as transactions_count
              FROM Transaction
              GROUP BY account_id
              ORDER BY transactions_count DESC
              LIMIT 1
            ) subquery;
            """, nativeQuery = true)
    Long getUserIdWithMostTransactions();

    @Query(value = "SELECT * FROM transaction WHERE account_id = :id", nativeQuery = true)
    List<Transaction> getTransactionsByAccountId(@Param("id") Long id);

    @Query(value = "SELECT * FROM transaction WHERE account_id = :id AND timestamp BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Transaction> getUserTransactionsBetweenDates(@Param("id") Long id,
                                                      @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT * FROM transaction WHERE timestamp BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Transaction> getBankTransactionsBetweenDates(@Param("startDate") LocalDateTime startDate,
                                                      @Param("endDate") LocalDateTime endDate);

    Object getTransactionsBetweenDate(LocalDateTime startDate, LocalDateTime endDate);

    Query createQuery(String selectTFromTransactionT, Class<Transaction> transactionClass);
}

