package com.iiht.training.eloan.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class UserDto {

	@Length(max = 5, min = 5, message="User ID should be of 5 digits")
	@NotNull(message = "User ID is required!")
	private Long customerId;

	@Length(max = 100, min = 3, message="First Name should be of minimum 3 characters")
	@NotBlank(message = "First Name is required!")
	private String firstName;

	@Length(max = 100, min = 3, message="Last Name should be of minimum 3 characters")
	@NotBlank(message = "Last Name is required!")
	private String lastName;

	@Email
	@Length(max = 100, min = 3, message="Email should be of minimum 3 characters")
	@NotBlank(message = "Email is required!")
	private String email;

	@Length(max = 10, min = 10, message="Mobile should be of 10 digits")
	@NotNull(message = "Mobile number is required!")
	private String mobile;

	@Length(max = 300, min = 3, message="Address should be of minimum 3 characters")
	@NotBlank(message = "Address is required!")
	private String address;


	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
