package ita.coursera.javaagil4.backend.api.dao;/*
 * File:   AccountDao.java
 *
 * Created on 25/06/17, 14:18
 */

import ita.coursera.javaagil4.backend.api.model.Account;

public interface AccountDao {
    Account findByName(String name);
    void create(Account account);
    void remove(Account account);
}
