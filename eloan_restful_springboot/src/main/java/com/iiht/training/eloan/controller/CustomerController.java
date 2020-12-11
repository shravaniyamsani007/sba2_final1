package com.iiht.training.eloan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iiht.training.eloan.dto.ClerkDto;
import com.iiht.training.eloan.dto.LoanDto;
import com.iiht.training.eloan.dto.LoanOutputDto;
import com.iiht.training.eloan.dto.UserDto;
import com.iiht.training.eloan.dto.exception.ExceptionResponse;
import com.iiht.training.eloan.exception.CustomerException;
import com.iiht.training.eloan.exception.CustomerNotFoundException;
import com.iiht.training.eloan.exception.InvalidDataException;
import com.iiht.training.eloan.exception.LoanNotFoundException;
import com.iiht.training.eloan.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> register(@RequestBody UserDto userDto){
	    UserDto user = this.customerService.register(userDto);
		ResponseEntity<UserDto> response = new ResponseEntity<UserDto>(user, HttpStatus.OK);
		return response;
	
	}
	
	@PostMapping("/apply-loan/{customerId}")
	public ResponseEntity<LoanOutputDto> applyLoan(@PathVariable String customerId, @RequestBody LoanDto loanDto, BindingResult result) {
		if(result.hasErrors()) {
			// throw custom exception
			throw new InvalidDataException("Invalid Data Format!");
		}
		
	
		LoanOutputDto loanOutputDto = this.customerService.applyLoan(Long.parseLong(customerId), loanDto);	
		if(loanOutputDto == null) {
		// throw custom exception
			throw new CustomerNotFoundException("Customer not found with Id : " + customerId);
		}
		
		ResponseEntity<LoanOutputDto> response = new ResponseEntity<LoanOutputDto>(loanOutputDto, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/loan-status/{loanAppId}")
	public ResponseEntity<LoanOutputDto> getStatus(@PathVariable String loanAppId){
		
		LoanOutputDto loanOutputDto = this.customerService.getStatus(Long.parseLong(loanAppId));
		if(loanOutputDto == null) {
			// throw custom exception
				throw new LoanNotFoundException("Loan not found with Id : " + loanAppId);
			}
		ResponseEntity<LoanOutputDto> response = new ResponseEntity<LoanOutputDto>(loanOutputDto, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/loan-status-all/{customerId}")
	public ResponseEntity<List<LoanOutputDto>> getStatusAll(@PathVariable String customerId){
		List<LoanOutputDto> list_loanOutputDto = this.customerService.getStatusAll(Long.parseLong(customerId));
		if(list_loanOutputDto == null) {
			// throw custom exception
				throw new CustomerNotFoundException("Customer not found with Id : " + customerId);
			}
		ResponseEntity<List<LoanOutputDto>> response = new ResponseEntity<List<LoanOutputDto>>(list_loanOutputDto, HttpStatus.OK);
		return response;
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handler(CustomerNotFoundException ex){
		ExceptionResponse exception = 
				new ExceptionResponse(ex.getMessage(),
									  System.currentTimeMillis(),
									  HttpStatus.NOT_FOUND.value());
		ResponseEntity<ExceptionResponse> response =
				new ResponseEntity<ExceptionResponse>(exception, HttpStatus.NOT_FOUND);
		return response;
	}
	
	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<ExceptionResponse> handler(InvalidDataException ex){
		ExceptionResponse exception = 
				new ExceptionResponse(ex.getMessage(),
									  System.currentTimeMillis(),
									  HttpStatus.NOT_FOUND.value());
		ResponseEntity<ExceptionResponse> response =
				new ResponseEntity<ExceptionResponse>(exception, HttpStatus.NOT_FOUND);
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
