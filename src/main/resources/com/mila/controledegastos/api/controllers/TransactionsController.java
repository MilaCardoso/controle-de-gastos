package com.mila.controledegastos.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mila.controledegastos.api.dtos.TransactionsDto;
import com.mila.controledegastos.api.entities.Transactions;
import com.mila.controledegastos.api.repositories.TransactionsRepository;
import com.mila.controledegastos.api.response.Response;
import com.mila.controledegastos.api.services.TransactionsService;

@RestController
@RequestMapping("/api/transactions")

public class TransactionsController {

	private static final Logger log = LoggerFactory.getLogger(TransactionsController.class);

	@Autowired
	private TransactionsService transactionsService;
	
	@Autowired
	private TransactionsRepository repository;

	public TransactionsController() {
	}

	@GetMapping(value = "ola/{envio}")
	public ResponseEntity<String> ola(@PathVariable("envio")  String envio)
			throws ParseException {
		
		System.out.println(envio);
		return ResponseEntity.ok("Ola Camila");
	}
	
	/**
	 * Adiciona um novo gasto.
	 * 
	 * @param transactions
	 * @param result
	 * @return ResponseEntity<Response<transactionsDto>>
	 * @throws ParseException
	 */
	@PostMapping
	public ResponseEntity<Response<TransactionsDto>> adicionar(@Valid @RequestBody TransactionsDto TransactionsDto, BindingResult result)
			throws ParseException {
		log.info("Adicionando gasto: {}", TransactionsDto.toString());
		Response<TransactionsDto> response = new Response<TransactionsDto>();
		Transactions transactions = this.converterTransactionsParaDto(TransactionsDto);

		if (result.hasErrors()) {
			log.error("Erro validando gasto: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		transactions = this.transactionsService.persistir(transactions);
		response.setData(this.converterDtoParaTransactions(transactions));
		return ResponseEntity.ok(response);
	}

	/**
	 * Converte os dados do DTO para transactions.
	 * 
	 * @param transactionsDto
	 * @param result
	 * @return transactions
	 * @throws NoSuchAlgorithmException
	 */
	private Transactions converterTransactionsParaDto(TransactionsDto transactionsDto) {
		Transactions transactions = new Transactions();
		transactions.setData(transactionsDto.getData());
		transactions.setDescricao(transactionsDto.getDescricao());
		transactions.setValor(transactionsDto.getValor());
		transactions.setTipo(transactionsDto.getTipo());


		return transactions;
	}

	/**
	 * Popula o DTO de transactions com os dados do transactions.
	 * 
	 * @param transactions
	 * @return transactionsDto
	 */
	private TransactionsDto converterDtoParaTransactions(Transactions transactions) {
		TransactionsDto transactionsDto = new TransactionsDto();
		transactionsDto.setId(transactions.getId());
		transactionsDto.setData(transactions.getData());
		transactionsDto.setDescricao(transactions.getDescricao());
		transactionsDto.setValor(transactions.getValor());
		transactionsDto.setTipo(transactions.getTipo());

		return transactionsDto;
	}

	/**
	 * Remove um gasto por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<transactions>>
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo gasto: {}", id);
		Response<String> response = new Response<String>();
		Optional<Transactions> transactions = this.transactionsService.buscarPorId(id);

		if (!transactions.isPresent()) {
			log.info("Erro ao remover devido ao tipo ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover gasto. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.transactionsService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	/**
	 * Atualiza os dados de um gasto.
	 * 
	 * @param id
	 * @param transactionsDto
	 * @param result
	 * @return ResponseEntity<Response<transactionsDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<TransactionsDto>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody TransactionsDto transactionsDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Atualizando gasto: {}", transactionsDto.toString());
		Response<TransactionsDto> response = new Response<TransactionsDto>();

		Optional<Transactions> transactions = this.transactionsService.buscarPorId(id);

		if (!transactions.isPresent()) {
			log.info("Erro ao atualizar devido ao gasto ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao atualizar gasto. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.atualizarDadosTransactions(transactions.get(), transactionsDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando gasto: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.transactionsService.persistir(transactions.get());
		response.setData(this.converterDtoParaTransactions(transactions.get()));

		return ResponseEntity.ok(response);
	}

	/**
	 * Atualiza os dados do transactions com base nos dados encontrados no DTO.
	 * 
	 * @param transactions
	 * @param transactionsDto
	 * @param result
	 * @throws NoSuchAlgorithmException
	 * 
	 * PERGUNTAR FELIPE
	 * 
	 */ 
	private void atualizarDadosTransactions(Transactions transactions, TransactionsDto transactionsDto, BindingResult result)
			throws NoSuchAlgorithmException {
		transactions.setData(transactionsDto.getData());
		transactions.setDescricao(transactionsDto.getDescricao());
		transactions.setValor(transactionsDto.getValor());	
		transactions.setTipo(transactionsDto.getTipo());
	}	
	
	/**
	 * Retorna um gasto dado um Id.
	 * 
	 * @param Id
	 * @return ResponseEntity<Response<transactionsDto>>
	 * 
	 * VER DATA COM FELIPE
	 * 
	 */
	@GetMapping(value = "id/{id}")
	public ResponseEntity<Response<TransactionsDto>> buscarPorId(@PathVariable("id") Long id) {
		log.info("Buscando gasto por Id: {}", id);
		Response<TransactionsDto> response = new Response<TransactionsDto>();
		Optional<Transactions> transactions = this.transactionsService.buscarPorId(id);

		if (!transactions.isPresent()) {
			log.error("Gasto não encontrado para o Id: {}", id);
			response.getErrors().add("Gasto não encontrado para o Id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterDtoParaTransactions(transactions.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna todos os registros transactions.
	 * 
	 * @return ResponseEntity<Response<transactionsDto>>
	 */
	@RequestMapping("all")
	public Iterable listaTransactions(Model model){

	    Iterable<Transactions> transactions = repository.findAll();
	    model.addAttribute("transactions", transactions);

		return (transactions);	
	}
	
//	@GetMapping(value = "all")
//	public ResponseEntity<Response<List<TransactionsDto>>> buscarPorTodostransactions() {
//		log.info("Buscando todos os registros transactions");
//		Response<List<TransactionsDto>> response = new Response<>();
//		Optional<List<Transactions>> transactionsList = this.transactionsService.buscarPorTodosTransactions();
//
//		if (!transactionsList.isPresent()) {
//			log.error("Não existe registros transactions");
//			response.getErrors().add("Não existe registros transactions");
//			return ResponseEntity.badRequest().body(response);
//		}
//
//		List<TransactionsDto> listTransactionsDto = new ArrayList<>();
//		for (Transactions transactions : transactionsList.get()) {
//			listTransactionsDto.add(this.converterDtoParaTransactions(transactions));
//		}
//		response.setData(listTransactionsDto);
//		return ResponseEntity.ok(response);
//
//	}


}
