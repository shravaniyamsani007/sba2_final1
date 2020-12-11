package com.iiht.training.eloan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

@Entity
public class ProcessingInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length=5, nullable=false)
	@ColumnDefault("40000")
	private Long processingId;
	
    @Column(length=5, nullable=false)
	private Long loanAppId;
	
    @Column(length=5, nullable=false)
	private Long loanClerkId;
	
    @Column(nullable=false)
	private Double acresOfLand;
	
    @Column(nullable=false)
	private Double landValue;
	
    @Column(nullable=false)
	private String appraisedBy;
	
    @Column(nullable=false)
	private String valuationDate;
	
    @Column(length=150, nullable=false)
	private String addressOfProperty;
	
    @Column(nullable=false)
	private Double suggestedAmountOfLoan;
	
	public Long getProcessingId() {
		return processingId;
	}
	public void setProcessingId(Long processingId) {
		this.processingId = processingId;
	}
	public Long getLoanAppId() {
		return loanAppId;
	}
	public void setLoanAppId(Long loanAppId) {
		this.loanAppId = loanAppId;
	}
	public Long getLoanClerkId() {
		return loanClerkId;
	}
	public void setLoanClerkId(Long loanClerkId) {
		this.loanClerkId = loanClerkId;
	}
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
