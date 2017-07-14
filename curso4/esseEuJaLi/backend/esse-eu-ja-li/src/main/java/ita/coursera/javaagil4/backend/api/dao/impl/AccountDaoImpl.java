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
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author higor
 */
public class AccountDaoImpl implements AccountDao {
    private static final Logger logger = Logger.getLogger(AccountDaoImpl.class.getName());

    @Inject
    @DataSourceQualifier
    private EntityManager em;

    @Override
    public Account findById(String id) {
        return Optional.ofNullable(em.find(Account.class, id))
                .orElseThrow(() -> new  NoResultException("Not found"));
    }

    public Account findByName(String name) {
        final TypedQuery<Account> query = em.createQuery("SELECT a FROM Account a WHERE a.name = :name", Account.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

	@Override
	public Optional<Account> findByToken(String token) {
        final TypedQuery<Account> query = em.createQuery("SELECT a FROM Account a WHERE a.token = :token", Account.class);
        query.setParameter("token", token);
        try {
			return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
        	return Optional.empty();
		}
	}

	public Account create(Account account) {
        try {
        	EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(account);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(e);
        }
		return account;
	}

    public void remove(Account account) {
        try {
        	EntityTransaction tx = em.getTransaction();
			tx.begin();
            em.remove(account);
            tx.commit();
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
        	EntityTransaction tx = em.getTransaction();
			tx.begin();
            accountUpdated = em.merge(accountUpdated);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(e);
        }
        return accountUpdated;
    }
}
