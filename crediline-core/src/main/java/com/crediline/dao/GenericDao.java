package com.crediline.dao;

import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

@Component
public abstract class GenericDao<T, K> implements IGenericDao<T, K> {

    public static Logger logger = Logger.getLogger(GenericDao.class.getName());

    @Inject
    public static EntityManagerFactory emf;

    private EntityManager em;

    private Class<T> entityClass;

    /*@Inject
    private SessionFactory sessionFactory;*/

    protected EntityManager getEntityManager() {
        return this.em;
    }

    public GenericDao() {
    }

    public GenericDao(Class<T> entityClass) {
        this.entityClass = entityClass;
        logger = Logger.getLogger(entityClass.getName());
    }

    @Override
    public void beginTransaction() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    @Override
    public void commit() {
        em.getTransaction().commit();
    }

    @Override
    public void rollback() {
        em.getTransaction().rollback();
    }

    @Override
    public void closeTransaction() {
        if (em.getTransaction().isActive()) {
            em.close();
        }
    }

    @Override
    public void commitAndCloseTransaction() {
        commit();
        closeTransaction();
    }

    @Override
    public void flush() {
        em.flush();
    }

    @Override
    public void joinTransaction() {
        em = emf.createEntityManager();
        em.joinTransaction();
    }


    @Override
    public void save(T entity) {
        if (em.contains(entity)) {
            em.merge(entity);
        } else {
            em.persist(entity);
        }
    }

    @Override
    public void delete(T entity) {
        T entityToBeRemoved = em.merge(entity);
        em.remove(entityToBeRemoved);
    }

    @Override
    public T update(T entity) {
        return em.merge(entity);
    }

    @Override
    public T find(K entityID) {
        return em.find(entityClass, entityID);
    }

    @Override
    public T findReferenceOnly(K entityID) {
        return em.getReference(entityClass, entityID);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<T> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }


    @SuppressWarnings("unchecked")
    protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
        T result = null;

        try {
            Query query = em.createNamedQuery(namedQuery);

            // Method that will populate parameters if they are passed not null and empty
            if (parameters != null && !parameters.isEmpty()) {
                populateQueryParameters(query, parameters);
            }

            result = (T) query.getSingleResult();

        } catch (NoResultException e) {
            System.out.println("No result found for named query: " + namedQuery);
        } catch (Exception e) {
            System.out.println("Error while running query: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    private void populateQueryParameters(Query query, Map<String, Object> parameters) {
        for (Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return emf.getCriteriaBuilder();
    }

    public List findByCriteria(CriteriaQuery<T> criteriaQuery) {
        return em.createQuery(criteriaQuery).getResultList();
    }

    public List findByCriteria(CriteriaQuery<T> criteriaQuery, int maxResult) {
        return em.createQuery(criteriaQuery).setMaxResults(maxResult).getResultList();
    }

    /*public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }*/
}