package br.com.easyuse.TDD.mail;

import java.net.ConnectException;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.easyuse.TDD.entities.Destinatario;

public class MailSender {

	private MailServer mailServer;

	@Autowired
	public MailSender(MailServer mailServer) {
		this.mailServer = mailServer;
	}

	public interface ConsumerWithCheckedException<T> {
		void accept(T obj) throws Exception;
	}

	public static <T> Consumer<T> convertExceptionToRunTime(ConsumerWithCheckedException<T> consumerWithException) {
		return new Consumer<T>() {
			@Override
			public void accept(T t) {
				try {
					consumerWithException.accept(t);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
	}

	public void sendEmail(List<Destinatario> destinatarios, String subject, String bodyTemplate)
			throws ConnectException {

		try {
			destinatarios.stream().forEach(convertExceptionToRunTime((destinatario) -> {
				String primeiro = destinatario.nome.replaceAll("^([^ ]*).*$", "$1");
				final String body = bodyTemplate.replaceAll("\\%destinatario\\%", primeiro);
				mailServer.send(destinatario.email, destinatario.nome, subject, body);
			}));
		} catch (RuntimeException e) {
			throw new ConnectException("Erro ao conectar no mail sender.");
		}
	}
	
}
