package com.iiht.training.eloan.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SanctionDto {
	
	@Min(value=1,message="Loan Amount Sanctioned cannot be negitive or zero")
	@NotNull(message = "Loan Amount Sanctioned is required!")
	private Double loanAmountSanctioned;
	
	@Min(value=1,message="Term of Loan cannot be negitive or zero")
	@NotNull(message = "Term of Loan is required!")
	private Double termOfLoan;
	
	@Min(value=1,message="Interest rate cannot be negitive or zero")
	@NotNull(message = "Interest Rate is required!")
	private Double interestRate;
	
	
	@NotBlank(message = "Payment Start Date is required!")
	private String paymentStartDate;
	
	@NotBlank(message = "Remarks is required!")
	private String remarks;
	

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	
	public Double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
}
