package com.mila.controledegastos.api.repositories;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mila.controledegastos.api.entities.Transactions;
import com.mila.controledegastos.api.entities.Type;

@Transactional(readOnly = true)
public interface TransactionsRepository extends CrudRepository<Transactions, Long> {
	
	Transactions findOne(Long id);
	
	Transactions findByData(Date data);
	
	Transactions findByTipo(Type tipo);
}
