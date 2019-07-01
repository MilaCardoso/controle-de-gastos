package com.mila.financialproject.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mila.financialproject.api.enums.TransactionType;

@Entity
@Table(name = "type")
public class Type implements Serializable {
	private static final long serialVersionUID = -5754246207015712518L;

	private Long id;
	private String name;
	private TransactionType transactionType;

	public Type() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name", nullable = true)
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
		return "Type [id=\" + id + \", name=" + name + "]";
	}	
}