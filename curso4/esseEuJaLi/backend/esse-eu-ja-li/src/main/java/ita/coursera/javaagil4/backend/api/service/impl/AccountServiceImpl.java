/*
 * File:   AccountServiceImpl.java
 *
 * Created on 24/06/17, 21:28
 */
package ita.coursera.javaagil4.backend.api.service.impl;

import ita.coursera.javaagil4.backend.api.dao.AccountDao;
import ita.coursera.javaagil4.backend.api.model.Account;
import ita.coursera.javaagil4.backend.api.service.AccountService;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author higor
 */
@Stateless
public class AccountServiceImpl implements AccountService {
    @Inject
    private AccountDao dao;

    public Account getAccount(String user, String pass) {
        return dao.findByName(user);
    }
}
