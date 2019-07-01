package com.mila.controledegastos.api.dtos;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.mila.controledegastos.api.enums.TransactionType;

public class TypeDto {
	
	private Long id;
	private String type;
	private TransactionType transactionType;
	
	public TypeDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@NotEmpty(message = "Type não pode ser vazio.")
	@Length(min = 2, max = 200, message = "Type deve conter entre 2 e 200 caracteres.")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		return "CadastroTypeDto [id=\" + id + \", +  type=" + type + "]";
	}
}
