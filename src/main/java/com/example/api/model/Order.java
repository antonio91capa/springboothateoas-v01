package com.example.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
//@Data
@Table(name = "CUSTOMER_ORDER")
public class Order {

	@Id
	@GeneratedValue
	private Long id;
	private String descripcion;
	private Status status;

	public Order() {
	}

	public Order(String descripcion, Status status) {
		this.descripcion = descripcion;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
