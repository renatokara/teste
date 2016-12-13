package br.com.easyuse.TDD.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.easyuse.TDD.service.http.HttpClient;
import br.com.easyuse.TDD.service.http.HttpResponse;

public class APICaller {

	private HttpClient httpClient;
	private String loginRefreshUrl;
	
	@Autowired
	public APICaller(HttpClient httpClient, String loginRefreshUrl) {
		this.httpClient = httpClient;
		this.loginRefreshUrl = loginRefreshUrl;
	}
	public HttpResponse get(String url) {
		HttpResponse response = httpClient.get(url);
		if (response.statusCode==419) {
			HttpResponse responseLogin = httpClient.get(loginRefreshUrl);
			if (responseLogin.statusCode == 200) {
				response = httpClient.get(url);
			}
		}
		return response;
	}

}
