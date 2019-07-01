package com.mila.financialproject.api.services;

import java.util.List;
import java.util.Optional;

import com.mila.financialproject.api.dtos.TypeSumDto;
import com.mila.financialproject.api.entities.Type;
import com.mila.financialproject.api.enums.TransactionType;

public interface TypeService {

	/**
	 * Persists one Type in data base.
	 * 
	 * @param  Type
	 * @return Type
	 */
	Type persist(Type type);
	
	/**
	 * Search and return one Type by ID.
	 * 
	 * @param  ID
	 * @return Optional<Type>
	 */
	Optional<Type> getTypeById(Long id);

	/**
	 * Search and return one Type.
	 * 
	 * @param  Type
	 * @return Optional<Type>
	 */
	Optional<Type> getByType(String type);	
	
	/**
	 * Search and return all registers Type.
	 * 
	 * @return Optional<List<Type>>
	 */
	Optional<List<Type>> getAllType();
	
	/**
	 * Remove one Type in data base.
	 * 
	 * @param id
	 */
	void remove(Long id);
	
	/**
	 * Sum values by TransactionType.
	 * 
	 * @param transactionType
	 * @return
	 */
	Double sumValuesByTransactionType(TransactionType transactionType, Integer month);	
	
	/**
	 * Sum values by Type.
	 * 
	 * @param transactionType 
	 * @param month 
	 * @return
	 */
	List<TypeSumDto> sumValuesByType(TransactionType transactionType, Integer month);
	
}

