package com.mila.controledegastos.api.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class TypeDto {
	
	private Long id;
	private String type;
	
	public TypeDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@NotEmpty(message = "Type não pode ser vazio.")
	@Length(min = 5, max = 200, message = "Type deve conter entre 5 e 200 caracteres.")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {	
		return "CadastroTypeDto [id=\" + id + \", +  type=" + type + "]";
	}
}
