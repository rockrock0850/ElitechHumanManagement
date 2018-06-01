package com.elitech.human.resource.util;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailUtil extends SimpleMailMessage{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6956020492341413318L;
	
	private final String FROM = "elitechtw.system@gmail.com";
	
	private MailSender sender;
	
	public static MailUtil getMailObj () {
		return new MailUtil();
	}
	
	public void sendFlowChangeEvent (String to) {
		String subject = "待簽核通知";
		String text = "簽核簽核簽核簽核簽核簽核";

		MailUtil mail = getMailObj();
		mail.setFrom(FROM);
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(text);
		send(mail);
	}

	public void sendCloseFlowEvent (String to) {
		String subject = "請假申請成功通知";
		String text = "申請成功申請成功申請成功申請成功申請成功";

		MailUtil mail = getMailObj();
		mail.setFrom(FROM);
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(text);
		send(mail);
	}

	public void send (MailUtil mail) {
		sender.send(mail);
	}

	public void setSender (MailSender sender) {
		this.sender = sender;
	}

}
