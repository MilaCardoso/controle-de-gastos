package com.mila.controledegastos.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mila.controledegastos.api.entities.Type;
import com.mila.controledegastos.api.repositories.TypeRepository;
import com.mila.controledegastos.api.services.TypeService;

@Service
public class TypeServiceImpl implements TypeService {

	private static final Logger log = LoggerFactory.getLogger(TypeServiceImpl.class);
	
	@Autowired
	private TypeRepository typeRepository;
	
	@Override
	public Type persistir(Type type) {
		log.info("Persistindo type: {}", type);
		return this.typeRepository.save(type);
	}

	public Optional<Type> buscarPorId(Long id) {
		log.info("Buscando type pelo ID {}", id);
		return Optional.ofNullable(this.typeRepository.findOne(id));
	}

	@Override
	public Optional<Type> buscarPorType(String type) {
		log.info("Buscando type {}", type);
		return Optional.ofNullable(this.typeRepository.findByType(type));
	}
	
	public void remover(Long id) {
		log.info("Removendo o type ID {}", id);
		this.typeRepository.delete(id);
	}

	@Override
	public Optional<List<Type>> buscarPorTodosType() {
		log.info("Buscando todos os registros type");
		return Optional.ofNullable(this.typeRepository.findAll());
	}
	
}
