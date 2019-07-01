package com.mila.financialproject.api.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transactions implements Serializable {
	private static final long serialVersionUID = -5754246207015712518L;

	private Long id;
	private Date date;
	private String description;
	private Double value;
	private Type type;
	private Date updateDate;

	public Transactions() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "date", nullable = true)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "update_date", nullable = true)
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "description", nullable = true)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "value", nullable = true)
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@PreUpdate
	public void preUpdate() {
		updateDate = new Date();
	}

	@PrePersist
	public void prePersist() {
		final Date atual = new Date();
		updateDate = atual;
	}

	@Override
	public String toString() {
		return "transactions [id=\" + id + \", date=" + date + ", description=" + description + ", value=" + value
				+ ", type=" + type + ", updateDate=" + updateDate + "]";
	}

}