package com.iiht.training.eloan.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

@Entity
public class Users {

	@Id
    @Column(length=5, nullable=false)
	@ColumnDefault("70000")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	
    @Column(length=100, nullable=false)
	private String firstName;
	
    @Column(length=100, nullable=false)
	private String lastName;
	
    @Column(length=100, nullable=false)
	private String email;
	
    @Column(length=10, nullable=false)
	private String mobile;
	
    @Column(length=100, nullable=false)
	private String address;
	

	//-----------------------------------------
	@OneToMany(mappedBy="users")
	private List<Loan> loans;
	
	public List<Loan> getLoans() {
		return loans;
	}
	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}
	//------------------------------------------
	
	
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
