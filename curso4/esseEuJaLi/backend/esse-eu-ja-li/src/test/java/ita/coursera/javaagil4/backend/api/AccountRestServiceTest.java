package ita.coursera.javaagil4.backend.api;

import ita.coursera.javaagil4.backend.api.model.Account;
import ita.coursera.javaagil4.backend.api.model.ApiResponse;
import ita.coursera.javaagil4.backend.api.rest.AccountRestService;
import ita.coursera.javaagil4.backend.api.service.AccountService;
import org.jboss.resteasy.util.Base64;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.security.auth.login.AccountException;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountRestServiceTest {
	@Mock
    private AccountService accountService;
	@Spy
	@InjectMocks
	private AccountRestService restService;

	@Before
    public void setUp() {
	    MockitoAnnotations.initMocks(this);
	}

	@Test
	public void paraUsuarioValido1DeveRetornarUmaContaValida() throws AccountException {
		String basicAuthEncoded = Base64.encodeBytes("nicanor:abc".getBytes());
		String authorization = "Basic " + basicAuthEncoded;

        Account accountExpected = new Account();
        accountExpected.setName("nicanor");
        accountExpected.setDisplayName("Nicanor");
        accountExpected.setToken("abc");

        when(accountService.login(authorization)).thenReturn(accountExpected);

		Account account = restService.login(authorization);
		assertNotNull(account);
		assertEquals("nicanor", account.getName());
		assertEquals("Nicanor", account.getDisplayName());
		assertEquals("abc", account.getToken());
		verify(accountService).login(authorization);
	}

	@Test
	public void paraUsuarioValido2DeveRetornarUmaContaValida() throws AccountException {
		String basicAuthEncoded = Base64.encodeBytes("lauterio:cba".getBytes());
		String authorization = "Basic " + basicAuthEncoded;

		Account accountExpected = new Account();
		accountExpected.setName("lauterio");
		accountExpected.setDisplayName("Lauterio");
		accountExpected.setToken("cba");

		when(accountService.login(authorization)).thenReturn(accountExpected);

		Account account = restService.login(authorization);
		assertNotNull(account);
		assertEquals("lauterio", account.getName());
		assertEquals("Lauterio", account.getDisplayName());
		assertEquals("cba", account.getToken());
		verify(accountService).login(authorization);
	}

	@Test
	public void deveCriarUmaNovaConta() {

		Account accountExpected = new Account();
		accountExpected.setName("lauterio");
		accountExpected.setDisplayName("Lauterio");
		accountExpected.setPassword("cba");

		when(accountService.createAccount(accountExpected)).thenReturn(accountExpected);

		Response response = restService.createAccount(accountExpected);
		assertNotNull(response);
		assertEquals(201, response.getStatus());
		assertTrue(response.getEntity() instanceof ApiResponse);
		ApiResponse<String> apiResponse = (ApiResponse<String>) response.getEntity();
		assertEquals(Integer.valueOf(201), apiResponse.getCode());
		assertEquals("Created", apiResponse.getStatus());
		verify(accountService).createAccount(accountExpected);
	}

	@Test
	public void deveAtualizarUmaConta() throws AccountException {
		Account accountExpected = new Account();
		accountExpected.setName("lauterio");
		accountExpected.setDisplayName("Lauterio");
		accountExpected.setToken("cba");

		accountExpected.setPassword("def");
		when(accountService.updateAccount(accountExpected)).thenReturn(accountExpected);

		Account accountUpdated = restService.updateAccount(accountExpected);
		assertEquals(accountExpected.getPassword(), accountUpdated.getPassword());
		verify(accountService).updateAccount(accountExpected);
	}
}
