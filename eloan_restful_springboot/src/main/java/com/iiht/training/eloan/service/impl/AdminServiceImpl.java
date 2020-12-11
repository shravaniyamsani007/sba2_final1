package com.iiht.training.eloan.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.training.eloan.dto.ClerkDto;
import com.iiht.training.eloan.dto.ManagerDto;
import com.iiht.training.eloan.dto.UserDto;
import com.iiht.training.eloan.dto.exception.ExceptionResponse;
import com.iiht.training.eloan.entity.Clerk;
import com.iiht.training.eloan.entity.Manager;
import com.iiht.training.eloan.repository.ClerkRepository;
import com.iiht.training.eloan.repository.ManagerRepository;
import com.iiht.training.eloan.repository.UsersRepository;
import com.iiht.training.eloan.service.AdminService;
import com.iiht.training.eloan.util.EloanUtil;


@Service
public class AdminServiceImpl implements AdminService {

	//ClerkUtil clerkUtil = new ClerkUtil();
	private ClerkDto convertClerkEntityToOutputDto(Clerk clerk) 
	{
		ClerkDto clerkOutputDto = new ClerkDto();
		clerkOutputDto.setClerkId(clerk.getClerkId());
		clerkOutputDto.setName(clerk.getName());
		return clerkOutputDto;
	}
	
	
	private Clerk convertClerkInputDtoToEntity(ClerkDto clerkInputDTO) 
	{
		Clerk clerk = new Clerk();
		clerk.setClerkId(clerkInputDTO.getClerkId());
		clerk.setName(clerkInputDTO.getName());
		
		return clerk;
	}
	
	private ManagerDto convertManagerEntityToOutputDto(Manager manager) 
	{
		ManagerDto managerOutputDto = new ManagerDto();
		managerOutputDto.setId(manager.getManagerId());
		managerOutputDto.setName(manager.getName());
		return managerOutputDto;
	}
	
	
	private Manager convertManagerInputDtoToEntity(ManagerDto managerInputDTO) 
	{
		Manager manager = new Manager();
		manager.setManagerId(managerInputDTO.getId());
		manager.setName(managerInputDTO.getName());
		
		return manager;
	}
	
	
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private ClerkRepository clerkRepository;
	
	@Autowired
	private ManagerRepository managerRepository;
	
	
	public ClerkDto registerClerk(ClerkDto clerkDto) {
		Clerk clerk = this.clerkRepository.save(convertClerkInputDtoToEntity(clerkDto));
		return convertClerkEntityToOutputDto(clerk);
		
	}

	@Override
	public ManagerDto registerManager(ManagerDto managerDto) {
		
		
		Manager manager = this.managerRepository.save(convertManagerInputDtoToEntity(managerDto));
		return convertManagerEntityToOutputDto(manager);
	}

	@Override
	public List<ClerkDto> getAllClerks() 
	{
		List<Clerk> clerks = this.clerkRepository.findAll();
		List<ClerkDto> clerkDtos = clerks.stream().map(this :: convertClerkEntityToOutputDto).collect(Collectors.toList());
		return clerkDtos;
	}

	@Override
	public List<ManagerDto> getAllManagers() {
		// TODO Auto-generated method stub
		List<Manager> managers=this.managerRepository.findAll();
		List<ManagerDto> managerDtos = managers.stream().map(this :: convertManagerEntityToOutputDto).collect(Collectors.toList());
		return managerDtos;
	
	}

}
