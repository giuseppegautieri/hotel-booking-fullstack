package com.giuseppe.hotel.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	private final JavaMailSender mailSender;
	
	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendBookingConfirmation(String toEmail, Long reservationId, String roomNumber, Double totalPrice) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("noreply@springhotel.com");
		
		message.setTo(toEmail);
		message.setSubject("conferma Prenotazione - Spring Hotel");
		message.setText("Gentile Cliente, \n" + "Siamo felici di confermare la sua prenotazione presso Spring Hotel! \n\n" + "Dettagli della prenotazione:\n" + "- ID Prenotazione: #" +  reservationId + "\n" + "- Numero Stanza: Stanza " + roomNumber + "\n" + "- Prezzo Totale: " + totalPrice + "€\n\n" + "Ti aspettiamo!\n" + "Lo Staff del Spring Hotel");
		
		mailSender.send(message);
		System.out.println("Email di conferma inviata con successo a: " + toEmail);
	}
}
