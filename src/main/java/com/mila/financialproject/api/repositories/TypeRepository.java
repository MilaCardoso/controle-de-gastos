package com.mila.financialproject.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mila.financialproject.api.entities.Type;

@Transactional(readOnly = true)
public interface TypeRepository extends JpaRepository<Type, Long> {
	
	Type findOne(Long id);
		
	Type findByName(String name);
}
