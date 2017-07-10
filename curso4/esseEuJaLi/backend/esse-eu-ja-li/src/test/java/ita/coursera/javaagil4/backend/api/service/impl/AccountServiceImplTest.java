package ita.coursera.javaagil4.backend.api.service.impl;

/*
 * Created on 25/06/17, 11:46
 */

import ita.coursera.javaagil4.backend.api.dao.AccountDao;
import ita.coursera.javaagil4.backend.api.model.Account;
import ita.coursera.javaagil4.backend.api.security.HashUtils;
import org.jboss.resteasy.util.Base64;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.security.auth.login.AccountException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author higor
 */
public class AccountServiceImplTest {
    @Mock
    private AccountDao dao;
    @Spy
    @InjectMocks
    private AccountServiceImpl service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void paraLoginValido1DeveRetornarUmaContaValida() throws AccountException {
        Account accountExpected = new Account();
        accountExpected.setName("nicanor");
        accountExpected.setDisplayName("Nicanor");
        accountExpected.setPassword(HashUtils.hashPassword(accountExpected.getName(), "abc"));
        accountExpected.setToken("abc");

        when(dao.findByName("nicanor")).thenReturn(accountExpected);
        when(dao.update(Matchers.any(Account.class))).thenReturn(accountExpected);

        String basicAuthEncoded = Base64.encodeBytes("nicanor:abc".getBytes());
        String authorization = "Basic " + basicAuthEncoded;

        Account account = service.login(authorization);
        assertNotNull(account);
        assertEquals(accountExpected.getName(), account.getName());
        assertEquals(accountExpected.getDisplayName(), account.getDisplayName());
        assertNotNull(account.getToken());
        assertNull(account.getPassword());
        verify(dao).findByName("nicanor");
        verify(dao).update(account);
    }

    @Test
    public void paraLoginValido2DeveRetornarUmaContaValida() throws AccountException {
        Account accountExpected = new Account();
        accountExpected.setName("lauterio");
        accountExpected.setDisplayName("Lauterio");
        accountExpected.setPassword(HashUtils.hashPassword(accountExpected.getName(), "cba"));
        accountExpected.setToken("cba");

        when(dao.findByName("lauterio")).thenReturn(accountExpected);
        when(dao.update(Matchers.any(Account.class))).thenReturn(accountExpected);

        String basicAuthEncoded = Base64.encodeBytes("lauterio:cba".getBytes());
        String authorization = "Basic " + basicAuthEncoded;

        Account account = service.login(authorization);
        assertNotNull(account);
        assertEquals(accountExpected.getName(), account.getName());
        assertEquals(accountExpected.getDisplayName(), account.getDisplayName());
        assertNotNull(account.getToken());
        assertNull(account.getPassword());
        verify(dao).findByName("lauterio");
        verify(dao).update(account);
    }

    @Test
    public void paraLogin1ComSenhaInvalidaDeveRetornarErro() {
        Account accountExpected = new Account();
        accountExpected.setName("nicanor");
        accountExpected.setDisplayName("Nicanor");
        accountExpected.setPassword(HashUtils.hashPassword(accountExpected.getName(), "abc"));

        when(dao.findByName("nicanor")).thenReturn(accountExpected);

        String basicAuthEncoded = Base64.encodeBytes("nicanor:1234".getBytes());
        String authorization = "Basic " + basicAuthEncoded;

        try {
            service.login(authorization);
            fail();
        } catch (AccountException e) {
            assertEquals("Login inválido", e.getMessage());
        }
    }

    @Test
    public void deveCriarUmaNovaConta() {
        Account accountExpected = new Account();
        accountExpected.setName("lauterio");
        accountExpected.setDisplayName("Lauterio");
        accountExpected.setPassword("cba");

        doAnswer(invocation -> {
            Account account = invocation.getArgumentAt(0, Account.class);
            account.setId("123");
            return account;
        }).when(dao).create(accountExpected);

        Account account = service.createAccount(accountExpected);
        verify(dao).create(accountExpected);
        assertNotNull(account);
        assertNotNull(account.getId());
        assertEquals(HashUtils.hashPassword(accountExpected.getName(), "cba"), account.getPassword());
    }

    @Test
    public void deveAtualizarUmaConta() throws AccountException {
        Account accountExpected = new Account();
        accountExpected.setName("lauterio");
        accountExpected.setDisplayName("Lauterio");
        accountExpected.setToken("cba");

        when(dao.findById("123")).thenReturn(accountExpected);

        Account account = service.getAccount("123");
        assertNotNull(account);
        assertEquals(accountExpected.getName(), account.getName());
        assertEquals(accountExpected.getDisplayName(), account.getDisplayName());
        assertEquals(accountExpected.getToken(), account.getToken());
        verify(dao).findById("123");

        accountExpected.setPassword("def");
        when(dao.update(accountExpected)).thenReturn(accountExpected);

        Account accountUpdated = service.updateAccount(accountExpected);
        assertEquals(accountExpected.getPassword(), accountUpdated.getPassword());
        verify(dao).update(accountExpected);
    }
}