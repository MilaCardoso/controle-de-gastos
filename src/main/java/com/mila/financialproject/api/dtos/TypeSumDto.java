package com.mila.financialproject.api.dtos;

import com.mila.financialproject.api.entities.Type;

public class TypeSumDto {
	
	private Type type;
	private Double value;
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Double getValue() {
		return value;
	}
	
	public void setValue(Double value) {
		this.value = value;
	}
	
}
