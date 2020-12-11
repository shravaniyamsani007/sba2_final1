package com.iiht.training.eloan.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.training.eloan.dto.ClerkDto;
import com.iiht.training.eloan.dto.LoanDto;
import com.iiht.training.eloan.dto.LoanOutputDto;
import com.iiht.training.eloan.dto.ManagerDto;
import com.iiht.training.eloan.dto.ProcessingDto;
import com.iiht.training.eloan.dto.SanctionDto;
import com.iiht.training.eloan.dto.SanctionOutputDto;
import com.iiht.training.eloan.dto.UserDto;
import com.iiht.training.eloan.dto.exception.ExceptionResponse;
import com.iiht.training.eloan.entity.Clerk;
import com.iiht.training.eloan.entity.Loan;
import com.iiht.training.eloan.entity.ProcessingInfo;
import com.iiht.training.eloan.entity.SanctionInfo;
import com.iiht.training.eloan.entity.Users;
import com.iiht.training.eloan.repository.LoanRepository;
import com.iiht.training.eloan.repository.ProcessingInfoRepository;
import com.iiht.training.eloan.repository.SanctionInfoRepository;
import com.iiht.training.eloan.repository.UsersRepository;
import com.iiht.training.eloan.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private ProcessingInfoRepository processingInfoRepository;

	@Autowired
	private SanctionInfoRepository sanctionInfoRepository;


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


	private Users convertUserInputDtoToEntity(UserDto userInputDto) 
	{
		Users user = new Users();
		user.setCustomerId(userInputDto.getCustomerId());
		user.setFirstName(userInputDto.getFirstName());
		user.setLastName(userInputDto.getLastName());
		user.setEmail(userInputDto.getEmail());
		user.setMobile(userInputDto.getMobile());
		user.setAddress(userInputDto.getAddress());
		return user;
	}

	private LoanOutputDto convertLoanEntityToOutputDto(Loan loanApplied, LoanDto loanInputDto, SanctionOutputDto sanctionOutputDto, ProcessingDto processingDto) 
	{
		LoanOutputDto loanOutputDto = new LoanOutputDto();
		loanOutputDto.setCustomerId(loanApplied.getCustomerId());
		loanOutputDto.setLoanAppId(loanApplied.getLoanId());
		loanOutputDto.setLoanDto(loanInputDto);
		loanOutputDto.setRemark(loanApplied.getRemark());
		loanOutputDto.setStatus(Integer.toString(loanApplied.getStatus()));
		loanOutputDto.setUserDto(convertUserEntityToOutputDto(this.usersRepository.getOne(loanApplied.getCustomerId())));
		
		loanOutputDto.setProcessingDto(processingDto);
		loanOutputDto.setSanctionOutputDto(sanctionOutputDto);
		
		return loanOutputDto;
	}

	private Loan convertLoanInputDtoToEntity(Long customerId, LoanDto loanInputDto) 
	{
		Loan loan = new Loan();
		loan.setLoanName(loanInputDto.getLoanName());
		loan.setLoanAmount(loanInputDto.getLoanAmount());
		loan.setCustomerId(customerId);
		loan.setLoanApplicationDate(loanInputDto.getLoanApplicationDate());
		loan.setBusinessStructure(loanInputDto.getBusinessStructure());
		loan.setBillingIndicator(loanInputDto.getBillingIndicator());
		loan.setTaxIndicator(loanInputDto.getTaxIndicator());
		loan.setRemark("request for loan");
		loan.setStatus(0);
		return loan;
	}
	
	private ProcessingDto convertProcessingEntityToOutputDto(ProcessingInfo processingInfo) 
	{
		ProcessingDto processingDto = new ProcessingDto();
		processingDto.setAcresOfLand(processingInfo.getAcresOfLand());
		processingDto.setAddressOfProperty(processingInfo.getAddressOfProperty());
		processingDto.setAppraisedBy(processingInfo.getAppraisedBy());
		processingDto.setLandValue(processingInfo.getLandValue());
		processingDto.setValuationDate(processingInfo.getValuationDate());
		processingDto.setSuggestedAmountOfLoan(processingInfo.getSuggestedAmountOfLoan());	
		return processingDto;
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
	


	@Override
	public UserDto register(UserDto userDto) {

		/*if(userDto!=null)
		{
			if(this.usersRepository.existsById(userDto.getUserId())) 
			{
				throw new ExceptionResponse("User ID "+userDto.getUserId()+"already exist");
			}
		}*/

		return convertUserEntityToOutputDto(this.usersRepository.save(convertUserInputDtoToEntity(userDto)));
	}

	@Override
	public LoanOutputDto applyLoan(Long customerId, LoanDto loanDto) {
		if(this.usersRepository.existsById(customerId)) {
		Loan loanApplied = this.loanRepository.save(convertLoanInputDtoToEntity(customerId, loanDto));
		return convertLoanEntityToOutputDto(loanApplied, loanDto, null, null);
		}
		return null;
	}

	@Override
	public LoanOutputDto getStatus(Long loanAppId) {
		if(this.loanRepository.existsById(loanAppId)) {
		Loan loanFetched = this.loanRepository.getOne(loanAppId);
		LoanDto loanDto= new LoanDto();
		loanDto.setLoanName(loanFetched.getLoanName());
		loanDto.setLoanApplicationDate(loanFetched.getLoanApplicationDate());
		loanDto.setBusinessStructure(loanFetched.getBusinessStructure());
		loanDto.setBillingIndicator(loanFetched.getBillingIndicator());
		loanDto.setTaxIndicator(loanFetched.getTaxIndicator());
		loanDto.setLoanAmount(loanFetched.getLoanAmount());
		
		ProcessingDto processingDto = null;
		List<ProcessingInfo> list_processingInfo = this.processingInfoRepository.checkExistsByLoanId(loanAppId);	
		
		if(!list_processingInfo.isEmpty())
		{
			processingDto = convertProcessingEntityToOutputDto(list_processingInfo.get(0));
		}
		
		SanctionOutputDto sanctionOutputDto = null;
		List<SanctionInfo> list_sanctionInfo = this.sanctionInfoRepository.checkExistsByLoanId(loanAppId);
		
		if(!list_sanctionInfo.isEmpty())
		{
			sanctionOutputDto = convertSanctionInfoEntityToOutputDto(list_sanctionInfo.get(0));
		}
		
		return convertLoanEntityToOutputDto(loanFetched, loanDto, sanctionOutputDto, processingDto);
		}
		return null;

	}

	@Override
	public List<LoanOutputDto> getStatusAll(Long customerId) {

		if(this.usersRepository.existsById(customerId)) {
		List<Loan> list_loanFetched = this.loanRepository.findAllByCustomerId(customerId);	

		List<LoanOutputDto> list_loanOutputDto = new ArrayList<LoanOutputDto>();
		LoanDto loanDto;
		ProcessingDto processingDto;
		SanctionOutputDto sanctionOutputDto;
		List<ProcessingInfo> list_processingInfo;
		List<SanctionInfo> list_sanctionInfo;
		
		for (Loan loanFetched : list_loanFetched)
		{
			loanDto= new LoanDto();
			loanDto.setLoanName(loanFetched.getLoanName());
			loanDto.setLoanApplicationDate(loanFetched.getLoanApplicationDate());
			loanDto.setBusinessStructure(loanFetched.getBusinessStructure());
			loanDto.setBillingIndicator(loanFetched.getBillingIndicator());
			loanDto.setTaxIndicator(loanFetched.getTaxIndicator());
			loanDto.setLoanAmount(loanFetched.getLoanAmount());
			
			list_processingInfo = this.processingInfoRepository.checkExistsByLoanId(loanFetched.getLoanId());
			
			if(!list_processingInfo.isEmpty())
			{
				processingDto = convertProcessingEntityToOutputDto(list_processingInfo.get(0));
			}
			else
			{
				processingDto = null;
			}
			
			list_sanctionInfo = this.sanctionInfoRepository.checkExistsByLoanId(loanFetched.getLoanId());
			
			if(!list_sanctionInfo.isEmpty())
			{
				sanctionOutputDto = convertSanctionInfoEntityToOutputDto(list_sanctionInfo.get(0));
			}
			else
			{
				sanctionOutputDto = null;
			}

			list_loanOutputDto.add(convertLoanEntityToOutputDto(loanFetched, loanDto, sanctionOutputDto, processingDto));
		}


		return list_loanOutputDto;
		}
		return null;
	}


	@Override
	public boolean getUserById(Long customerId) {
		
		
		return this.usersRepository.existsById(customerId);
	}

}
