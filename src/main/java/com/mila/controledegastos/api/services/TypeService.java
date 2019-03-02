package com.mila.controledegastos.api.services;

import java.util.List;
import java.util.Optional;

import com.mila.controledegastos.api.entities.Type;
import com.mila.controledegastos.api.enums.TransactionType;

public interface TypeService {

	/**
	 * Persiste um type na base de dados.
	 * 
	 * @param Type
	 * @return Type
	 */
	Type persistir(Type type);
	
	/**
	 * Busca e retorna um type por ID.
	 * 
	 * @param id
	 * @return Optional<Type>
	 */
	Optional<Type> buscarPorId(Long id);

	/**
	 * Busca e retorna um type.
	 * 
	 * @param type
	 * @return Optional<Type>
	 */
	Optional<Type> buscarPorType(String type);	
	
	/**
	 * Busca e retorna todos os registros type.
	 * 
	 * @return Optional<List<Type>>
	 */
	Optional<List<Type>> buscarPorTodosType();
	
	/**
	 * Remove um type da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);
	
	/**
	 * Soma Valores Por TransactionType
	 * @param transactionType
	 * @return
	 */
	Double somaValoresPorTransactionType(TransactionType transactionType, Integer mes);	
	
}

