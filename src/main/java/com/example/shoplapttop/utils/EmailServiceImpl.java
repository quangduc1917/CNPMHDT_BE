package com.example.shoplapttop.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class EmailServiceImpl {
	@Autowired
	private JavaMailSender javaMailSender;
	public void sendMail(String content,String theme,String user) throws Exception {
		 MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	        helper.setTo(user);
	        helper.setText(content);
	        helper.setSubject(theme);
	        javaMailSender.send(message);
	}


	public void sendSimpleEmail(String toEmail,
								String subject,
								String body
	)

	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("19110190@student.hcmute.edu.vn");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		javaMailSender.send(message);
		System.out.println("Mail Send...");


	}
}
