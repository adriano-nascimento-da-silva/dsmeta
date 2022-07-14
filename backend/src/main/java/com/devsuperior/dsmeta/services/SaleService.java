package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service      /* registra SaleService como componente do sistema*/
public class SaleService {
	
	@Autowired
	private SaleRepository repository;

	public Page<Sale> findSales(String minDate, String maxDate, Pageable pageable) {
		
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()); /*Cria data com dia de hoje*/
		
		
		LocalDate min = minDate.equals("") ? today.minusDays(365) : LocalDate.parse(minDate); /*Se maxDate for igual a espaço vazio então coloca: data de hoje. Senão data 1 ano atrás*/
		LocalDate max = maxDate.equals("") ? today : LocalDate.parse(maxDate); /*Se maxDate for igual a espaço vazio então coloca: data de hoje. Senão faz conversão para data do LocalDate*/
		
		return repository.findSales(min, max, pageable);
		
	}
}
