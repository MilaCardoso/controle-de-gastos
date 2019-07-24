package com.mila.financialproject.api.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.mila.financialproject.api.entities.Type;
import com.mila.financialproject.api.entities.Transactions;

public interface TransactionsService {

	/**
	 * Persists one expense in data base.
	 * 
	 * @param  Transactions
	 * @return Transactions
	 */
	Transactions persist(Transactions Transactions);
	
	/**
	 * Search and return one expense and one date.
	 * 
	 * @param  date
	 * @return Optional<Transactions>
	 */
	Optional<Transactions> getByDate(Date date);
	
	/**
	 * Search and return one expense by type.
	 * 
	 * @param  type
	 * @return Optional<Transactions>
	 */
	Optional<Transactions> getByType(Type type);
	
	/**
	 * Search and return one expense by ID.
	 * 
	 * @param  id
	 * @return Optional<Transactions>
	 */
	Optional<Transactions> getById(Long id);
	
	/**
	 * Delete one type in data base.
	 * 
	 * @param id
	 */
	void remove(Long id);

	/**
	 * Search and return all registers Transactions.
	 * 
	 * @return Optional<List<Transactions>>
	 */
	Optional<List<Transactions>> getAllTransactions();
	
}

