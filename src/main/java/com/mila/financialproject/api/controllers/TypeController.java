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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mila.financialproject.api.dtos.MonthlySumDto;
import com.mila.financialproject.api.dtos.TypeDto;
import com.mila.financialproject.api.dtos.TypeSumDto;
import com.mila.financialproject.api.entities.Type;
import com.mila.financialproject.api.enums.TransactionType;
import com.mila.financialproject.api.response.Response;
import com.mila.financialproject.api.services.TypeService;

@RestController
@RequestMapping("/api/type")

public class TypeController {

	private static final Logger log = LoggerFactory.getLogger(TypeController.class);

	@Autowired
	private TypeService typeService;

	public TypeController() {
	}

	/**
	 * Post new type.
	 * 
	 * @param  type
	 * @param  result
	 * @return ResponseEntity<Response<TypeDto>>
	 * @throws ParseException
	 */
	@PostMapping
	public ResponseEntity<Response<TypeDto>> post(@Valid @RequestBody TypeDto typeDto, BindingResult result)
			throws ParseException {
		log.info("Posting type: {}", typeDto.toString());
		Response<TypeDto> response = new Response<TypeDto>();
		validateExistingData(typeDto, result);
		Type type = this.fromTypeToDto(typeDto);

		if (result.hasErrors()) {
			log.error("Error validating type: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		type = this.typeService.persist(type);
		response.setDate(this.fromDtoToType(type));
		return ResponseEntity.ok(response);
	}

	/**
	 * Checks if Type already registered.
	 * 
	 * @param typeDto
	 * @param result
	 */
	private void validateExistingData(TypeDto typeDto, BindingResult result) {
		this.typeService.getByType(typeDto.getName())
				.ifPresent(type -> result.addError(new ObjectError("type", "Already registered.")));
	}

	/**
	 * Converts data from DTO to type.
	 * 
	 * @param  typeDto
	 * @param  result
	 * @return Type
	 * @throws NoSuchAlgorithmException
	 */
	private Type fromTypeToDto(TypeDto typeDto) {
		Type type = new Type();
		type.setName(typeDto.getName());
		type.setTransactionType(typeDto.getTransactionType());

		return type;
	}

	/**
	 * Population DTO with data from type.
	 * 
	 * @param  Type
	 * @return TypeDto
	 */
	private TypeDto fromDtoToType(Type type) {
		TypeDto typeDto = new TypeDto();
		typeDto.setId(type.getId());
		typeDto.setName(type.getName());
		typeDto.setTransactionType(type.getTransactionType());

		return typeDto;
	}

	/**
	 * Delete one Type for ID.
	 * 
	 * @param  id
	 * @return ResponseEntity<Response<Type>>
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) {
		log.info("Deleting type: {}", id);
		Response<String> response = new Response<String>();
		Optional<Type> type = this.typeService.getTypeById(id);

		if (!type.isPresent()) {
			log.info("Error to delete type ID: {} it's invalid.", id);
			response.getErrors().add("Error to delete type. Register not found to id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.typeService.remove(id);
		return ResponseEntity.ok(new Response<String>());
	}

	/**
	 * Put data to one type.
	 * 
	 * @param  id
	 * @param  typeDto
	 * @param  result
	 * @return ResponseEntity<Response<typeDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<TypeDto>> put(@PathVariable("id") Long id, @Valid @RequestBody TypeDto typeDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Putting type: {}", typeDto.toString());
		Response<TypeDto> response = new Response<TypeDto>();

		Optional<Type> type = this.typeService.getTypeById(id);

		if (!type.isPresent()) {
			log.info("Error to put type ID: {} it's invalid.", id);
			response.getErrors().add("Error to put type. Register not found to id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.updateDataType(type.get(), typeDto, result);

		if (result.hasErrors()) {
			log.error("Error validating type: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.typeService.persist(type.get());
		response.setDate(this.fromDtoToType(type.get()));

		return ResponseEntity.ok(response);
	}

	/**
	 * Update all data to type with data finding in DTO.
	 * 
	 * @param  type
	 * @param  typeDto
	 * @param  result
	 * @throws NoSuchAlgorithmException
	 */
	private void updateDataType(Type type, TypeDto typeDto, BindingResult result) throws NoSuchAlgorithmException {

		if (!type.getName().equals(typeDto.getName())) {
			this.typeService.getByType(typeDto.getName())
					.ifPresent(func -> result.addError(new ObjectError("Type", "Type already registered.")));
			type.setName(typeDto.getName());
		}
	}

	/**
	 * Return one type by ID.
	 * 
	 * @param Id
	 * @return ResponseEntity<Response<TypeDto>>
	 */
	@GetMapping(value = "id/{id}")
	public ResponseEntity<Response<TypeDto>> getById(@PathVariable("id") Long id) {
		log.info("Getting type by Id: {}", id);
		Response<TypeDto> response = new Response<TypeDto>();
		Optional<Type> type = this.typeService.getTypeById(id);

		if (!type.isPresent()) {
			log.error("Type not found Id: {}", id);
			response.getErrors().add("Type not found Id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setDate(this.fromDtoToType(type.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Return all registers Type.
	 * 
	 * @return ResponseEntity<Response<TypeDto>>
	 */
	@GetMapping(value = "all")
	public ResponseEntity<Response<List<TypeDto>>> getAllType() {
		log.info("Getting all registers type");
		Response<List<TypeDto>> response = new Response<>();
		Optional<List<Type>> typeList = this.typeService.getAllType();

		if (!typeList.isPresent()) {
			log.error("There are not registers type");
			response.getErrors().add("There are not registers type");
			return ResponseEntity.badRequest().body(response);
		}

		List<TypeDto> listTypeDto = new ArrayList<>();
		for (Type type : typeList.get()) {
			listTypeDto.add(this.fromDtoToType(type));
		}
		response.setDate(listTypeDto);
		return ResponseEntity.ok(response);

	}

	/**
	 * Retorn  sum INCOME and OUTCOME.
	 * 
	 * @param  Month
	 * @return ResponseEntity<Response<TypeDto>>
	 */
	@GetMapping(value = "monthlySum/{mes}")
	public ResponseEntity<Response<MonthlySumDto>> monthlySum(@PathVariable("mes") Integer mes) {
		log.info("Summing for Transaction type");
		Response<MonthlySumDto> response = new Response<MonthlySumDto>();
		MonthlySumDto monthlySumDto = new MonthlySumDto();
		monthlySumDto.setSumIncome(this.typeService.sumValuesByTransactionType(TransactionType.IN, mes));
		monthlySumDto.setSumOutcome(this.typeService.sumValuesByTransactionType(TransactionType.OUT, mes));	
		monthlySumDto.setDifference(monthlySumDto.getSumIncome() - monthlySumDto.getSumOutcome());
		
		response.setDate(monthlySumDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Return  sum for Type.
	 * 
	 * @param  Month
	 * @param  transactionType 
	 * @return ResponseEntity<Response<TypeDto>>
	 */
	@GetMapping(value = "TypeSum/{mes}/{transactionType}")
	public ResponseEntity<Response<List<TypeSumDto>>> typeSum(@PathVariable("mes") Integer month, 
			                                            @PathVariable("transactionType") TransactionType transactionType) {
		log.info("Sum values for type");
		Response<List<TypeSumDto>> response = new Response<>();
		List<TypeSumDto> typeSumList = this.typeService.sumValuesByType(transactionType, month);

		response.setDate(typeSumList);
		return ResponseEntity.ok(response);
		
	}
	
}
