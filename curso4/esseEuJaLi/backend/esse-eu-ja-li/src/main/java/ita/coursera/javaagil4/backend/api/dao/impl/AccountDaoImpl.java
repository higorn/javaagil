/*
 * File:   AccountDaoImpl.java
 *
 * Created on 25/06/17, 14:36
 */
package ita.coursera.javaagil4.backend.api.dao.impl;

import ita.coursera.javaagil4.backend.api.dao.AccountDao;
import ita.coursera.javaagil4.backend.api.model.Account;
import ita.coursera.javaagil4.backend.api.persistence.DataSourceQualifier;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.TransactionManager;
import javax.ws.rs.WebApplicationException;

/**
 * @author higor
 */
public class AccountDaoImpl implements AccountDao {
    @Inject
    @DataSourceQualifier
    private EntityManager em;
    @Inject
    private TransactionManager tm;

    @Override
    public Account findById(String id) {
        return em.find(Account.class, id);
    }

    public Account findByName(String name) {
        final TypedQuery<Account> query = em.createQuery("SELECT a FROM Account a WHERE a.name = :name", Account.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

	public Account create(Account account) {
        try {
            tm.begin();
            em.persist(account);
            tm.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(e);
        }
		return account;
	}

    public void remove(Account account) {
        try {
            tm.begin();
            em.remove(account);
            tm.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(e);
        }
    }

    @Override
    public Account update(Account account) {
        Account accountUpdated = findById(account.getId());
        accountUpdated.setName(account.getName());
        accountUpdated.setDisplayName(account.getDisplayName());
        accountUpdated.setRole(account.getRole());
        if (account.getPassword() != null && !account.getPassword().isEmpty()) {
            accountUpdated.setPassword(account.getPassword());
        }

        try {
            tm.begin();
            accountUpdated = em.merge(accountUpdated);
            tm.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(e);
        }
        return accountUpdated;
    }
}
