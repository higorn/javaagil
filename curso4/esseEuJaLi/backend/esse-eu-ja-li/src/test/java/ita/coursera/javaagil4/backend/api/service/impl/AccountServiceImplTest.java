package ita.coursera.javaagil4.backend.api.service.impl;

/*
 * Created on 25/06/17, 11:46
 */

import ita.coursera.javaagil4.backend.api.dao.AccountDao;
import ita.coursera.javaagil4.backend.api.model.Account;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
    public void paraLoginValido1DeveRetornarUmaContaValida() {
        Account accountExpected = new Account();
        accountExpected.setName("nicanor");
        accountExpected.setDisplayName("Nicanor");
        accountExpected.setToken("abc");

        when(dao.findByName("nicanor")).thenReturn(accountExpected);

        Account account = service.getAccount("nicanor", "abc");
        assertNotNull(account);
        assertEquals("nicanor", account.getName());
        assertEquals("Nicanor", account.getDisplayName());
        assertEquals("abc", account.getToken());
        verify(dao).findByName("nicanor");
    }

    @Test
    public void paraLoginValido2DeveRetornarUmaContaValida() {
        Account accountExpected = new Account();
        accountExpected.setName("lauterio");
        accountExpected.setDisplayName("Lauterio");
        accountExpected.setToken("cba");

        when(dao.findByName("lauterio")).thenReturn(accountExpected);

        Account account = service.getAccount("lauterio", "cba");
        assertNotNull(account);
        assertEquals("lauterio", account.getName());
        assertEquals("Lauterio", account.getDisplayName());
        assertEquals("cba", account.getToken());
    }
}