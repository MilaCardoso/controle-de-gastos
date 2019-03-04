package com.mila.controledegastos.api.dtos;

import com.mila.controledegastos.api.entities.Type;

public class TypeSumDto {
	
	private Type type;
	private Double valor;
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Double getValor() {
		return valor;
	}
	
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
}
