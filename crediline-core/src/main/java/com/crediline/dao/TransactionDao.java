package com.crediline.dao;

import com.crediline.model.Transaction;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Created by dimer on 1/14/14.
 */

@Repository("incomeDao")
public interface TransactionDao extends CredilineRepository<Transaction> {
    @Query("SELECT sum(i.sum) FROM Transaction i where i.sum > 0 and i.creationDate >= :fromDate and i.creationDate < :toDate")
    BigDecimal findIncomesSumByDate(LocalDateTime fromDate, LocalDateTime toDate);

}