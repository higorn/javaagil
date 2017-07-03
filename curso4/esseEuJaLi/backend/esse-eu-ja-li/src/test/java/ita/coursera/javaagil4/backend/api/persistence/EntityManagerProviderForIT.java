/*
 * File:   EntityManagerProvider.java
 *
 * Created on 01/07/17, 22:42
 */
package ita.coursera.javaagil4.backend.api.persistence;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.interceptor.Interceptor;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * @author higor
 */
@Alternative
@Priority(Interceptor.Priority.APPLICATION + 100)
@ApplicationScoped
public class EntityManagerProviderForIT {

    @Produces
    @DataSourceQualifier
    public EntityManager getEntityManager() {
        return Persistence.createEntityManagerFactory("db-test").createEntityManager();
    }
}
