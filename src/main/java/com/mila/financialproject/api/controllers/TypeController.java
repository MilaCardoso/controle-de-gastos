package com.mila.financialproject.api.controllers;

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

import com.mila.financialproject.api.dtos.MonthlySumDto;
import com.mila.financialproject.api.dtos.TypeDto;
import com.mila.financialproject.api.dtos.TypeSumDto;
import com.mila.financialproject.api.entities.Type;
import com.mila.financialproject.api.enums.TransactionType;
import com.mila.financialproject.api.services.TypeService;

@RestController
@RequestMapping("/api/type")

public class TypeController {

	private static final Logger log = LoggerFactory.getLogger(TypeController.class);

	@Autowired
	private TypeService typeService;

	public TypeController() {
	}

	@PostMapping
	public ResponseEntity<TypeDto> post(@Valid @RequestBody TypeDto typeDto, BindingResult result)
			throws Exception {
		log.info("Posting type: {}", typeDto.toString());
		
		if(!validateAvailableName(typeDto.getName())) {
			throw new Exception("Invalid name");
		}
		
		Type type = this.fromTypeToDto(typeDto);

		if (result.hasErrors()) {
			log.error("Error validating type: {}", result.getAllErrors());
			ResponseEntity.badRequest().build();
		}
		
		type = this.typeService.persist(type);
		return ResponseEntity.ok(this.fromDtoToType(type));
	}

	private boolean validateAvailableName(String name) {
		return !this.typeService.getByType(name).isPresent();
	}

	private Type fromTypeToDto(TypeDto typeDto) {
		Type type = new Type();
		type.setName(typeDto.getName());
		type.setTransactionType(typeDto.getTransactionType());

		return type;
	}

	private TypeDto fromDtoToType(Type type) {
		TypeDto typeDto = new TypeDto();
		typeDto.setId(type.getId());
		typeDto.setName(type.getName());
		typeDto.setTransactionType(type.getTransactionType());

		return typeDto;
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		log.info("Deleting type: {}", id);
		Optional<Type> type = this.typeService.getTypeById(id);

		if (!type.isPresent()) {
			log.info("Error to delete type ID: {} it's invalid.", id);
			ResponseEntity.badRequest().build();
		}

		this.typeService.remove(id);
		return ResponseEntity.ok().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<TypeDto> put(@PathVariable("id") Long id, @Valid @RequestBody TypeDto typeDto,
			BindingResult result) throws Exception {
		log.info("Putting type: {}", typeDto.toString());

		Optional<Type> type = this.typeService.getTypeById(id);

		if (!type.isPresent()) {
			log.info("Error to put type ID: {} it's invalid.", id);
			ResponseEntity.badRequest().build();
		}

		this.updateDataType(type.get(), typeDto, result);

		if (result.hasErrors()) {
			log.error("Error validating type: {}", result.getAllErrors());
			ResponseEntity.badRequest().build();
		}

		this.typeService.persist(type.get());

		return ResponseEntity.ok(this.fromDtoToType(type.get()));
	}

	private void updateDataType(Type type, TypeDto typeDto, BindingResult result) throws Exception {

		if (!type.getName().equals(typeDto.getName())) {
			if(!validateAvailableName(typeDto.getName())) {
				if(type.getTransactionType().equals(typeDto.getTransactionType())) {
					throw new Exception("Invalid name");
				}
			}
		}
		type.setName(typeDto.getName());
		type.setTransactionType(typeDto.getTransactionType());
	}

	@GetMapping(value = "id/{id}")
	public ResponseEntity<TypeDto> getById(@PathVariable("id") Long id) {
		log.info("Getting type by Id: {}", id);
		Optional<Type> type = this.typeService.getTypeById(id);

		if (!type.isPresent()) {
			log.error("Type not found Id: {}", id);
			ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok(this.fromDtoToType(type.get()));
	}

	@GetMapping(value = "all")
	public ResponseEntity<List<TypeDto>> getAllType() {
		log.info("Getting all registers type");
		Optional<List<Type>> typeList = this.typeService.getAllType();

		if (!typeList.isPresent()) {
			log.error("There are not registers type");
			ResponseEntity.badRequest().build();
		}

		List<TypeDto> listTypeDto = new ArrayList<>();
		for (Type type : typeList.get()) {
			listTypeDto.add(this.fromDtoToType(type));
		}

		return ResponseEntity.ok(listTypeDto);

	}

	@GetMapping(value = "monthlySum/{mes}")
	public ResponseEntity<MonthlySumDto> monthlySum(@PathVariable("mes") Integer mes) {
		log.info("Summing for Transaction type");
		MonthlySumDto monthlySumDto = new MonthlySumDto();
		monthlySumDto.setSumIncome(this.typeService.sumValuesByTransactionType(TransactionType.IN, mes));
		monthlySumDto.setSumOutcome(this.typeService.sumValuesByTransactionType(TransactionType.OUT, mes));	
		monthlySumDto.setDifference(monthlySumDto.getSumIncome() - monthlySumDto.getSumOutcome());
		
		return ResponseEntity.ok(monthlySumDto);
	}
	
	@GetMapping(value = "TypeSum/{mes}/{transactionType}")
	public ResponseEntity<List<TypeSumDto>> typeSum(@PathVariable("mes") Integer month, 
			                                        @PathVariable("transactionType") TransactionType transactionType) {
		log.info("Sum values for type");
		List<TypeSumDto> typeSumList = this.typeService.sumValuesByType(transactionType, month);

		return ResponseEntity.ok(typeSumList);
		
	}
	
}
