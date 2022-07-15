package com.devsuperior.dsmeta.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service                  //Está registrada como componente do Service
public class SmsService {

	@Value("${twilio.sid}")
	private String twilioSid;

	@Value("${twilio.key}")
	private String twilioKey;

	@Value("${twilio.phone.from}")
	private String twilioPhoneFrom;

	@Value("${twilio.phone.to}")
	private String twilioPhoneTo;
	
	@Autowired
	private SaleRepository saleRepository;

	public void sendSms(Long saleId) {
		
		Sale sale = saleRepository.findById(saleId).get(); // mostra ID do vendedor (nome vendedor)
		
		String date = sale.getDate().getMonthValue() + "/" + sale.getDate().getYear(); //mostra data da venda na mensagem SMS
		
		String msg = "Vendedor " + sale.getSellerName() + " foi destaque em " + date
		+ " com um total de R$ " + String.format("%.2f", sale.getAmount()).replace(".", ","); // LINHA 37, 38 - mensagem da venda dentro da função que vai por SMS

		Twilio.init(twilioSid, twilioKey);

		PhoneNumber to = new PhoneNumber(twilioPhoneTo);
		PhoneNumber from = new PhoneNumber(twilioPhoneFrom);

		Message message = Message.creator(to, from, msg).create();

		System.out.println(message.getSid());
	}
}








/*ANOTAÇÕES
 ==========
 
 LINHA 13 a 22 => Dentro da anotation @VALUE aqui temos 4 dados:
 
 - 4 Strings com a anotation @Value com nomes dentro das chaves, que representam os campos
 mostrados na src/main/resources/aplication.properties (LINHAS 1 a 4).
 
 - Estes nomes referenciam os valores sensíveis disponíveis dentro da conta que abrimos no 
 Twilio, e que não devem ser repassados.
 
 LINHAS 25 a 34 => Programa envio de SMS pelo java, disponível na documentação da Twílio.
 
 
 */
 