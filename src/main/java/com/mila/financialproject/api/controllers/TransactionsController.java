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
import com.mila.financialproject.api.response.Response;
import com.mila.financialproject.api.services.TransactionsService;

@RestController
@RequestMapping("/api/transactions")

public class TransactionsController {

	private static final Logger log = LoggerFactory.getLogger(TransactionsController.class);

	@Autowired
	private TransactionsService transactionsService;

	public TransactionsController() {
	}

	/**
	 * Post new expense
	 * 
	 * @param transactions
	 * @param result
	 * @return ResponseEntity<Response<transactionsDto>>
	 * @throws ParseException
	 */
	@PostMapping
	public ResponseEntity<Response<TransactionsDto>> post(@Valid @RequestBody TransactionsDto TransactionsDto, BindingResult result)
			throws ParseException {
		log.info("Post new expense: {}", TransactionsDto.toString());
		Response<TransactionsDto> response = new Response<TransactionsDto>();
		Transactions transactions = this.fromDtoToTransactions(TransactionsDto);

		if (result.hasErrors()) {
			log.error("Error validating expense: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		transactions = this.transactionsService.persist(transactions);
		response.setDate(this.fromTransactionsToDto(transactions));
		return ResponseEntity.ok(response);
	}

	/**
	 * From DTO to Transactions.
	 * 
	 * @param  transactionsDto
	 * @param  result
	 * @return transactions
	 * @throws NoSuchAlgorithmException
	 */
	private Transactions fromDtoToTransactions(TransactionsDto transactionsDto) {
		Transactions transactions = new Transactions();
		transactions.setDate(transactionsDto.getDate());
		transactions.setDescription(transactionsDto.getDescription());
		transactions.setValue(transactionsDto.getValue());
		transactions.setType(transactionsDto.getType());


		return transactions;
	}

	/**
	 * from Transactions to Dto.
	 * 
	 * @param transactions
	 * @return transactionsDto
	 */
	private TransactionsDto fromTransactionsToDto(Transactions transactions) {
		TransactionsDto transactionsDto = new TransactionsDto();
		transactionsDto.setId(transactions.getId());
		transactionsDto.setDate(transactions.getDate());
		transactionsDto.setDescription(transactions.getDescription());
		transactionsDto.setValue(transactions.getValue());
		transactionsDto.setType(transactions.getType());

		return transactionsDto;
	}

	/**
	 * Delete one expense for ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<transactions>>
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) {
		log.info("Deleting expense: {}", id);
		Response<String> response = new Response<String>();
		Optional<Transactions> transactions = this.transactionsService.getById(id);

		if (!transactions.isPresent()) {
			log.info("Error to delete expense ID: {} it's invalid.", id);
			response.getErrors().add("Error to delete. Register not found to id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.transactionsService.remove(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	/**
	 * Put data of the expense.
	 * 
	 * @param  id
	 * @param  transactionsDto
	 * @param  result
	 * @return ResponseEntity<Response<transactionsDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<TransactionsDto>> put(@PathVariable("id") Long id, @Valid @RequestBody TransactionsDto transactionsDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Putting expense: {}", transactionsDto.toString());
		Response<TransactionsDto> response = new Response<TransactionsDto>();

		Optional<Transactions> transactions = this.transactionsService.getById(id);

		if (!transactions.isPresent()) {
			log.info("Error to put expense ID: {} it's invalid.", id);
			response.getErrors().add("Error to put. Register not found to id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.updateDataTransactions(transactions.get(), transactionsDto, result);

		if (result.hasErrors()) {
			log.error("Error validating expense: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.transactionsService.persist(transactions.get());
		response.setDate(this.fromTransactionsToDto(transactions.get()));

		return ResponseEntity.ok(response);
	}

	/**
	 * Update data of Transactions with base in data finding in DTO.
	 * 
	 * @param  transactions
	 * @param  transactionsDto
	 * @param  result
	 * @throws NoSuchAlgorithmException
	 * 
	 */ 
	private void updateDataTransactions(Transactions transactions, TransactionsDto transactionsDto, BindingResult result)
			throws NoSuchAlgorithmException {
		transactions.setDate(transactionsDto.getDate());
		transactions.setDescription(transactionsDto.getDescription());
		transactions.setValue(transactionsDto.getValue());	
		transactions.setType(transactionsDto.getType());
	}	
	
	/**
	 * Return one expense give one ID.
	 * 
	 * @param  Id
	 * @return ResponseEntity<Response<transactionsDto>>
	 * 
	 * VER DATA COM FELIPE
	 * 
	 */
	@GetMapping(value = "id/{id}")
	public ResponseEntity<Response<TransactionsDto>> findById(@PathVariable("id") Long id) {
		log.info("Getting expense by ID: {}", id);
		Response<TransactionsDto> response = new Response<TransactionsDto>();
		Optional<Transactions> transactions = this.transactionsService.getById(id);

		if (!transactions.isPresent()) {
			log.error("Expense not found to ID: {}", id);
			response.getErrors().add("Expense not found to ID " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setDate(this.fromTransactionsToDto(transactions.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Return all registers Transactions.
	 * 
	 * @return ResponseEntity<Response<transactionsDto>>
	 */
	@GetMapping(value = "all")
	public ResponseEntity<Response<List<TransactionsDto>>> getAllTransactions() {
		log.info("Getting all registers transactions");
		Response<List<TransactionsDto>> response = new Response<>();
		Optional<List<Transactions>> transactionsList = this.transactionsService.getAllTransactions();

		if (!transactionsList.isPresent()) {
			log.error("There are not registers transactions");
			response.getErrors().add("There are not registers transactions");
			return ResponseEntity.badRequest().body(response);
		}

		List<TransactionsDto> listTransactionsDto = new ArrayList<>();
		for (Transactions transactions : transactionsList.get()) {
			listTransactionsDto.add(this.fromTransactionsToDto(transactions));
		}
		response.setDate(listTransactionsDto);
		return ResponseEntity.ok(response);

	}

}
