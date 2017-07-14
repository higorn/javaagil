package ita.coursera.javaagil4.backend.api.service;/*
 * File:   AccountService.java
 *
 * Created on 24/06/17, 21:02
 */

import ita.coursera.javaagil4.backend.api.model.Account;

import javax.security.auth.login.AccountException;

public interface AccountService {
    Account getAccount(String id);
    Account login(String authorization) throws AccountException;
    Account createAccount(Account accountExpected);
    Account updateAccount(Account account);
    void remove(String accountId);
    void logout(String token);
}
