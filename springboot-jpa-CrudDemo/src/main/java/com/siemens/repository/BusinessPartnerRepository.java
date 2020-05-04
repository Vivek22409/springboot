package com.siemens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.siemens.entity.BusinessPartner;

@Repository("businessPartnerRepository")
public interface BusinessPartnerRepository extends JpaRepository<BusinessPartner, Long>{	

}
