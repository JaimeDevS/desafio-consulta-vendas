package com.devsuperior.dsmeta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleReportDTO>> getReport(
			Pageable pageable,
			@RequestParam(name = "minDate", required = false) String minDate,
			@RequestParam(name = "maxDate", required = false) String maxDate,
			@RequestParam(name = "name", required = false) String name
	) {
		Page<SaleReportDTO> dto = service.findReport(pageable, minDate, maxDate, name);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<Page<SaleSummaryDTO>> getSummary(
				Pageable pageable, 
				@RequestParam(name = "minDate", required = false) String minDate,
				@RequestParam(name = "maxDate", required = false) String maxDate
	) {
		Page<SaleSummaryDTO> dto = service.findSummary(pageable, minDate, maxDate);
		return ResponseEntity.ok(dto);
	}
}
