package com.mila.financialproject.api.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mila.financialproject.api.entities.Transactions;
import com.mila.financialproject.api.entities.Type;
import com.mila.financialproject.api.repositories.TransactionsRepository;
import com.mila.financialproject.api.services.TransactionsService;

@Service
public class TransactionsServiceImpl implements TransactionsService {

	private static final Logger log = LoggerFactory.getLogger(TransactionsServiceImpl.class);
	
	@Autowired
	private TransactionsRepository transactionsRepository;
	
	@Override
	public Transactions persist(Transactions transactions) {
		log.info("Persisting transactions: {}", transactions);
		return this.transactionsRepository.save(transactions);
	}

	@Override
	public Optional<Transactions> getByDate(Date date) {
		log.info("Getting expense by date {}", date);
		return Optional.ofNullable(this.transactionsRepository.findByDate(date));
	}

	@Override
	public Optional<Transactions> getByType(Type type) {
		log.info("Getting expense by type {}", type);
		return Optional.ofNullable(this.transactionsRepository.findByType(type));
	}
	
	@Override
	public Optional<Transactions> getById(Long id) {
		log.info("Getting by ID {}", id);
		return Optional.ofNullable(this.transactionsRepository.findOne(id));
	}

	@Override
	public void remove(Long id) {
		log.info("Removing expense ID {}", id);
		this.transactionsRepository.delete(id);		
	}

	@Override
	public Optional<List<Transactions>> getAllTransactions() {
		log.info("Getting all registers transactions");
		return Optional.ofNullable(this.transactionsRepository.findAll());
	}
	
}
