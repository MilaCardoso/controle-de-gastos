package com.mila.financialproject.api.repositories;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mila.financialproject.api.entities.Transactions;
import com.mila.financialproject.api.entities.Type;

@Transactional(readOnly = true)
public interface TransactionsRepository extends CrudRepository<Transactions, Long> {
	
	Transactions findOne(Long id);
	
	Transactions findByDate(Date date);
	
	Transactions findByType(Type type);
}
