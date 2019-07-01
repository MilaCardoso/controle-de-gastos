package com.mila.controledegastos.api.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.mila.controledegastos.api.entities.Type;
import com.mila.controledegastos.api.entities.Transactions;

public interface TransactionsService {

	/**
	 * Persiste um gasto na base de dados.
	 * 
	 * @param Transactions
	 * @return Transactions
	 */
	Transactions persistir(Transactions Transactions);
	
	/**
	 * Busca e retorna um gasto dado uma data.
	 * 
	 * @param data
	 * @return Optional<Transactions>
	 */
	Optional<Transactions> buscarPorData(Date data);
	
	/**
	 * Busca e retorna um gasto dado um tipo.
	 * 
	 * @param tipo
	 * @return Optional<Transactions>
	 */
	Optional<Transactions> buscarPorTipo(Type tipo);
	
	/**
	 * Busca e retorna um gasto por ID.
	 * 
	 * @param id
	 * @return Optional<Transactions>
	 */
	Optional<Transactions> buscarPorId(Long id);
	
	/**
	 * Remove um tipo da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);

	/**
	 * Busca e retorna todos os registros Transactions.
	 * 
	 * @return Optional<List<Transactions>>
	 */
	Optional<List<Transactions>> buscarPorTodosTransactions();
	
}

