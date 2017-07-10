package ita.coursera.javaagil4.backend.api.dao.impl;

import ita.coursera.javaagil4.backend.api.WeldJUnit4Runner;
import ita.coursera.javaagil4.backend.api.model.Account;
import ita.coursera.javaagil4.backend.api.security.HashUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/*
 * File:   AccountDaoImplTest.java
 *
 * Created on 25/06/17, 15:09
 */

/**
 * @author higor
 */
@RunWith(WeldJUnit4Runner.class)
public class AccountDaoImplIT {

    @Inject
    private AccountDaoImpl dao;
    private Account account;
	private String token;

    @Before
    public void setUp() throws NotSupportedException, SystemException, NoSuchAlgorithmException {
        MockitoAnnotations.initMocks(this);

        token = HashUtils.generateToken();
        account = new Account();
        account.setName("nicanor");
        account.setDisplayName("Nicanor");
        account.setRole("user");
        account.setPassword("abc");
        account.setToken(token);
        dao.create(account);
    }
    
    @After
    public void tearDown() throws SecurityException, IllegalStateException,
    	RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
        dao.remove(account);
    }

    @Test
    public void deveEncontrarContaPeloNome() {
        Account account = dao.findByName("nicanor");
        assertNotNull(account);
        assertNotNull(account.getId());
        assertEquals("nicanor", account.getName());
        assertEquals("Nicanor", account.getDisplayName());
        assertEquals("user", account.getRole());
        assertEquals("abc", account.getPassword());
        assertEquals(this.account.getToken(), account.getToken());
    }

    @Test
    public void deveEncontrarContaPeloId() {
        Account account = dao.findById(this.account.getId());
        assertNotNull(account);
        assertEquals(this.account.getId(), account.getId());
    }

    @Test
    public void deveEncontrarContaPeloToken() {
        Optional<Account> maybeAccount = dao.findByToken(token);
        assertTrue(maybeAccount.isPresent());
        assertEquals(this.account.getId(), maybeAccount.get().getId());
        assertEquals(this.account.getToken(), maybeAccount.get().getToken());
    }

    @Test
    public void quandoTokenNaoEncontradoDeveRetornarContaVazia() {
        Optional<Account> maybeAccount = dao.findByToken("invalido");
        assertFalse(maybeAccount.isPresent());
    }

    @Test
    public void deveAtualizarUmaConta() {
        Account account = dao.findByName("nicanor");
        assertNotNull(account);
        assertNotNull(account.getId());
        assertEquals("nicanor", account.getName());
        assertEquals("Nicanor", account.getDisplayName());
        assertEquals("user", account.getRole());
        assertEquals("abc", account.getPassword());
        assertEquals(this.account.getToken(), account.getToken());

        account.setPassword("def");
        Account accountUpdated = dao.update(account);
        assertNotNull(accountUpdated);
        assertEquals(account.getPassword(), accountUpdated.getPassword());
    }
}