/*
 * File:   AccountServiceImpl.java
 *
 * Created on 24/06/17, 21:28
 */
package ita.coursera.javaagil4.backend.api.service.impl;

import ita.coursera.javaagil4.backend.api.dao.AccountDao;
import ita.coursera.javaagil4.backend.api.model.Account;
import ita.coursera.javaagil4.backend.api.security.HashUtils;
import ita.coursera.javaagil4.backend.api.service.AccountService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.security.auth.login.AccountException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author higor
 */
@Stateless
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = Logger.getLogger(AccountServiceImpl.class.getName());

    @Inject
    private AccountDao dao;

    public Account getAccount(String id) {
        Account account = dao.findById(id);
        account.setPassword(null);
        return account;
    }

    @Override
    public Account login(String authorization) throws AccountException {
        String[] info = getAuthInfo(authorization);
        String usuario = info[0];
        String senha = info[1];
        Account account = dao.findByName(usuario);
        validaSenha(usuario, senha, account.getPassword());
        geraToken(account);
        account.setPassword(null);
        return account;
    }

    private void geraToken(final Account account) throws AccountException {
        try {
            account.setToken(HashUtils.generateToken());
            dao.update(account);
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new AccountException(e.getMessage());
        }
    }

    @Override
    public Account createAccount(Account account) {
        account.setPassword(HashUtils.hashPassword(account.getName(), account.getPassword()));
        return dao.create(account);
    }

    @Override
    public Account updateAccount(Account account) {
        if (account.getPassword() != null && !account.getPassword().isEmpty()) {
            account.setPassword(HashUtils.hashPassword(account.getName(), account.getPassword()));
        }
        Account accountUpdated = dao.update(account);
        accountUpdated.setPassword(null);
        return accountUpdated;
    }

    @Override
    public void remove(final String accountId) {
        dao.remove(dao.findById(accountId));
    }

    @Override
    public void logout(String token) {
        Account account = dao.findByToken(token).orElseThrow(() -> new NoResultException("Not found"));
        account.setToken(null);
        dao.update(account);
    }

    private void validaSenha(final String usuario, final String senhaInformada, final String senhaEsperada) throws AccountException {
        if (senhaEsperada == null) {
            logger.warning("Usuário '" + usuario + "' sem senha.");
            throw new AccountException("Login inválido");
        }
        if (senhaEsperada.equals(HashUtils.hashPassword(usuario, senhaInformada))) {
           return;
        }

        logger.warning("Senha informada '" + senhaInformada + "' inválida para usuário '" + usuario + "'");
        throw new AccountException("Login inválido");
    }

    private String[] getAuthInfo(String authorization) {
        String basicAuthEncoded = authorization.split(" ")[1];
        String basicAuthDecoded = new String(Base64.getDecoder().decode(basicAuthEncoded));
        return basicAuthDecoded.split(":");
    }

}
