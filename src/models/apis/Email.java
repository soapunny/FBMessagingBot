package models.apis;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.EnvHelper;

public class Email {
	public static Logger logger = LogManager.getLogger(Email.class);
	private static final String GMAIL_EMAIL = EnvHelper.getInstance().getValue("GMAIL_EMAIL");
	private static final String GMAIL_PASSWORD = EnvHelper.getInstance().getValue("GMAIL_PASSWORD");

	public static void send(String title, String sendTo, String msg) {
		// Recipient's email ID needs to be mentioned.
		String to = sendTo;
		
		// Sender's email ID needs to be mentioned.
        String from = GMAIL_EMAIL;
		
		// Assuming you are sending email from through gmails smtp
		String host = "smtp.gmail.com";
		
		// Get system properties
		Properties properties = System.getProperties();
		
		// Setup email server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(GMAIL_EMAIL, GMAIL_PASSWORD);
			}
		});
		
		//Used to debug SMTP issues
		session.setDebug(true);
		
		
		try {
			//Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			
			//Set From: header field of the header.
			message.setFrom(new InternetAddress(from));
			
			//Set To : header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			//Set subject: header field
			message.setSubject(title);
			
			//Set actual message
			message.setContent(msg, "text/html");
			
			//Send message
			Transport.send(message);
			logger.info("Sent an email to "+sendTo);
			
		} catch (MessagingException e) {
			logger.error(e.getMessage());
		}
	}
}
