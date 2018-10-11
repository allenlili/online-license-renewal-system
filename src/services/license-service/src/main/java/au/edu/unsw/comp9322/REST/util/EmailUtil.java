package au.edu.unsw.comp9322.REST.util;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import au.edu.unsw.comp9322.REST.constant.Constant;

@Service
public class EmailUtil {

	public void sendEmail(String emailAddress, String eamilSubjec, String emailBody) {

		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", Constant.EMAIL_USER);
		props.put("mail.smtp.password", Constant.EMAIL_PASSWORD);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Constant.EMAIL_USER, Constant.EMAIL_PASSWORD);
			}
		});

		try {

			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(Constant.EMAIL_USER));
			message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(emailAddress));
			message.setSubject(eamilSubjec);
			message.setText(emailBody);

			Transport.send(message);

			System.out.println("succeed to send email to " + emailAddress);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
