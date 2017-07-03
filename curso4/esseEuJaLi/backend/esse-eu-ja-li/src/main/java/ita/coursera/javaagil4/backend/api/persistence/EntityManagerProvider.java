/*
 * File:   EntityManagerProvider.java
 *
 * Created on 30/06/17, 22:07
 */
package ita.coursera.javaagil4.backend.api.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author higor
 */
@Default
@ApplicationScoped
public class EntityManagerProvider {
    @PersistenceContext(unitName = "esseEuJaLiPU")
    private EntityManager entityManager;

    @Produces
    @RequestScoped
    @DataSourceQualifier
    public EntityManager getEntityManager() {
        return this.entityManager;
    }
}
