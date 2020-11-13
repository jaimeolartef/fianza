/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fianza.facade;

import com.mycompany.fianza.entidades.Constantes;
import com.mycompany.fianza.facade.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author dianaplata
 */
public class ConstantesJpaController implements Serializable {

    public ConstantesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    
    public Constantes constanteByCodConstante(String codConstante) {
        
        Constantes constante = new Constantes();
        
        try {
            Query rta = getEntityManager().createNamedQuery("Constante.findByCodigo");
            rta.setParameter("codigo", codConstante);
            constante = (Constantes) rta.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        
        return constante;
    }

    public void create(Constantes constantes) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(constantes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Constantes constantes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            constantes = em.merge(constantes);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = constantes.getId();
                if (findConstantes(id) == null) {
                    throw new NonexistentEntityException("The constantes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Constantes constantes;
            try {
                constantes = em.getReference(Constantes.class, id);
                constantes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The constantes with id " + id + " no longer exists.", enfe);
            }
            em.remove(constantes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Constantes> findConstantesEntities() {
        return findConstantesEntities(true, -1, -1);
    }

    public List<Constantes> findConstantesEntities(int maxResults, int firstResult) {
        return findConstantesEntities(false, maxResults, firstResult);
    }

    private List<Constantes> findConstantesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Constantes.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Constantes findConstantes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Constantes.class, id);
        } finally {
            em.close();
        }
    }

    public int getConstantesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Constantes> rt = cq.from(Constantes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
