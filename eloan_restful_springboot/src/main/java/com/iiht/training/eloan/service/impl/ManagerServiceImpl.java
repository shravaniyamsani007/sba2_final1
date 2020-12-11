package com.iiht.training.eloan.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.training.eloan.dto.LoanDto;
import com.iiht.training.eloan.dto.LoanOutputDto;
import com.iiht.training.eloan.dto.ManagerDto;
import com.iiht.training.eloan.dto.ProcessingDto;
import com.iiht.training.eloan.dto.RejectDto;
import com.iiht.training.eloan.dto.SanctionDto;
import com.iiht.training.eloan.dto.SanctionOutputDto;
import com.iiht.training.eloan.dto.UserDto;
import com.iiht.training.eloan.dto.exception.ExceptionResponse;
import com.iiht.training.eloan.entity.Loan;
import com.iiht.training.eloan.entity.Manager;
import com.iiht.training.eloan.entity.ProcessingInfo;
import com.iiht.training.eloan.entity.SanctionInfo;
import com.iiht.training.eloan.entity.Users;
import com.iiht.training.eloan.repository.LoanRepository;
import com.iiht.training.eloan.repository.ManagerRepository;
import com.iiht.training.eloan.repository.ProcessingInfoRepository;
import com.iiht.training.eloan.repository.SanctionInfoRepository;
import com.iiht.training.eloan.repository.UsersRepository;
import com.iiht.training.eloan.service.ManagerService;
import com.iiht.training.eloan.util.EloanUtil;

@Service
public class ManagerServiceImpl implements ManagerService {

	private ManagerDto convertManagerEntityToOutputDto(Manager manager) 
	{
		ManagerDto managerDto=new ManagerDto();
		managerDto.setId(manager.getManagerId());
		managerDto.setName(manager.getName());
		return managerDto;
	}
	
	private Manager convertManagerInputDtoToEntity(ManagerDto managerDto, Long clerkId, Long loanAppId) 
	{
		Manager managerEntity = new Manager();
		managerEntity.setManagerId(managerDto.getId());
		managerEntity.setName(managerDto.getName());
		return managerEntity;
	}
	
	
	private SanctionOutputDto convertSanctionInfoEntityToOutputDto(SanctionInfo sanctionInfo) 
	{
		SanctionOutputDto sanctionOutputDto = new SanctionOutputDto();
		sanctionOutputDto.setLoanAmountSanctioned(sanctionInfo.getLoanAmountSanctioned());
		sanctionOutputDto.setLoanClosureDate(sanctionInfo.getLoanClosureDate());
		sanctionOutputDto.setMonthlyPayment(sanctionInfo.getMonthlyPayment());
		sanctionOutputDto.setPaymentStartDate(sanctionInfo.getPaymentStartDate());
		sanctionOutputDto.setTermOfLoan(sanctionInfo.getTermOfLoan());
		sanctionOutputDto.setInterestRate(sanctionInfo.getInterestRate());
		sanctionOutputDto.setRemarks(sanctionInfo.getRemarks());
		return sanctionOutputDto;
		
	}
	
	
	private SanctionInfo convertSanctionInputDtoToEntity(SanctionDto sanctionDto, Long managerId, Long loanAppId) 
	{
		SanctionInfo sanctionEntity = new SanctionInfo();
		sanctionEntity.setLoanAmountSanctioned(sanctionDto.getLoanAmountSanctioned());
		sanctionEntity.setTermOfLoan(sanctionDto.getTermOfLoan());
		sanctionEntity.setPaymentStartDate(sanctionDto.getPaymentStartDate());
		
		double termPaymentAmount = (sanctionDto.getLoanAmountSanctioned()) * Math.pow((1 + sanctionDto.getInterestRate()/100), sanctionDto.getTermOfLoan());
		double monthlyPayment = termPaymentAmount / sanctionDto.getTermOfLoan();
		String loanClosureDate = getLastPaymentDate(sanctionDto.getPaymentStartDate(), Double.toString(sanctionDto.getTermOfLoan()));
				
		sanctionEntity.setInterestRate(sanctionDto.getInterestRate());
		sanctionEntity.setLoanAppId(loanAppId);
		sanctionEntity.setLoanClosureDate(loanClosureDate);
		sanctionEntity.setManagerId(managerId);
		sanctionEntity.setRemarks(sanctionDto.getRemarks());
		sanctionEntity.setMonthlyPayment(monthlyPayment);
		
		return sanctionEntity;
	}
	
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private ProcessingInfoRepository processingInfoRepository;
	
	@Autowired
	private SanctionInfoRepository sanctionInfoRepository;
	
	@Autowired
	private ManagerRepository managerRepository;
	

	
	
	
	
	private UserDto convertUserEntityToOutputDto(Users user) 
	{
		UserDto userOutputDto = new UserDto();
		userOutputDto.setCustomerId(user.getCustomerId());
		userOutputDto.setFirstName(user.getFirstName());
		userOutputDto.setLastName(user.getLastName());
		userOutputDto.setEmail(user.getEmail());
		userOutputDto.setMobile(user.getMobile());
		userOutputDto.setAddress(user.getAddress());
		return userOutputDto;
	}

