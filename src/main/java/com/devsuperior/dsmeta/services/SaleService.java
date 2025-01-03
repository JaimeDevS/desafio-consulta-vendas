package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
	
	public Page<SaleReportDTO> findReport(Pageable pageable, String minDate, String maxDate, String name) {
		LocalDate startDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).minusYears(1L);
		LocalDate endDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		
		if (isNullOrEmpty(name)) {
			name = "%%%";
		} 
		
		if (!isNullOrEmpty(minDate)) {
			startDate = formattedDate(minDate);
		} 

		if (!isNullOrEmpty(maxDate)) {
			endDate = formattedDate(maxDate);
		} 
		
		Page<SaleReportDTO> result = repository.searchReport(pageable, startDate, endDate, name);
		return result;
	}

	public Page<SaleSummaryDTO> findSummary(Pageable pageable, String minDate, String maxDate) {
		LocalDate startDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).minusYears(1L);
		LocalDate endDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		
		if (!isNullOrEmpty(minDate)) {
			startDate = formattedDate(minDate);
		} 

		if (!isNullOrEmpty(maxDate)) {
			endDate = formattedDate(maxDate);
		} 
		
		Page<SaleSummaryDTO> result = repository.searchSummary(pageable, startDate, endDate);
		return result;
	}

	private Boolean isNullOrEmpty(String string) {
		return string == null || string.isEmpty();
	}

	private LocalDate formattedDate(String date) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dt = LocalDate.parse(date, format);
		return dt;
	}
}
