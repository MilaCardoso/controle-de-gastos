package com.mila.controledegastos.api.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mila.controledegastos.api.entities.Gastos;

@Transactional(readOnly = true)
public interface GastosRepository extends JpaRepository<Gastos, Long> {
	
	Gastos findOne(Long id);
	
	Gastos findByData(Date data);
	
	Gastos findByTipo(String tipo);
}
