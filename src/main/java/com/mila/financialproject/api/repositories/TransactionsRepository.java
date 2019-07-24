package com.mila.financialproject.api.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mila.financialproject.api.entities.Transactions;
import com.mila.financialproject.api.entities.Type;

@Transactional(readOnly = true)
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
	
	Transactions findByDate(Date date);
	
	Transactions findByType(Type type);
}
