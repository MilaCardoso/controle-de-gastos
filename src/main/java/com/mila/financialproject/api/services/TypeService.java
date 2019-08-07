package com.mila.financialproject.api.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.mila.financialproject.api.dtos.TypeSumDto;
import com.mila.financialproject.api.entities.Type;
import com.mila.financialproject.api.enums.TransactionType;

public interface TypeService {

	Type persist(Type type);
	
	Optional<Type> getTypeById(Long id);

	Optional<Type> getByType(String type);	
	
	Optional<List<Type>> getAllType();
	
	void remove(Long id);
	
	BigDecimal sumValuesByTransactionType(TransactionType transactionType, Integer month);	
	
	List<TypeSumDto> sumValuesByType(TransactionType transactionType, Integer month);
	
}

