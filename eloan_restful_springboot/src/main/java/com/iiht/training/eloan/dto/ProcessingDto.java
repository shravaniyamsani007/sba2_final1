package com.iiht.training.eloan.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ProcessingDto {
	
	@Min(value=1, message="Acres Of Land cannot be negitive or zero")
	@NotNull(message = "Acres Of Land is required!")
	private Double acresOfLand;
	
	@Min(value=1, message="Land Value cannot be negitive or zero")
	@NotNull(message = "Land Value is required!")
	private Double landValue;
	
	@NotBlank(message = "Appraised By is required!")
	private String appraisedBy;
	
	@NotBlank(message = "Valuation is required!")
	private String valuationDate;
	
	@Length(max = 150, min = 3, message="Address of Property should be of minimum 3 characters")
	@NotBlank(message = "Address of Property is required!")
	private String addressOfProperty;
	
	@Min(value=1, message="Suggested Amount of Loan cannot be negitive or zero")
	@NotNull(message = "Suggested Amount of Loan is required!")
	private Double suggestedAmountOfLoan;
	
	public Double getAcresOfLand() {
		return acresOfLand;
	}
	public void setAcresOfLand(Double acresOfLand) {
		this.acresOfLand = acresOfLand;
	}
	public Double getLandValue() {
		return landValue;
	}
	public void setLandValue(Double landValue) {
		this.landValue = landValue;
	}
	public String getAppraisedBy() {
		return appraisedBy;
	}
	public void setAppraisedBy(String appraisedBy) {
		this.appraisedBy = appraisedBy;
	}
	public String getValuationDate() {
		return valuationDate;
	}
	public void setValuationDate(String valuationDate) {
		this.valuationDate = valuationDate;
	}
	public String getAddressOfProperty() {
		return addressOfProperty;
	}
	public void setAddressOfProperty(String addressOfProperty) {
		this.addressOfProperty = addressOfProperty;
	}
	public Double getSuggestedAmountOfLoan() {
		return suggestedAmountOfLoan;
	}
	public void setSuggestedAmountOfLoan(Double suggestedAmountOfLoan) {
		this.suggestedAmountOfLoan = suggestedAmountOfLoan;
	}
	
	
}
