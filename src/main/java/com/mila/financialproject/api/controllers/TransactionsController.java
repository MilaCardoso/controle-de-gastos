package com.mila.financialproject.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mila.financialproject.api.dtos.TransactionsDto;
import com.mila.financialproject.api.entities.Transactions;
import com.mila.financialproject.api.services.TransactionsService;

@RestController
@RequestMapping("/api/transactions")

public class TransactionsController {

	private static final Logger log = LoggerFactory.getLogger(TransactionsController.class);

	@Autowired
	private TransactionsService transactionsService;

	public TransactionsController() {
	}

	@PostMapping
	public ResponseEntity<TransactionsDto> post(@Valid @RequestBody TransactionsDto TransactionsDto, BindingResult result)
			throws ParseException {
		log.info("Post new expense: {}", TransactionsDto.toString());
		Transactions transactions = this.fromDtoToTransactions(TransactionsDto);

		if (result.hasErrors()) {
			log.error("Error validating expense: {}", result.getAllErrors());
			ResponseEntity.badRequest().build();

		}

		transactions = this.transactionsService.persist(transactions);
		return ResponseEntity.ok(this.fromTransactionsToDto(transactions));
	}

	private Transactions fromDtoToTransactions(TransactionsDto transactionsDto) {
		Transactions transactions = new Transactions();
		transactions.setDate(transactionsDto.getDate());
		transactions.setDescription(transactionsDto.getDescription());
		transactions.setValue(transactionsDto.getValue());
		transactions.setType(transactionsDto.getType());

		return transactions;
	}

	private TransactionsDto fromTransactionsToDto(Transactions transactions) {
		TransactionsDto transactionsDto = new TransactionsDto();
		transactionsDto.setId(transactions.getId());
		transactionsDto.setDate(transactions.getDate());
		transactionsDto.setDescription(transactions.getDescription());
		transactionsDto.setValue(transactions.getValue());
		transactionsDto.setType(transactions.getType());

		return transactionsDto;
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		log.info("Deleting expense: {}", id);
		Optional<Transactions> transactions = this.transactionsService.getById(id);

		if (!transactions.isPresent()) {
			log.info("Error to delete expense ID: {} it's invalid.", id);
			ResponseEntity.badRequest().build();

		}

		this.transactionsService.remove(id);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<TransactionsDto> put(@PathVariable("id") Long id, @Valid @RequestBody TransactionsDto transactionsDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Putting expense: {}", transactionsDto.toString());

		Optional<Transactions> transactions = this.transactionsService.getById(id);

		if (!transactions.isPresent()) {
			log.info("Error to put expense ID: {} it's invalid.", id);
			ResponseEntity.badRequest().build();

		}

		this.updateDataTransactions(transactions.get(), transactionsDto, result);

		if (result.hasErrors()) {
			log.error("Error validating expense: {}", result.getAllErrors());
			ResponseEntity.badRequest().build();

		}

		this.transactionsService.persist(transactions.get());

		return ResponseEntity.ok(this.fromTransactionsToDto(transactions.get()));
	}

	private void updateDataTransactions(Transactions transactions, TransactionsDto transactionsDto, BindingResult result)
			throws NoSuchAlgorithmException {
		transactions.setDate(transactionsDto.getDate());
		transactions.setDescription(transactionsDto.getDescription());
		transactions.setValue(transactionsDto.getValue());	
		transactions.setType(transactionsDto.getType());
	}	
	
	@GetMapping(value = "id/{id}")
	public ResponseEntity<TransactionsDto> findById(@PathVariable("id") Long id) {
		log.info("Getting expense by ID: {}", id);
		Optional<Transactions> transactions = this.transactionsService.getById(id);

		if (!transactions.isPresent()) {
			log.error("Expense not found to ID: {}", id);
			ResponseEntity.badRequest().build();

		}

		return ResponseEntity.ok(this.fromTransactionsToDto(transactions.get()));
	}
	

	@GetMapping(value = "all")
	public ResponseEntity<List<TransactionsDto>> getAllTransactions() {
		log.info("Getting all registers transactions");
		Optional<List<Transactions>> transactionsList = this.transactionsService.getAllTransactions();

		if (!transactionsList.isPresent()) {
			log.error("There are not registers transactions");
			ResponseEntity.badRequest().build();

		}

		List<TransactionsDto> listTransactionsDto = new ArrayList<>();
		for (Transactions transactions : transactionsList.get()) {
			listTransactionsDto.add(this.fromTransactionsToDto(transactions));
		}

		return ResponseEntity.ok(listTransactionsDto);

	}

}
