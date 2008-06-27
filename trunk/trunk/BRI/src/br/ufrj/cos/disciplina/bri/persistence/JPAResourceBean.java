package br.ufrj.cos.disciplina.bri.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

 public class JPAResourceBean {

    protected EntityManagerFactory emf;

    /*
     * Lazily acquire the EntityManagerFactory and cache it.
     */
     public EntityManagerFactory getEMF (String persistenceUnit){
        if (emf == null){
            emf = Persistence.createEntityManagerFactory(persistenceUnit, new java.util.HashMap());
        }
        return emf;
        
    }
}