	private LoanOutputDto convertLoanEntityToOutputDto(Loan loanApplied, LoanDto loanInputDto, ProcessingDto processingDto, SanctionOutputDto sanctionOutputDto) 
	{
		LoanOutputDto loanOutputDto = new LoanOutputDto();
		loanOutputDto.setCustomerId(loanApplied.getCustomerId());
		loanOutputDto.setLoanAppId(loanApplied.getLoanId());
		loanOutputDto.setLoanDto(loanInputDto);
		loanOutputDto.setRemark(loanApplied.getRemark());
		loanOutputDto.setStatus(Integer.toString(loanApplied.getStatus()));
		loanOutputDto.setUserDto(convertUserEntityToOutputDto(this.usersRepository.getOne(loanApplied.getCustomerId())));
		
		
		loanOutputDto.setSanctionOutputDto(sanctionOutputDto);
		loanOutputDto.setProcessingDto(processingDto);
		
		return loanOutputDto;
	}
	
	@Override
	public List<LoanOutputDto> allProcessedLoans() {
		List<Loan> list_loanFetched = this.loanRepository.findAllProcessedLoans();
		//List<LoanOutputDto> List_loanOutputDto = list_loanFetched.stream().map(this :: convertManagerEntityToOutputDto).collect(Collectors.toList());
		List<LoanOutputDto> list_loanOutputDto = new ArrayList<LoanOutputDto>();
		LoanDto loanDto;
		
		for (Loan loanFetched : list_loanFetched)
		{
			loanDto= new LoanDto();
			loanDto.setLoanName(loanFetched.getLoanName());
			loanDto.setLoanApplicationDate(loanFetched.getLoanApplicationDate());
			loanDto.setBusinessStructure(loanFetched.getBusinessStructure());
			loanDto.setBillingIndicator(loanFetched.getBillingIndicator());
			loanDto.setTaxIndicator(loanFetched.getTaxIndicator());
			loanDto.setLoanAmount(loanFetched.getLoanAmount());

			SanctionOutputDto sanctionOutputDto = null;
			List<SanctionInfo> list_sanctionInfo = this.sanctionInfoRepository.checkExistsByLoanId(loanFetched.getLoanId());
			
			if(!list_sanctionInfo.isEmpty())
			{
				sanctionOutputDto = convertSanctionInfoEntityToOutputDto(list_sanctionInfo.get(0));
			}
			
			list_loanOutputDto.add(convertLoanEntityToOutputDto(loanFetched, loanDto, null, sanctionOutputDto));
		}


		return list_loanOutputDto;

	}

	@Override
	public LoanOutputDto rejectLoan(Long managerId, Long loanAppId, RejectDto rejectDto)// throws ExceptionResponse 
	{
		
		//List<Loan> list_processingInfo = this.loanRepository.checkExistsByLoanId(loanAppId, 1);
		
		this.loanRepository.UpdateLoanStatus(loanAppId,-1, rejectDto.getRemark());
		
		Loan loanApplied = this.loanRepository.getOne(loanAppId);
		LoanDto loanInputDto =new LoanDto();
		loanInputDto.setLoanAmount(loanApplied.getLoanAmount());
		loanInputDto.setLoanApplicationDate(loanApplied.getLoanApplicationDate());
		loanInputDto.setBillingIndicator(loanApplied.getBillingIndicator());
		loanInputDto.setBusinessStructure(loanApplied.getBusinessStructure());
		loanInputDto.setLoanName(loanApplied.getLoanName());
		loanInputDto.setTaxIndicator(loanApplied.getTaxIndicator());
		
		return convertLoanEntityToOutputDto(loanApplied, loanInputDto, null, null);	
	
	}

	
	@Override
	public SanctionOutputDto sanctionLoan(Long managerId, Long loanAppId, SanctionDto sanctionDto) {

		List<Loan> list_processingInfo = this.loanRepository.checkExistsByLoanId(loanAppId, 1);
		
		if(loanAppId!=null)
		{
			if(list_processingInfo.isEmpty())
			{		
				//throw new ExceptionResponse("Loan App ID "+loanAppId+" don't  exist in the Processing Info Table",EloanUtil.currentTimestamp(), -1);					
			}
			else 
			{ 
				SanctionOutputDto sanctionOutputDto = convertSanctionInfoEntityToOutputDto(this.sanctionInfoRepository.save(convertSanctionInputDtoToEntity(sanctionDto, managerId, loanAppId)));
				this.loanRepository.UpdateLoanStatus(loanAppId, 2, sanctionDto.getRemarks());
				return sanctionOutputDto;
			}
		}
		
		return null;
	}
	
	public static String getLastPaymentDate(String startDate, String termLoan)
	{
		 LocalDate date = LocalDate.parse(startDate); 
		 LocalDate returnvalue = date.plusMonths(Integer.parseInt(termLoan.split("\\.")[0]));

		 System.out.println(returnvalue.toString()); 

		return returnvalue.toString();
	}
	
	public boolean getLoanById(Long loanAppId) {	
		return this.loanRepository.existsById(loanAppId);
	}
	
	public boolean getManagerById(Long managerId) {
		return this.managerRepository.existsById(managerId);
	}

}
