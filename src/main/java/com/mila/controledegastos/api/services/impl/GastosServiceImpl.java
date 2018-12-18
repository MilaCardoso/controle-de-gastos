package com.mila.controledegastos.api.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mila.controledegastos.api.entities.Gastos;
import com.mila.controledegastos.api.repositories.GastosRepository;
import com.mila.controledegastos.api.services.GastosService;

@Service
public class GastosServiceImpl implements GastosService {

	private static final Logger log = LoggerFactory.getLogger(GastosServiceImpl.class);
	
	@Autowired
	private GastosRepository gastosRepository;
	
	@Override
	public Gastos persistir(Gastos gastos) {
		log.info("Persistindo gastos: {}", gastos);
		return this.gastosRepository.save(gastos);
	}

	@Override
	public Optional<Gastos> buscarPorData(Date data) {
		log.info("Buscando gasto pelo data do gasto {}", data);
		return Optional.ofNullable(this.gastosRepository.findByData(data));
	}

	@Override
	public Optional<Gastos> buscarPorTipo(String tipo) {
		log.info("Buscando gasto pelo tipo de gasto {}", tipo);
		return Optional.ofNullable(this.gastosRepository.findByTipo(tipo));
	}
	
	public Optional<Gastos> buscarPorId(Long id) {
		log.info("Buscando gasto pelo ID {}", id);
		return Optional.ofNullable(this.gastosRepository.findOne(id));
	}

	@Override
	public void remover(Long id) {
		log.info("Removendo o gasto ID {}", id);
		this.gastosRepository.delete(id);		
	}
	
	@Override
	public Optional<List<Gastos>> buscarPorTodosGastos() {
		log.info("Buscando todos os registros gastos");
		return Optional.ofNullable(this.gastosRepository.findAll());
	}
}
