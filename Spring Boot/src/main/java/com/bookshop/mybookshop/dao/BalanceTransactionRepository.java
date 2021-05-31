package com.bookshop.mybookshop.dao;

import com.bookshop.mybookshop.domain.user.BalanceTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceTransactionRepository extends JpaRepository<BalanceTransaction, Long> {
}
