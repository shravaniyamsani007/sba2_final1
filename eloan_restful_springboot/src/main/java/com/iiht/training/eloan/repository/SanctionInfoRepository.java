package com.iiht.training.eloan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iiht.training.eloan.entity.SanctionInfo;

@Repository
public interface SanctionInfoRepository extends JpaRepository<SanctionInfo, Long>
{

	@Query(value = "Select * from sanction_info s WHERE s.loan_app_id = :loanAppId", nativeQuery = true)
	List<SanctionInfo> checkExistsByLoanId(@Param("loanAppId") Long loanAppId);
	
}
