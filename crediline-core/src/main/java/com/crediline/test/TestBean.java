package com.crediline.test;

import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by dimer on 1/11/14.
 */
@Named
@Scope("session")
public class TestBean {

    public String printHello() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("crediline");
        EntityManager em = emf.createEntityManager();
        return "Hello Dimer!";
    }

}
