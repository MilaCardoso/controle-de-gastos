package com.mila.controledegastos.api.services;

import java.util.List;
import java.util.Optional;

import com.mila.controledegastos.api.entities.Tipo;

public interface TipoService {

	/**
	 * Persiste um tipo na base de dados.
	 * 
	 * @param Tipo
	 * @return Tipo
	 */
	Tipo persistir(Tipo tipo);
	
	/**
	 * Busca e retorna um tipo por ID.
	 * 
	 * @param id
	 * @return Optional<Tipo>
	 */
	Optional<Tipo> buscarPorId(Long id);

	/**
	 * Busca e retorna um tipo.
	 * 
	 * @param tipo
	 * @return Optional<Tipo>
	 */
	Optional<Tipo> buscarPorTipo(String tipo);	
	
	/**
	 * Busca e retorna todos os registros tipo.
	 * 
	 * @return Optional<List<Tipo>>
	 */
	Optional<List<Tipo>> buscarPorTodosTipo();
	
	/**
	 * Remove um tipo da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);
	
}

