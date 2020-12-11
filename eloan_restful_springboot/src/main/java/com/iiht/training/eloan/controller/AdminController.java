package com.iiht.training.eloan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iiht.training.eloan.dto.ClerkDto;
import com.iiht.training.eloan.dto.ManagerDto;
import com.iiht.training.eloan.dto.UserDto;
import com.iiht.training.eloan.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@PostMapping("/register-clerk")
	public ResponseEntity<ClerkDto> registerClerk(@RequestBody ClerkDto clerkDto){
		ClerkDto clerk = this.adminService.registerClerk(clerkDto);
		ResponseEntity<ClerkDto> response = new ResponseEntity<ClerkDto>(clerk, HttpStatus.OK);
		return response;
	}
	
	@PostMapping("/register-manager")
	public ResponseEntity<ManagerDto> registerManager(@RequestBody ManagerDto managerDto){
		
		ManagerDto manager = this.adminService.registerManager(managerDto);
		ResponseEntity<ManagerDto> response = new ResponseEntity<ManagerDto>(manager, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/all-clerks")
	public ResponseEntity<List<ClerkDto>> getAllClerks()
	{
		
		List<ClerkDto> clerks = this.adminService.getAllClerks();
		ResponseEntity<List<ClerkDto>> response = new ResponseEntity<List<ClerkDto>>(clerks, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/all-managers")
	public ResponseEntity<List<ManagerDto>> getAllManagers(){
		List<ManagerDto> managers = this.adminService.getAllManagers();
		ResponseEntity<List<ManagerDto>> response = new ResponseEntity<List<ManagerDto>>(managers, HttpStatus.OK);
		return response;
		
	}
	
	
}
