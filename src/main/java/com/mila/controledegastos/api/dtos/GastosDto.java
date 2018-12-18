package com.mila.controledegastos.api.dtos;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class GastosDto {
	
	private Long id;
	private Date data;
	private String descricao;
	private Double valor;
	private String tipo;
	private Date dataAtualizacao;
	
	public GastosDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/*
	 *  PENDENTE procurar um validador de datas
	 */
	@NotNull(message = "Data não pode ser vazio.")
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@NotEmpty(message = "Descrição não pode ser vazio.")
	@Length(min = 5, max = 200, message = "Descrição deve conter entre 5 e 200 caracteres.")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@NotNull(message = "Valor não pode ser vazio.")
	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
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
		return "CadastroGastosDto [id=\" + id + \", data=" + data + ", descricao=" + descricao + ", valor=" + valor
				+ ", tipo=" + tipo + ", dataAtualizacao=" + dataAtualizacao + "]";
	}
}
