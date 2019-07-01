package com.mila.controledegastos.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mila.controledegastos.api.entities.Type;

@Transactional(readOnly = true)
public interface TypeRepository extends JpaRepository<Type, Long> {
	
	Type findOne(Long id);
		
	Type findByType(String type);
}
