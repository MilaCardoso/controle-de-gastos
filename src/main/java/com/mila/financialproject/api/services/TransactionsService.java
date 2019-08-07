package com.mila.financialproject.api.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.mila.financialproject.api.entities.Type;
import com.mila.financialproject.api.entities.Transactions;

public interface TransactionsService {

	Transactions persist(Transactions Transactions);

	Optional<Transactions> getByDate(Date date);
	
	Optional<Transactions> getByType(Type type);
	
	Optional<Transactions> getById(Long id);
	
	void remove(Long id);

	Optional<List<Transactions>> getAllTransactions();
	
}

