package com.mila.controledegastos.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mila.controledegastos.api.entities.Tipo;

@Transactional(readOnly = true)
public interface TipoRepository extends JpaRepository<Tipo, Long> {
	
	Tipo findOne(Long id);
		
	Tipo findByTipo(String tipo);
}
