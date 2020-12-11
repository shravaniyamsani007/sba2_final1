package com.iiht.training.eloan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iiht.training.eloan.entity.Loan;
import com.iiht.training.eloan.entity.ProcessingInfo;

@Repository
public interface ProcessingInfoRepository extends JpaRepository<ProcessingInfo, Long>{
	
	@Query(value = "Select * from processing_info p WHERE p.loan_app_id = :loanAppId", nativeQuery = true)
	List<ProcessingInfo> checkExistsByLoanId(@Param("loanAppId") Long loanAppId);
	
	
	/*
	 * @Query(value =
	 * "Select * from processing_info p WHERE p.loan_app_id = :loanAppId",
	 * nativeQuery = true) boolean updateAllLoanInfo(@Param("loanAppId") Long
	 * loanAppId);
	 */
	
}
