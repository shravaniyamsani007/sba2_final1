package com.iiht.training.eloan.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ClerkDto {

	@Length(max = 5, min = 5, message="Clerk ID should be of 5 digits")
	@NotNull(message = "ClerkID is required!")
	private Long clerkId;
	
	@NotBlank(message = "Name is required!")
	private String name;
	
	
	public Long getClerkId() {
		return clerkId;
	}
	public void setClerkId(Long clerkId) {
		this.clerkId = clerkId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
