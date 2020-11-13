/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fianza.facade;

import com.mycompany.fianza.entidades.DetallePersonas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.fianza.entidades.Inmobiliarias;
import com.mycompany.fianza.entidades.ListasGrupo;
import com.mycompany.fianza.facade.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author dianaplata
 */
public class DetallePersonasJpaController implements Serializable {

    public DetallePersonasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetallePersonas detallePersonas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inmobiliarias idInmobiliaria = detallePersonas.getIdInmobiliaria();
            if (idInmobiliaria != null) {
                idInmobiliaria = em.getReference(idInmobiliaria.getClass(), idInmobiliaria.getId());
                detallePersonas.setIdInmobiliaria(idInmobiliaria);
            }
            ListasGrupo estado = detallePersonas.getEstado();
            if (estado != null) {
                estado = em.getReference(estado.getClass(), estado.getId());
                detallePersonas.setEstado(estado);
            }
            ListasGrupo tipoPersona = detallePersonas.getTipoPersona();
            if (tipoPersona != null) {
                tipoPersona = em.getReference(tipoPersona.getClass(), tipoPersona.getId());
                detallePersonas.setTipoPersona(tipoPersona);
            }
            em.persist(detallePersonas);
            if (idInmobiliaria != null) {
                idInmobiliaria.getDetallePersonasSet().add(detallePersonas);
                idInmobiliaria = em.merge(idInmobiliaria);
            }
            if (estado != null) {
                estado.getDetallePersonasSet().add(detallePersonas);
                estado = em.merge(estado);
            }
            if (tipoPersona != null) {
                tipoPersona.getDetallePersonasSet().add(detallePersonas);
                tipoPersona = em.merge(tipoPersona);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetallePersonas detallePersonas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetallePersonas persistentDetallePersonas = em.find(DetallePersonas.class, detallePersonas.getId());
            Inmobiliarias idInmobiliariaOld = persistentDetallePersonas.getIdInmobiliaria();
            Inmobiliarias idInmobiliariaNew = detallePersonas.getIdInmobiliaria();
            ListasGrupo estadoOld = persistentDetallePersonas.getEstado();
            ListasGrupo estadoNew = detallePersonas.getEstado();
            ListasGrupo tipoPersonaOld = persistentDetallePersonas.getTipoPersona();
            ListasGrupo tipoPersonaNew = detallePersonas.getTipoPersona();
            if (idInmobiliariaNew != null) {
                idInmobiliariaNew = em.getReference(idInmobiliariaNew.getClass(), idInmobiliariaNew.getId());
                detallePersonas.setIdInmobiliaria(idInmobiliariaNew);
            }
            if (estadoNew != null) {
                estadoNew = em.getReference(estadoNew.getClass(), estadoNew.getId());
                detallePersonas.setEstado(estadoNew);
            }
            if (tipoPersonaNew != null) {
                tipoPersonaNew = em.getReference(tipoPersonaNew.getClass(), tipoPersonaNew.getId());
                detallePersonas.setTipoPersona(tipoPersonaNew);
            }
            detallePersonas = em.merge(detallePersonas);
            if (idInmobiliariaOld != null && !idInmobiliariaOld.equals(idInmobiliariaNew)) {
                idInmobiliariaOld.getDetallePersonasSet().remove(detallePersonas);
                idInmobiliariaOld = em.merge(idInmobiliariaOld);
            }
            if (idInmobiliariaNew != null && !idInmobiliariaNew.equals(idInmobiliariaOld)) {
                idInmobiliariaNew.getDetallePersonasSet().add(detallePersonas);
                idInmobiliariaNew = em.merge(idInmobiliariaNew);
            }
            if (estadoOld != null && !estadoOld.equals(estadoNew)) {
                estadoOld.getDetallePersonasSet().remove(detallePersonas);
                estadoOld = em.merge(estadoOld);
            }
            if (estadoNew != null && !estadoNew.equals(estadoOld)) {
                estadoNew.getDetallePersonasSet().add(detallePersonas);
                estadoNew = em.merge(estadoNew);
            }
            if (tipoPersonaOld != null && !tipoPersonaOld.equals(tipoPersonaNew)) {
                tipoPersonaOld.getDetallePersonasSet().remove(detallePersonas);
                tipoPersonaOld = em.merge(tipoPersonaOld);
            }
            if (tipoPersonaNew != null && !tipoPersonaNew.equals(tipoPersonaOld)) {
                tipoPersonaNew.getDetallePersonasSet().add(detallePersonas);
                tipoPersonaNew = em.merge(tipoPersonaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detallePersonas.getId();
                if (findDetallePersonas(id) == null) {
                    throw new NonexistentEntityException("The detallePersonas with id " + id + " no longer exists.");
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
            DetallePersonas detallePersonas;
            try {
                detallePersonas = em.getReference(DetallePersonas.class, id);
                detallePersonas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detallePersonas with id " + id + " no longer exists.", enfe);
            }
            Inmobiliarias idInmobiliaria = detallePersonas.getIdInmobiliaria();
            if (idInmobiliaria != null) {
                idInmobiliaria.getDetallePersonasSet().remove(detallePersonas);
                idInmobiliaria = em.merge(idInmobiliaria);
            }
            ListasGrupo estado = detallePersonas.getEstado();
            if (estado != null) {
                estado.getDetallePersonasSet().remove(detallePersonas);
                estado = em.merge(estado);
            }
            ListasGrupo tipoPersona = detallePersonas.getTipoPersona();
            if (tipoPersona != null) {
                tipoPersona.getDetallePersonasSet().remove(detallePersonas);
                tipoPersona = em.merge(tipoPersona);
            }
            em.remove(detallePersonas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetallePersonas> findDetallePersonasEntities() {
        return findDetallePersonasEntities(true, -1, -1);
    }

    public List<DetallePersonas> findDetallePersonasEntities(int maxResults, int firstResult) {
        return findDetallePersonasEntities(false, maxResults, firstResult);
    }

    private List<DetallePersonas> findDetallePersonasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetallePersonas.class));
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

    public DetallePersonas findDetallePersonas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetallePersonas.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetallePersonasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetallePersonas> rt = cq.from(DetallePersonas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
