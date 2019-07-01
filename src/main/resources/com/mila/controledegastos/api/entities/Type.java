package com.mila.controledegastos.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mila.controledegastos.api.enums.TransactionType;

@Entity
@Table(name = "type")
public class Type implements Serializable {
	private static final long serialVersionUID = -5754246207015712518L;

	private Long id;
	private String type;
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

	@Column(name = "type", nullable = true)
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
		return "Type [id=\" + id + \", type=" + type + "]";
	}	
}