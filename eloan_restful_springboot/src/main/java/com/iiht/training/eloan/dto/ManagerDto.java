package com.iiht.training.eloan.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ManagerDto {

	@Length(max = 5, min = 5, message="Manager ID should be of 5 digits")
	@NotNull(message = "Manager ID is required!")
	private Long id;
	
	@NotBlank(message = "Manager Name is required!")
	private String name;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
