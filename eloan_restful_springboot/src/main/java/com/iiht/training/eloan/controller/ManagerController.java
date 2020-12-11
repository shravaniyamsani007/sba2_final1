package com.iiht.training.eloan.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iiht.training.eloan.dto.LoanOutputDto;
import com.iiht.training.eloan.dto.ProcessingDto;
import com.iiht.training.eloan.dto.RejectDto;
import com.iiht.training.eloan.dto.SanctionDto;
import com.iiht.training.eloan.dto.SanctionOutputDto;
import com.iiht.training.eloan.dto.exception.ExceptionResponse;
import com.iiht.training.eloan.exception.AlreadyFinalizedException;
import com.iiht.training.eloan.exception.AlreadyProcessedException;
import com.iiht.training.eloan.exception.ClerkNotFoundException;
import com.iiht.training.eloan.exception.LoanNotFoundException;
import com.iiht.training.eloan.exception.ManagerNotFoundException;
import com.iiht.training.eloan.service.ManagerService;

@RestController
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	private ManagerService managerService;
	
	@GetMapping("/all-processed")
	public ResponseEntity<List<LoanOutputDto>> allProcessedLoans() {
		List<LoanOutputDto> loanOutputDto = this.managerService.allProcessedLoans();
		ResponseEntity<List<LoanOutputDto>> response = new ResponseEntity<List<LoanOutputDto>>(loanOutputDto, HttpStatus.OK);
		return response;
	}
	
	@PostMapping("/reject-loan/{managerId}/{loanAppId}")
	public ResponseEntity<LoanOutputDto> rejectLoan(@PathVariable String managerId,
												@PathVariable String loanAppId,
												@RequestBody RejectDto rejectDto){
		Long managerId_long = Long.parseLong(managerId);
		Long loanAppId_long = Long.parseLong(loanAppId);
		
		if(!this.managerService.getManagerById(managerId_long)) {
			throw new ManagerNotFoundException("Manager not found with Id : " + managerId);
		}
		if (!this.managerService.getLoanById(loanAppId_long)) {
			throw new LoanNotFoundException("Loan not found with Id : " + loanAppId);
		}
		LoanOutputDto  loanOutputDto = this.managerService.rejectLoan(managerId_long, loanAppId_long, rejectDto);
		if (loanOutputDto==null) {
			throw new AlreadyProcessedException("Loan is already processed with the given loan id : " + loanAppId);
		}
		
		ResponseEntity<LoanOutputDto>  response = new ResponseEntity<LoanOutputDto>(loanOutputDto, HttpStatus.OK);
		return response;
	}
	
	@PostMapping("/sanction-loan/{managerId}/{loanAppId}")
	public ResponseEntity<SanctionOutputDto> sanctionLoan(@Valid @PathVariable Long managerId,	@PathVariable Long loanAppId, @Valid @RequestBody SanctionDto sanctionDto) {
		
		if(!this.managerService.getManagerById(managerId)) {
			throw new ManagerNotFoundException("Manager not found with Id : " + managerId);
		}
		if (!this.managerService.getLoanById(loanAppId)) {
			throw new LoanNotFoundException("Loan not found with Id : " + loanAppId);
		}
		SanctionOutputDto sanctionOutputDto = this.managerService.sanctionLoan(managerId, loanAppId, sanctionDto);
		if (sanctionOutputDto==null) {
			throw new AlreadyProcessedException("Loan is already processed with the given loan id : " + loanAppId);
		}
		
		ResponseEntity<SanctionOutputDto>  response = new ResponseEntity<SanctionOutputDto>(sanctionOutputDto, HttpStatus.OK);
		return response;
	}
	
	@ExceptionHandler(ManagerNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handler(ManagerNotFoundException ex){
		ExceptionResponse exception = 
				new ExceptionResponse(ex.getMessage(),
									  System.currentTimeMillis(),
									  HttpStatus.NOT_FOUND.value());
		ResponseEntity<ExceptionResponse> response =
				new ResponseEntity<ExceptionResponse>(exception, HttpStatus.NOT_FOUND);
		return response;
	}
	
	@ExceptionHandler(AlreadyFinalizedException.class)
	public ResponseEntity<ExceptionResponse> handler(AlreadyFinalizedException ex){
		ExceptionResponse exception = 
				new ExceptionResponse(ex.getMessage(),
									  System.currentTimeMillis(),
									  HttpStatus.BAD_REQUEST.value());
		ResponseEntity<ExceptionResponse> response =
				new ResponseEntity<ExceptionResponse>(exception, HttpStatus.BAD_REQUEST);
		return response;
	}
	@ExceptionHandler(LoanNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handler(LoanNotFoundException ex){
		ExceptionResponse exception = 
				new ExceptionResponse(ex.getMessage(),
									  System.currentTimeMillis(),
									  HttpStatus.NOT_FOUND.value());
		ResponseEntity<ExceptionResponse> response =
				new ResponseEntity<ExceptionResponse>(exception, HttpStatus.NOT_FOUND);
		return response;
	}
}
