package ita.coursera.javaagil4.backend.api.dao;/*
 * File:   AccountDao.java
 *
 * Created on 25/06/17, 14:18
 */

import java.util.Optional;

import ita.coursera.javaagil4.backend.api.model.Account;

public interface AccountDao {
    Account findById(String id);
    Account findByName(String name);
	Optional<Account> findByToken(String token);
    Account create(Account account);
    void remove(Account account);
    Account update(Account account);
}
