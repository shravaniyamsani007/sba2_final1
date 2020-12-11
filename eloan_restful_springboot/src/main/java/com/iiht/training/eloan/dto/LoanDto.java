package com.iiht.training.eloan.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class LoanDto {

	@Length(max = 100, min = 3, message="Loan Name should be of minimum 3 characters")
	@NotBlank(message = "Loan Name is required!")
	private String loanName;
	
	@Min(value=1,message="Loan Amount cannot be negitive or zero")
	@NotNull(message = "Loan Amount is required!")
	private Double loanAmount;
	
	@NotBlank(message = "Loan ApplicationDate is required!")
	private String loanApplicationDate;
	
	@NotBlank(message = "Business Structure is required!")
	private String businessStructure;
	
	@NotBlank(message = "Billing Indicator is required!")
	private String billingIndicator;
	
	@NotBlank(message = "Tax Indicator is required!")
	private String taxIndicator;
	
	
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public Double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getLoanApplicationDate() {
		return loanApplicationDate;
	}
	public void setLoanApplicationDate(String loanApplicationDate) {
		this.loanApplicationDate = loanApplicationDate;
	}
	public String getBusinessStructure() {
		return businessStructure;
	}
	public void setBusinessStructure(String businessStructure) {
		this.businessStructure = businessStructure;
	}
	public String getBillingIndicator() {
		return billingIndicator;
	}
	public void setBillingIndicator(String billingIndicator) {
		this.billingIndicator = billingIndicator;
	}
	public String getTaxIndicator() {
		return taxIndicator;
	}
	public void setTaxIndicator(String taxIndicator) {
		this.taxIndicator = taxIndicator;
	}
	
	
}
