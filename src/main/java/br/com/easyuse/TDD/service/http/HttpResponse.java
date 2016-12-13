package br.com.easyuse.TDD.service.http;

public class HttpResponse {
	public final String body;
	public final Integer statusCode;
	
	public HttpResponse(String body, Integer statusCode) {
		super();
		this.body = body;
		this.statusCode = statusCode;
	}
	
}
