package ita.coursera.javaagil4.backend.api;

import ita.coursera.javaagil4.backend.api.model.Account;
import ita.coursera.javaagil4.backend.api.service.AccountService;
import org.jboss.resteasy.util.Base64;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.security.auth.login.AccountException;
import javax.ws.rs.core.MultivaluedHashMap;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountRestServiceTest {
	@Mock
    private AccountService accountService;
	@Spy
	@InjectMocks
	private AccountRestService restService;
	private MultivaluedHashMap<String, String> headersMap;

	@Before
    public void setUp() {
	    MockitoAnnotations.initMocks(this);
		headersMap = new MultivaluedHashMap<String, String>();
		headersMap.put("Content-Type", Arrays.asList(new String[] {"application/json"}));
	}

	@Test
	public void paraUsuarioValido1DeveRetornarUmaContaValida() throws AccountException {
		String basicAuthEncoded = Base64.encodeBytes("nicanor:abc".getBytes());
		headersMap.put("Authorization", Arrays.asList(new String[] {"Basic " + basicAuthEncoded}));

        Account accountExpected = new Account();
        accountExpected.setName("nicanor");
        accountExpected.setDisplayName("Nicanor");
        accountExpected.setToken("abc");

        when(accountService.getAccount("nicanor", "abc")).thenReturn(accountExpected);

		Account account = restService.getAccount("Basic " + basicAuthEncoded);
		assertNotNull(account);
		assertEquals("nicanor", account.getName());
		assertEquals("Nicanor", account.getDisplayName());
		assertEquals("abc", account.getToken());
		verify(accountService).getAccount("nicanor", "abc");
	}

	@Test
	public void paraUsuarioValido2DeveRetornarUmaContaValida() throws AccountException {
		String basicAuthEncoded = Base64.encodeBytes("lauterio:cba".getBytes());
		headersMap.put("Authorization", Arrays.asList(new String[] {"Basic " + basicAuthEncoded}));

		Account accountExpected = new Account();
		accountExpected.setName("lauterio");
		accountExpected.setDisplayName("Lauterio");
		accountExpected.setToken("cba");

		when(accountService.getAccount("lauterio", "cba")).thenReturn(accountExpected);

		Account account = restService.getAccount("Basic " + basicAuthEncoded);
		assertNotNull(account);
		assertEquals("lauterio", account.getName());
		assertEquals("Lauterio", account.getDisplayName());
		assertEquals("cba", account.getToken());
		verify(accountService).getAccount("lauterio", "cba");
	}
}
