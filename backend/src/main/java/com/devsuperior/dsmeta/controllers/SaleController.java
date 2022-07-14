package com.devsuperior.dsmeta.controllers;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.services.SaleService;
import com.devsuperior.dsmeta.services.SmsService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {
	
	@Autowired
	private SaleService service;
	
	@Autowired
	private SmsService smsService;
	
	@GetMapping
	public Page<Sale> findSales(
			@RequestParam(value = "minDate", defaultValue = "") String minDate,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDate, 
			Pageable pageable) {   /*Método criado para disponibilizar as vendas para o frontend*/
		return service.findSales(minDate, maxDate, pageable);
	}

	@GetMapping("/{id}/nofification")
	public void notifySms(@PathVariable Long id) {
		smsService.sendSms(id);
		
	}
	
	
	
	
	
	
	
	
	
	
	/* 
	 
	ANOTAÇÕES
	=========
	
	- LINHAS 25, 35 a 36 => Mostra a variável criada (linha 25) com a função (linhas 35 e 36)
	para chamar o método ENVIA SMS, constado na classe SmsService.java criada dentro do pacote
	DSMETA.SERVICES.
	 
	- LINHA 35 mencionamos um id (entre chaves), que ao ser gerado o SMS, vai emitir a venda,
	e nome do vendedor 
	 
	 
	 */
}