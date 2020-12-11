package com.iiht.training.eloan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

@Entity
public class SanctionInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length=5, nullable=false)
	@ColumnDefault("50000")
	private Long sanctionId;
	
    @Column(length=5, nullable=false)
	private Long loanAppId;
	
    @Column(length=5, nullable=false)
	private Long managerId;
	
    @Min(1)
    @Column(nullable=false)
	private Double loanAmountSanctioned;
	
	@Min(1)
	@Column(nullable=false)
	private Double termOfLoan;
	
	@Min(1)
	@Column(nullable=false)
	private Double interestRate;

	public Double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
	@Column(nullable=false)
	private String paymentStartDate;
	
	@Column(nullable=false)
	private String loanClosureDate;
	
	@Column(nullable=false)
	private String remarks;
	
	@Column(nullable=false)
	@Min(1)
	private Double monthlyPayment;
	
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Long getSanctionId() {
		return sanctionId;
	}
	public void setSanctionId(Long sanctionId) {
		this.sanctionId = sanctionId;
	}
	public Long getLoanAppId() {
		return loanAppId;
	}
	public void setLoanAppId(Long loanAppId) {
		this.loanAppId = loanAppId;
	}
	public Long getManagerId() {
		return managerId;
	}
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	public Double getLoanAmountSanctioned() {
		return loanAmountSanctioned;
	}
	public void setLoanAmountSanctioned(Double loanAmountSanctioned) {
		this.loanAmountSanctioned = loanAmountSanctioned;
	}
	public Double getTermOfLoan() {
		return termOfLoan;
	}
	public void setTermOfLoan(Double termOfLoan) {
		this.termOfLoan = termOfLoan;
	}
	public String getPaymentStartDate() {
		return paymentStartDate;
	}
	public void setPaymentStartDate(String paymentStartDate) {
		this.paymentStartDate = paymentStartDate;
	}
	public String getLoanClosureDate() {
		return loanClosureDate;
	}
	public void setLoanClosureDate(String loanClosureDate) {
		this.loanClosureDate = loanClosureDate;
	}
	public Double getMonthlyPayment() {
		return monthlyPayment;
	}
	public void setMonthlyPayment(Double monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

}
