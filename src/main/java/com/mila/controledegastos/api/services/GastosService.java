package com.mila.controledegastos.api.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.mila.controledegastos.api.entities.Gastos;

public interface GastosService {

	/**
	 * Persiste um gasto na base de dados.
	 * 
	 * @param Gastos
	 * @return Gastos
	 */
	Gastos persistir(Gastos gastos);
	
	/**
	 * Busca e retorna um gasto dado uma data.
	 * 
	 * @param data
	 * @return Optional<Gastos>
	 */
	Optional<Gastos> buscarPorData(Date data);
	
	/**
	 * Busca e retorna um gasto dado um tipo.
	 * 
	 * @param tipo
	 * @return Optional<Gastos>
	 */
	Optional<Gastos> buscarPorTipo(String tipo);
	
	/**
	 * Busca e retorna um gasto por ID.
	 * 
	 * @param id
	 * @return Optional<Gastos>
	 */
	Optional<Gastos> buscarPorId(Long id);
	
	/**
	 * Remove um tipo da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);

	/**
	 * Busca e retorna todos os registros gastos.
	 * 
	 * @return Optional<List<Gastos>>
	 */
	Optional<List<Gastos>> buscarPorTodosGastos();
	
}

