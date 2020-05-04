package com.siemens.controller;

//package com.siemens.controller;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siemens.entity.BusinessPartner;
import com.siemens.exception.ResourceNotFoundException;
import com.siemens.service.BusinessPartnerService;

@RestController
@RequestMapping("/api/v1")
public class BusinessPartnerResource {
	
	@Autowired
	BusinessPartnerService businessPartnerService;
	
	
	@GetMapping("/businessPartners")
	public List<BusinessPartner> findAll() {
		return businessPartnerService.findAll();
	}

	@GetMapping("/businessPartners/{id}")
	public ResponseEntity<BusinessPartner> getBusinesPartnersById(@PathVariable(value = "id") Long bpId) throws ResourceNotFoundException {
		BusinessPartner businessPartner = businessPartnerService.getBusinessPartnersById(bpId);				
		return ResponseEntity.ok().body(businessPartner);
	}

	@PostMapping("/businessPartners")
	public BusinessPartner createEmployee(@Valid @RequestBody BusinessPartner businessPartner) {
		return businessPartnerService.createBusinessPartners(businessPartner);
	}

	@PutMapping("/businessPartners")
	public ResponseEntity<BusinessPartner> updateBusinessPartners(@Valid @RequestBody BusinessPartner bpDetails) throws ResourceNotFoundException { 		
		final BusinessPartner businessPartner = businessPartnerService.updateBusinessPartners(bpDetails);	
		return ResponseEntity.ok(businessPartner);
	}

	@DeleteMapping("/businessPartners/{id}")
	public Map<String, Boolean> deleteBusinessPartners(@PathVariable(value = "id") Long bpId) throws ResourceNotFoundException {
		Map<String, Boolean> response = businessPartnerService.deleteBusinessPartners(bpId);		
		return response;
	}
}
