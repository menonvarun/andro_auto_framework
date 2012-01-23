/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imaginea.botbot.server.service;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityTransaction;

/**
 * Utility class for dealing with persistence.
 *
 * @author rohit
 */
public class PersistenceService {

    private static String DEFAULT_PU = "botbot-server";
    private static EntityManagerFactory pmf;
    
    static {
        try {
            pmf = (EntityManagerFactory) new InitialContext().lookup("java:comp/env/persistence/" + DEFAULT_PU);
        } catch (NamingException ex) {
            pmf = Persistence.createEntityManagerFactory(DEFAULT_PU);
        }        
    }
    private static ThreadLocal<PersistenceService> instance = new ThreadLocal<PersistenceService>() {

        @Override
        protected PersistenceService initialValue() {
            return new PersistenceService();
        }
    };
    private EntityManager em;
    private EntityTransaction utx;
    
    private PersistenceService() {        
        this.em = pmf.createEntityManager();
        this.utx = em.getTransaction();
    }

    /**
     * Returns an instance of PersistenceService.
     * 
     * @return an instance of PersistenceService
     */
    public static PersistenceService getInstance() {
        return instance.get();
    }
    
    private static void removeInstance() {
        instance.remove();
    }

    /**
     * Returns an instance of EntityManager.
     *
     * @return an instance of EntityManager
     */
    public EntityManager getEntityManager() {
        return em;
    }

    /**
     * Begins a resource transaction.
     */
    public void beginTx() {
        try {
            utx.begin();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Commits a resource transaction.
     */
    public void commitTx() {
        try {
            utx.commit();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Rolls back a resource transaction.
     */
    public void rollbackTx() {
        try {
            utx.rollback();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Closes this instance.
     */
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        
        removeInstance();
    }
}
