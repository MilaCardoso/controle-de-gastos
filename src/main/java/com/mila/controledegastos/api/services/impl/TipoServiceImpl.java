package com.mila.controledegastos.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mila.controledegastos.api.entities.Tipo;
import com.mila.controledegastos.api.repositories.TipoRepository;
import com.mila.controledegastos.api.services.TipoService;

@Service
public class TipoServiceImpl implements TipoService {

	private static final Logger log = LoggerFactory.getLogger(TipoServiceImpl.class);
	
	@Autowired
	private TipoRepository tipoRepository;
	
	@Override
	public Tipo persistir(Tipo tipo) {
		log.info("Persistindo tipo: {}", tipo);
		return this.tipoRepository.save(tipo);
	}

	public Optional<Tipo> buscarPorId(Long id) {
		log.info("Buscando tipo pelo ID {}", id);
		return Optional.ofNullable(this.tipoRepository.findOne(id));
	}

	@Override
	public Optional<Tipo> buscarPorTipo(String tipo) {
		log.info("Buscando tipo {}", tipo);
		return Optional.ofNullable(this.tipoRepository.findByTipo(tipo));
	}
	
	public void remover(Long id) {
		log.info("Removendo o tipo ID {}", id);
		this.tipoRepository.delete(id);
	}

	@Override
	public Optional<List<Tipo>> buscarPorTodosTipo() {
		log.info("Buscando todos os registros tipo");
		return Optional.ofNullable(this.tipoRepository.findAll());
	}
	
}
