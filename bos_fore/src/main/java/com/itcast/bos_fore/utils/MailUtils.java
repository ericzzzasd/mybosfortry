package com.itcast.bos_fore.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {
	private static String smtp_host = "smtp.163.com";
	private static String username = "li_sunyou@163.com";
	private static String password = "lsy1505759";
	private static String from = "li_sunyou@163.com";
	private static String activeUrl = "http://localhost:9321/bos_fore/doActive.action";

	public static void sendMail(String subject, String telephone, String to,
			String activecode) {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host",smtp_host);
		props.setProperty("mail.transport.protocol","smtp");
		props.setProperty("mail.smtp.auth","true");
		Session session = Session.getInstance(props);
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));
			message.setRecipient(RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setContent("<h3>速运快递,邮箱激活,尊敬的用户您好！,请点击地址激活:<a href=" + activeUrl
					+ "?activecode=" + activecode + "&telephone="+telephone+">" + activeUrl
					+ "</a></h3>", "text/html;charset=utf-8");
			Transport transport = session.getTransport();
			transport.connect(smtp_host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("邮件发送失败...");
		}
	}

	
}
