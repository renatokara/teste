package br.com.easyuse.TDD.mail;

public interface MailServer {
	public void send(String email, String name, String subject, String body) throws Exception;
}
