package br.com.easyuse.TDD;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.net.ConnectException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.easyuse.TDD.entities.Destinatario;
import br.com.easyuse.TDD.mail.MailSender;
import br.com.easyuse.TDD.mail.MailServer;

@RunWith(MockitoJUnitRunner.class)
public class MailSenderTest {
	
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
	@Mock
	MailServer mailServer;
	
	List<Destinatario> destinatarios = Arrays.asList(new Destinatario("David Rissato Cruz", "davidrc@gmail.com"), new Destinatario("Renato Cara", "rntkara@gmail.com"));

	MailSender mailSender;
	
	@Before
	public void before() {
		mailSender = new MailSender(mailServer);
	}

	@Test
	public void whenSendingEmailToUsers_thenSuccess() throws Exception {
		
		// Act
		String subject = "Não se esqueça do tdd";
		String bodyTemplate = "Olá %destinatario%, não se esqueça do TDD";
		this.mailSender.sendEmail(destinatarios, subject, bodyTemplate);
		
		// Assert 
		verify(this.mailServer).send("davidrc@gmail.com","David Rissato Cruz", subject, "Olá David, não se esqueça do TDD");
		verify(this.mailServer).send("rntkara@gmail.com", "Renato Cara", subject, "Olá Renato, não se esqueça do TDD");
	}
	
	@Test
	public void givenServerOffline_whenSendingEmailToUsers_thenError() throws Exception {
		// Arrange
		doThrow(new Exception()).when(mailServer).send(any(), any(), any(), any());
		thrown.expect(ConnectException.class);
		thrown.expectMessage("Erro ao conectar no mail sender.");
		
		// Act
		String subject = "Não se esqueça do tdd";
		String bodyTemplate = "Olá %destinatario%, não se esqueça do TDD";		
		this.mailSender.sendEmail(destinatarios, subject, bodyTemplate);
	}
	

}
