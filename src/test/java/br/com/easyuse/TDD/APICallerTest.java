package br.com.easyuse.TDD;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import br.com.easyuse.TDD.service.APICaller;
import br.com.easyuse.TDD.service.http.HttpClient;
import br.com.easyuse.TDD.service.http.HttpResponse;

public class APICallerTest {

	private static final HttpResponse HTTPRESPONSE_EXPIRED = new HttpResponse("Expired", 419);
	private static final HttpResponse HTTPRESPONSE_OK = new HttpResponse("OK", 200);
	private static final String URL_HTTP_API_DUMMY = "http://api.com/dummy";
	private static final String URL_HTTP_API_LOGINREFRESH = "http://api.com/loginRefresh";
	
	@Rule public MockitoRule mockito = MockitoJUnit.rule();

	@Mock HttpClient httpClient;
	
	APICaller apiCaller;
	
	@Before
	public void setup() {
		this.apiCaller = new APICaller(httpClient, URL_HTTP_API_LOGINREFRESH);
	}
	
	@Test
	public void when_response_is_419_then_refresh_authentication_and_retry_with_success() {
		
		// Arrange
		when(httpClient.get(URL_HTTP_API_DUMMY)).thenReturn(HTTPRESPONSE_EXPIRED, HTTPRESPONSE_OK);
		when(httpClient.get(URL_HTTP_API_LOGINREFRESH)).thenReturn(HTTPRESPONSE_OK);
		
		// Act
		HttpResponse res = apiCaller.get(URL_HTTP_API_DUMMY);
		
		// Assert
		assertThat("Status code", res.statusCode, equalTo(200));
		InOrder inOrder = Mockito.inOrder(httpClient);
		inOrder.verify(httpClient).get(URL_HTTP_API_DUMMY);
		inOrder.verify(httpClient).get(URL_HTTP_API_LOGINREFRESH);
		inOrder.verify(httpClient).get(URL_HTTP_API_DUMMY);
	}
	
	@Test
	public void when_response_is_200_then_return_ok() {

		// Arrange
		when(httpClient.get(URL_HTTP_API_DUMMY)).thenReturn(HTTPRESPONSE_OK);
		
		// Act
		HttpResponse res = apiCaller.get(URL_HTTP_API_DUMMY);
		
		// Verify
		assertThat("Status code", res.statusCode, equalTo(200));
		InOrder inOrder = Mockito.inOrder(httpClient);
		inOrder.verify(httpClient).get(URL_HTTP_API_DUMMY);
		inOrder.verifyNoMoreInteractions();
	}

}
