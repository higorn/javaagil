package ita.coursera.javaagil4.backend.api.service;/*
 * File:   AccountService.java
 *
 * Created on 24/06/17, 21:02
 */

import ita.coursera.javaagil4.backend.api.model.Account;

public interface AccountService {
    Account getAccount(final String user, final String pass);
}
