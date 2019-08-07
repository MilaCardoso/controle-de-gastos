package com.mila.financialproject.api.dtos;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.mila.financialproject.api.entities.Type;

public class TransactionsDto {
	
	private Long id;
	private Date date;
	private String description;
	private BigDecimal value;
	private Type type;
	private Date updateDate;
	
	public TransactionsDto() {
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
	@NotNull(message = "Date can not be empty.")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@NotEmpty(message = "Description can not be empty.")
	@Length(min = 5, max = 200, message = "Description should be between 5 and 200 characters.")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull(message = "Value can not be empty.")
	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	@Override
	public String toString() {	
		return "TransactionsDto [id=\" + id + \", date=" + date + ", description=" + description + ", value=" + value
				+ ", type=" + type + ", updateDate=" + updateDate + "]";
	}
}
