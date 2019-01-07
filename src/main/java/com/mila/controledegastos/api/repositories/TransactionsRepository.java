package com.mila.controledegastos.api.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mila.controledegastos.api.entities.Type;
import com.mila.controledegastos.api.entities.Transactions;

@Transactional(readOnly = true)
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
	
	Transactions findOne(Long id);
	
	Transactions findByData(Date data);
	
	Transactions findByTipo(Type tipo);
}
