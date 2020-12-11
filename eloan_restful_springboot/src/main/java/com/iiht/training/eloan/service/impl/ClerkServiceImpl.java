package com.iiht.training.eloan.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.training.eloan.dto.LoanDto;
import com.iiht.training.eloan.dto.LoanOutputDto;
import com.iiht.training.eloan.dto.ManagerDto;
import com.iiht.training.eloan.dto.ProcessingDto;
import com.iiht.training.eloan.dto.UserDto;
import com.iiht.training.eloan.dto.exception.ExceptionResponse;
import com.iiht.training.eloan.entity.Loan;
import com.iiht.training.eloan.entity.Manager;
import com.iiht.training.eloan.entity.ProcessingInfo;
import com.iiht.training.eloan.entity.Users;
import com.iiht.training.eloan.repository.ClerkRepository;
import com.iiht.training.eloan.repository.LoanRepository;
import com.iiht.training.eloan.repository.ProcessingInfoRepository;
import com.iiht.training.eloan.repository.SanctionInfoRepository;
import com.iiht.training.eloan.repository.UsersRepository;
import com.iiht.training.eloan.service.ClerkService;
import com.iiht.training.eloan.util.EloanUtil;

@Service
public class ClerkServiceImpl implements ClerkService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private ClerkRepository clerkRepository;
	
	
	@Autowired
	private ProcessingInfoRepository processingInfoRepository;
	
	@Autowired
	private SanctionInfoRepository sanctionInfoRepository;
	
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
	
	private ProcessingInfo convertProcessingInputDtoToEntity(ProcessingDto processingDto, Long clerkId, Long loanAppId) 
	{
		ProcessingInfo processingEntity = new ProcessingInfo();
		processingEntity.setLoanAppId(loanAppId);
		processingEntity.setLoanClerkId(clerkId);
  	    processingEntity.setSuggestedAmountOfLoan(processingDto.getSuggestedAmountOfLoan());
		processingEntity.setValuationDate(processingDto.getValuationDate());
		processingEntity.setLandValue(processingDto.getLandValue());
		processingEntity.setAcresOfLand(processingDto.getAcresOfLand());
		processingEntity.setAddressOfProperty(processingDto.getAddressOfProperty());
		processingEntity.setAppraisedBy(processingDto.getAppraisedBy());
		return processingEntity;
	}


	
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

	private LoanOutputDto convertLoanEntityToOutputDto(Loan loanApplied, LoanDto loanInputDto) 
	{
		LoanOutputDto loanOutputDto = new LoanOutputDto();
		loanOutputDto.setCustomerId(loanApplied.getCustomerId());
		loanOutputDto.setLoanAppId(loanApplied.getLoanId());
		loanOutputDto.setLoanDto(loanInputDto);
		loanOutputDto.setRemark(loanApplied.getRemark());
		loanOutputDto.setStatus(Integer.toString(loanApplied.getStatus()));
		loanOutputDto.setUserDto(convertUserEntityToOutputDto(this.usersRepository.getOne(loanApplied.getCustomerId())));
		return loanOutputDto;
	}
	
	@Override
	public List<LoanOutputDto> allAppliedLoans() {
		List<Loan> list_loanFetched = this.loanRepository.findAllAppliedLoans();
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

			list_loanOutputDto.add(convertLoanEntityToOutputDto(loanFetched, loanDto));
		}

		return list_loanOutputDto;

	}

	@Override
	public ProcessingDto processLoan(Long clerkId, Long loanAppId, ProcessingDto processingDto)  {
	
				ProcessingInfo processingEntity=null;
				List<ProcessingInfo> list_processingInfo = this.processingInfoRepository.checkExistsByLoanId(loanAppId);
				processingEntity= this.processingInfoRepository.save(convertProcessingInputDtoToEntity(processingDto, clerkId, loanAppId));
				this.loanRepository.UpdateLoanStatus(loanAppId,1,"loan processing done");
				return convertProcessingEntityToOutputDto(processingEntity);
	}
	
	
	public boolean getLoanById(Long loanAppId) {	
		return this.loanRepository.existsById(loanAppId);
	}
	
	public boolean getClerkById(Long clerkId) {
		return this.clerkRepository.existsById(clerkId);
	}


}
