package com.iiht.training.eloan.service;

import java.util.List;

import com.iiht.training.eloan.dto.ClerkDto;
import com.iiht.training.eloan.dto.ManagerDto;
import com.iiht.training.eloan.dto.UserDto;

public interface AdminService {

	
	public ClerkDto registerClerk(ClerkDto clerkDto);
		
	public ManagerDto registerManager(ManagerDto managerDto);
	
	public List<ClerkDto> getAllClerks();
		
	public List<ManagerDto> getAllManagers();
	
	
}
