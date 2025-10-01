package gm.utils.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailTeste {

	public static void main(String[] args) throws MessagingException {

		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("noreply.cooperforte@cf.coop.br", null);
			}
		};

		Properties props = new Properties();

		props.put("mail.smtps.host", "smtp.cooperforte.coop");
		props.put("mail.smtp.host", "smtp.cooperforte.coop");
		props.put("mail.smtp.auth", "false");
		props.put("mail.smtp.port", "25");
		props.put("mail.smtps.auth", "true");
		props.put("mail.smtps.quitwait", "false");

		Session session = Session.getInstance(props, auth);
		MimeMessage message = new MimeMessage(session);
		message.setSubject("assunto - teste");
		message.setFrom(new InternetAddress("Cooperforte"));

		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText("texto - teste");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		InternetAddress[] destinatarios = InternetAddress.parse("gamarra@cf.coop.br");

		message.setRecipients(Message.RecipientType.TO, destinatarios);
		message.setContent(multipart);
		Transport.send(message);

	}

}
