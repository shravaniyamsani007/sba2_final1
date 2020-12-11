package com.iiht.training.eloan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iiht.training.eloan.dto.LoanOutputDto;
import com.iiht.training.eloan.entity.Loan;
import com.iiht.training.eloan.entity.ProcessingInfo;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long>
{
	@Query(value = "SELECT * FROM Loan l WHERE l.customer_id = :customerId", nativeQuery = true)
	List<Loan> findAllByCustomerId(@Param("customerId") Long customerId);	
	
	@Query(value = "SELECT * FROM Loan l WHERE l.status = 0", nativeQuery = true)
	List<Loan> findAllAppliedLoans();
	
	//@Query(value = "Update loan l Set l.status = 1 WHERE l.loan_id = :loanAppId", nativeQuery = true)
	//boolean UpdateLoanStatus(@Param("loanAppId") Long loanAppId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "Update loan l Set l.status = :status, l.remark = :remark WHERE l.loan_id = :loanAppId", nativeQuery = true)
	public void UpdateLoanStatus(@Param("loanAppId") Long loanAppId,@Param("status") Integer status, @Param("remark") String remark);
	
	
	@Query(value = "SELECT * FROM Loan l WHERE l.status = 1", nativeQuery = true)
	List<Loan> findAllProcessedLoans();
	
	@Query(value = "Select * from loan l WHERE l.loan_id = :loanAppId and l.status=:status", nativeQuery = true)
	List<Loan> checkExistsByLoanId(@Param("loanAppId") Long loanAppId, @Param("status") Integer status);
	
}
