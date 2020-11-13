/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fianza.facade;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.fianza.entidades.Inmobiliarias;
import com.mycompany.fianza.entidades.Inmuebles;
import com.mycompany.fianza.entidades.Personas;
import com.mycompany.fianza.facade.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author dianaplata
 */
public class InmueblesJpaController implements Serializable {

    public InmueblesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inmuebles inmuebles) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inmobiliarias idInmobiliaria = inmuebles.getIdInmobiliaria();
            if (idInmobiliaria != null) {
                idInmobiliaria = em.getReference(idInmobiliaria.getClass(), idInmobiliaria.getId());
                inmuebles.setIdInmobiliaria(idInmobiliaria);
            }
            Personas idPersona = inmuebles.getIdPersona();
            if (idPersona != null) {
                idPersona = em.getReference(idPersona.getClass(), idPersona.getId());
                inmuebles.setIdPersona(idPersona);
            }
            em.persist(inmuebles);
            if (idInmobiliaria != null) {
                idInmobiliaria.getInmueblesSet().add(inmuebles);
                idInmobiliaria = em.merge(idInmobiliaria);
            }
            if (idPersona != null) {
                idPersona.getInmueblesSet().add(inmuebles);
                idPersona = em.merge(idPersona);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inmuebles inmuebles) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inmuebles persistentInmuebles = em.find(Inmuebles.class, inmuebles.getId());
            Inmobiliarias idInmobiliariaOld = persistentInmuebles.getIdInmobiliaria();
            Inmobiliarias idInmobiliariaNew = inmuebles.getIdInmobiliaria();
            Personas idPersonaOld = persistentInmuebles.getIdPersona();
            Personas idPersonaNew = inmuebles.getIdPersona();
            if (idInmobiliariaNew != null) {
                idInmobiliariaNew = em.getReference(idInmobiliariaNew.getClass(), idInmobiliariaNew.getId());
                inmuebles.setIdInmobiliaria(idInmobiliariaNew);
            }
            if (idPersonaNew != null) {
                idPersonaNew = em.getReference(idPersonaNew.getClass(), idPersonaNew.getId());
                inmuebles.setIdPersona(idPersonaNew);
            }
            inmuebles = em.merge(inmuebles);
            if (idInmobiliariaOld != null && !idInmobiliariaOld.equals(idInmobiliariaNew)) {
                idInmobiliariaOld.getInmueblesSet().remove(inmuebles);
                idInmobiliariaOld = em.merge(idInmobiliariaOld);
            }
            if (idInmobiliariaNew != null && !idInmobiliariaNew.equals(idInmobiliariaOld)) {
                idInmobiliariaNew.getInmueblesSet().add(inmuebles);
                idInmobiliariaNew = em.merge(idInmobiliariaNew);
            }
            if (idPersonaOld != null && !idPersonaOld.equals(idPersonaNew)) {
                idPersonaOld.getInmueblesSet().remove(inmuebles);
                idPersonaOld = em.merge(idPersonaOld);
            }
            if (idPersonaNew != null && !idPersonaNew.equals(idPersonaOld)) {
                idPersonaNew.getInmueblesSet().add(inmuebles);
                idPersonaNew = em.merge(idPersonaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = inmuebles.getId();
                if (findInmuebles(id) == null) {
                    throw new NonexistentEntityException("The inmuebles with id " + id + " no longer exists.");
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
            Inmuebles inmuebles;
            try {
                inmuebles = em.getReference(Inmuebles.class, id);
                inmuebles.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inmuebles with id " + id + " no longer exists.", enfe);
            }
            Inmobiliarias idInmobiliaria = inmuebles.getIdInmobiliaria();
            if (idInmobiliaria != null) {
                idInmobiliaria.getInmueblesSet().remove(inmuebles);
                idInmobiliaria = em.merge(idInmobiliaria);
            }
            Personas idPersona = inmuebles.getIdPersona();
            if (idPersona != null) {
                idPersona.getInmueblesSet().remove(inmuebles);
                idPersona = em.merge(idPersona);
            }
            em.remove(inmuebles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Inmuebles> findInmueblesEntities() {
        return findInmueblesEntities(true, -1, -1);
    }

    public List<Inmuebles> findInmueblesEntities(int maxResults, int firstResult) {
        return findInmueblesEntities(false, maxResults, firstResult);
    }

    private List<Inmuebles> findInmueblesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inmuebles.class));
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

    public Inmuebles findInmuebles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inmuebles.class, id);
        } finally {
            em.close();
        }
    }

    public int getInmueblesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inmuebles> rt = cq.from(Inmuebles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
