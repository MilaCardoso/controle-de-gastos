package com.mila.financialproject.api.dtos;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.mila.financialproject.api.enums.TransactionType;

public class TypeDto {
	
	private Long id;
	private String name;
	private TransactionType transactionType;
	
	public TypeDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@NotEmpty(message = "Name can not be empty.")
	@Length(min = 2, max = 200, message = "Name should be between 2 and 200 characters.")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Enumerated(EnumType.STRING)
	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	
	@Override
	public String toString() {	
		return "TypeDto [id=\" + id + \", +  name=" + name + "]";
	}
}
