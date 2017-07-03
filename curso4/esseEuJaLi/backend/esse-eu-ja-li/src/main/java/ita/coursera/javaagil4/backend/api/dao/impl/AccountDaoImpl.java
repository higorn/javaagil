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

/**
 * @author higor
 */
public class AccountDaoImpl implements AccountDao {
    @Inject
    @DataSourceQualifier
    private EntityManager em;

    public void setEntityManager(final EntityManager em) {
        this.em = em;
    }

    public Account findByName(String name) {
        final TypedQuery<Account> query = em.createQuery("SELECT a FROM Account a WHERE a.name = :name", Account.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

	public void create(Account account) {
		em.persist(account);
	}

    public void remove(Account account) {
        em.remove(account);
    }
}
