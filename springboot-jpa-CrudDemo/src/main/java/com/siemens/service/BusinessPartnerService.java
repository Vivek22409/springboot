package com.siemens.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.siemens.entity.BusinessPartner;
import com.siemens.exception.ResourceNotFoundException;
import com.siemens.repository.BusinessPartnerRepository;


@Service
public class BusinessPartnerService {

	@Autowired	
	public BusinessPartnerRepository businessPartnerRepository;

	public List<BusinessPartner> findAll() {
		return businessPartnerRepository.findAll();
	}

	public BusinessPartner getBusinessPartnersById(Long bpId) throws ResourceNotFoundException {
		BusinessPartner businessPartner = businessPartnerRepository.findById(bpId)
				.orElseThrow(() -> new ResourceNotFoundException("BusinessPartner not found for this id :: " + bpId));
		return businessPartner;
	}

	
	public BusinessPartner createBusinessPartners(BusinessPartner businessPartner) {
		return businessPartnerRepository.save(businessPartner);
	}

	
	public BusinessPartner updateBusinessPartners(BusinessPartner bpDetails) throws ResourceNotFoundException {
		BusinessPartner bp = businessPartnerRepository.findById(bpDetails.getBP_ID())
				.orElseThrow(() -> new ResourceNotFoundException("BusinessPartner not found for this id :: " + bpDetails.getBP_ID()));
		bp.setBP_ID(bpDetails.getBP_ID());
		bp.setBP_NAME(bpDetails.getBP_NAME());
		bp.setBP_ID_AS_STRING(bpDetails.getBP_ID_AS_STRING());
		bp.setBP_ALIAS(bpDetails.getBP_ALIAS());
		final BusinessPartner businessPartner = businessPartnerRepository.save(bp);
		return businessPartner;
	}


	public Map<String, Boolean> deleteBusinessPartners(Long bpId) throws ResourceNotFoundException {
		BusinessPartner bp = businessPartnerRepository.findById(bpId)
				.orElseThrow(() -> new ResourceNotFoundException("BusinessPartner not found for this id :: " + bpId));
		businessPartnerRepository.delete(bp);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}


}
