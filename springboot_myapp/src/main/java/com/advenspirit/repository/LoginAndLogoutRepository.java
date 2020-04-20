package com.advenspirit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.advenspirit.entity.LoginLogoutHistory;

@Repository
public interface LoginAndLogoutRepository extends JpaRepository<LoginLogoutHistory, Long> {
	
	
	@Query( "select linlouthis from LoginLogoutHistory linlouthis where linlouthis.emailId = :empEmailId" )
	Optional<LoginLogoutHistory> findLoginLogoutHistoryByEmailId(@Param("empEmailId") String empEmailId);
	
	@Query( "select linlouthis from LoginLogoutHistory linlouthis where linlouthis.token = :tokenId" )
	Optional<LoginLogoutHistory> findLoginLogoutHistoryBytoken(@Param("tokenId") String tokenId);
	
	//@Query("select linlouthis from LoginLogoutHistory linlouthis inner join linlouthis.employee emp on emp.emailId = :empEmailId")			
	//@Query( "select linlouthis from LoginLogoutHistory linlouthis where linlouthis.emailId = :empEmailId" )
	//Optional<LoginLogoutHistory> findLoginLogoutHistoryByEmailIdWithJoin(@Param("empEmailId") String empEmailId);

}
