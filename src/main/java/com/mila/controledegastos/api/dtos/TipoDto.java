package com.mila.controledegastos.api.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class TipoDto {
	
	private Long id;
	private String tipo;
	
	public TipoDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@NotEmpty(message = "Tipo não pode ser vazio.")
	@Length(min = 5, max = 200, message = "Tipo deve conter entre 5 e 200 caracteres.")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public String toString() {	
		return "CadastroTipoDto [id=\" + id + \", +  tipo=" + tipo + "]";
	}
}
