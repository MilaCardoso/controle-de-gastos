package com.mila.controledegastos.api.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mila.controledegastos.api.entities.Type;
import com.mila.controledegastos.api.entities.Transactions;
import com.mila.controledegastos.api.repositories.TransactionsRepository;
import com.mila.controledegastos.api.services.TransactionsService;

@Service
public class TransactionsServiceImpl implements TransactionsService {

	private static final Logger log = LoggerFactory.getLogger(TransactionsServiceImpl.class);
	
	@Autowired
	private TransactionsRepository transactionsRepository;
	
	@Override
	public Transactions persistir(Transactions transactions) {
		log.info("Persistindo transactions: {}", transactions);
		return this.transactionsRepository.save(transactions);
	}

	@Override
	public Optional<Transactions> buscarPorData(Date data) {
		log.info("Buscando gasto pelo data do gasto {}", data);
		return Optional.ofNullable(this.transactionsRepository.findByData(data));
	}

	@Override
	public Optional<Transactions> buscarPorTipo(Type tipo) {
		log.info("Buscando gasto pelo tipo de gasto {}", tipo);
		return Optional.ofNullable(this.transactionsRepository.findByTipo(tipo));
	}
	
	public Optional<Transactions> buscarPorId(Long id) {
		log.info("Buscando gasto pelo ID {}", id);
		return Optional.ofNullable(this.transactionsRepository.findOne(id));
	}

	@Override
	public void remover(Long id) {
		log.info("Removendo o gasto ID {}", id);
		this.transactionsRepository.delete(id);		
	}
	
	@Override
	public Optional<List<Transactions>> buscarPorTodosTransactions() {
		log.info("Buscando todos os registros transactions");
		return Optional.ofNullable(this.transactionsRepository.findAll());
	}

}
