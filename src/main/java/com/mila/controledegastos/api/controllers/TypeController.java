package com.mila.controledegastos.api.controllers;

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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mila.controledegastos.api.dtos.MonthlySumDto;
import com.mila.controledegastos.api.dtos.TypeDto;
import com.mila.controledegastos.api.entities.Type;
import com.mila.controledegastos.api.enums.TransactionType;
import com.mila.controledegastos.api.response.Response;
import com.mila.controledegastos.api.services.TypeService;

@RestController
@RequestMapping("/api/type")

public class TypeController {

	private static final Logger log = LoggerFactory.getLogger(TypeController.class);

	@Autowired
	private TypeService typeService;

	public TypeController() {
	}

	@GetMapping(value = "olatype/{envio}")
	public ResponseEntity<String> ola(@PathVariable("envio") String envio) throws ParseException {

		System.out.println(envio);
		return ResponseEntity.ok("Ola teste type da Camila");
	}

	/**
	 * Adiciona um novo type.
	 * 
	 * @param type
	 * @param result
	 * @return ResponseEntity<Response<TypeDto>>
	 * @throws ParseException
	 */
	@PostMapping
	public ResponseEntity<Response<TypeDto>> adicionar(@Valid @RequestBody TypeDto typeDto, BindingResult result)
			throws ParseException {
		log.info("Adicionando type: {}", typeDto.toString());
		Response<TypeDto> response = new Response<TypeDto>();
		validarDadosExistentes(typeDto, result);
		Type type = this.converterTypeParaDto(typeDto);

		if (result.hasErrors()) {
			log.error("Erro validando type: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		type = this.typeService.persistir(type);
		response.setData(this.converterDtoParaType(type));
		return ResponseEntity.ok(response);
	}

	/**
	 * Verifica se type já está cadastrado.
	 * 
	 * @param typeDto
	 * @param result
	 */
	private void validarDadosExistentes(TypeDto typeDto, BindingResult result) {
		this.typeService.buscarPorType(typeDto.getType())
				.ifPresent(type -> result.addError(new ObjectError("type", "Já existente.")));
	}

	/**
	 * Converte os dados do DTO para type.
	 * 
	 * @param typeDto
	 * @param result
	 * @return Type
	 * @throws NoSuchAlgorithmException
	 */
	private Type converterTypeParaDto(TypeDto typeDto) {
		Type type = new Type();
		type.setType(typeDto.getType());
		type.setTransactionType(typeDto.getTransactionType());

		return type;
	}

	/**
	 * Popula o DTO de type com os dados do type.
	 * 
	 * @param Type
	 * @return TypeDto
	 */
	private TypeDto converterDtoParaType(Type type) {
		TypeDto typeDto = new TypeDto();
		typeDto.setId(type.getId());
		typeDto.setType(type.getType());
		typeDto.setTransactionType(type.getTransactionType());

		return typeDto;
	}

	/**
	 * Remove um type por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Type>>
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo type: {}", id);
		Response<String> response = new Response<String>();
		Optional<Type> type = this.typeService.buscarPorId(id);

		if (!type.isPresent()) {
			log.info("Erro ao remover devido ao type ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover type. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.typeService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}

	/**
	 * Atualiza os dados de um type.
	 * 
	 * @param id
	 * @param typeDto
	 * @param result
	 * @return ResponseEntity<Response<typeDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<TypeDto>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody TypeDto typeDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Atualizando type: {}", typeDto.toString());
		Response<TypeDto> response = new Response<TypeDto>();

		Optional<Type> type = this.typeService.buscarPorId(id);

		if (!type.isPresent()) {
			log.info("Erro ao atualizar devido ao type ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao atualizar type. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.atualizarDadosType(type.get(), typeDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando type: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.typeService.persistir(type.get());
		response.setData(this.converterDtoParaType(type.get()));

		return ResponseEntity.ok(response);
	}

	/**
	 * Atualiza os dados do type com base nos dados encontrados no DTO.
	 * 
	 * @param type
	 * @param typeDto
	 * @param result
	 * @throws NoSuchAlgorithmException
	 */
	private void atualizarDadosType(Type type, TypeDto typeDto, BindingResult result) throws NoSuchAlgorithmException {

		if (!type.getType().equals(typeDto.getType())) {
			this.typeService.buscarPorType(typeDto.getType())
					.ifPresent(func -> result.addError(new ObjectError("Type", "Type já existente.")));
			type.setType(typeDto.getType());
		}
	}

	/**
	 * Retorna um type dado um Id.
	 * 
	 * @param Id
	 * @return ResponseEntity<Response<TypeDto>>
	 */
	@GetMapping(value = "id/{id}")
	public ResponseEntity<Response<TypeDto>> buscarPorId(@PathVariable("id") Long id) {
		log.info("Buscando type por Id: {}", id);
		Response<TypeDto> response = new Response<TypeDto>();
		Optional<Type> type = this.typeService.buscarPorId(id);

		if (!type.isPresent()) {
			log.error("Type não encontrado para o Id: {}", id);
			response.getErrors().add("Type não encontrado para o Id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterDtoParaType(type.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Retorna todos os registros type.
	 * 
	 * @return ResponseEntity<Response<TypeDto>>
	 */
	@GetMapping(value = "all")
	public ResponseEntity<Response<List<TypeDto>>> buscarPorTodosType() {
		log.info("Buscando todos os registros type");
		Response<List<TypeDto>> response = new Response<>();
		Optional<List<Type>> typeList = this.typeService.buscarPorTodosType();

		if (!typeList.isPresent()) {
			log.error("Não existe registros type");
			response.getErrors().add("Não existe registros type");
			return ResponseEntity.badRequest().body(response);
		}

		List<TypeDto> listTypeDto = new ArrayList<>();
		for (Type type : typeList.get()) {
			listTypeDto.add(this.converterDtoParaType(type));
		}
		response.setData(listTypeDto);
		return ResponseEntity.ok(response);

	}

	/**
	 * Retorna soma do INCOME e OUTCOME.
	 * 
	 * @param Mes
	 * @return ResponseEntity<Response<TypeDto>>
	 */
	@GetMapping(value = "monthlySum/{mes}")
	public ResponseEntity<Response<MonthlySumDto>> monthlySum(@PathVariable("mes") Integer mes) {
		log.info("Somando por transaction type");
		Response<MonthlySumDto> response = new Response<MonthlySumDto>();
		MonthlySumDto monthlySumDto = new MonthlySumDto();
		monthlySumDto.setSumIncome(this.typeService.somaValoresPorTransactionType(TransactionType.IN, mes));
		monthlySumDto.setSumOutcome(this.typeService.somaValoresPorTransactionType(TransactionType.OUT, mes));
		
		response.setData(monthlySumDto);
		return ResponseEntity.ok(response);
	}
	
}
