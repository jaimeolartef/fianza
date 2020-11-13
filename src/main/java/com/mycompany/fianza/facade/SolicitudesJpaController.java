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
import com.mycompany.fianza.entidades.ListasGrupo;
import com.mycompany.fianza.entidades.Solicitudes;
import com.mycompany.fianza.facade.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author dianaplata
 */
public class SolicitudesJpaController implements Serializable {

    public SolicitudesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Solicitudes solicitudes) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inmobiliarias idInmobiliaria = solicitudes.getIdInmobiliaria();
            if (idInmobiliaria != null) {
                idInmobiliaria = em.getReference(idInmobiliaria.getClass(), idInmobiliaria.getId());
                solicitudes.setIdInmobiliaria(idInmobiliaria);
            }
            ListasGrupo tipoInmueble = solicitudes.getTipoInmueble();
            if (tipoInmueble != null) {
                tipoInmueble = em.getReference(tipoInmueble.getClass(), tipoInmueble.getId());
                solicitudes.setTipoInmueble(tipoInmueble);
            }
            em.persist(solicitudes);
            if (idInmobiliaria != null) {
                idInmobiliaria.getSolicitudesSet().add(solicitudes);
                idInmobiliaria = em.merge(idInmobiliaria);
            }
            if (tipoInmueble != null) {
                tipoInmueble.getSolicitudesSet().add(solicitudes);
                tipoInmueble = em.merge(tipoInmueble);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Solicitudes solicitudes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Solicitudes persistentSolicitudes = em.find(Solicitudes.class, solicitudes.getId());
            Inmobiliarias idInmobiliariaOld = persistentSolicitudes.getIdInmobiliaria();
            Inmobiliarias idInmobiliariaNew = solicitudes.getIdInmobiliaria();
            ListasGrupo tipoInmuebleOld = persistentSolicitudes.getTipoInmueble();
            ListasGrupo tipoInmuebleNew = solicitudes.getTipoInmueble();
            if (idInmobiliariaNew != null) {
                idInmobiliariaNew = em.getReference(idInmobiliariaNew.getClass(), idInmobiliariaNew.getId());
                solicitudes.setIdInmobiliaria(idInmobiliariaNew);
            }
            if (tipoInmuebleNew != null) {
                tipoInmuebleNew = em.getReference(tipoInmuebleNew.getClass(), tipoInmuebleNew.getId());
                solicitudes.setTipoInmueble(tipoInmuebleNew);
            }
            solicitudes = em.merge(solicitudes);
            if (idInmobiliariaOld != null && !idInmobiliariaOld.equals(idInmobiliariaNew)) {
                idInmobiliariaOld.getSolicitudesSet().remove(solicitudes);
                idInmobiliariaOld = em.merge(idInmobiliariaOld);
            }
            if (idInmobiliariaNew != null && !idInmobiliariaNew.equals(idInmobiliariaOld)) {
                idInmobiliariaNew.getSolicitudesSet().add(solicitudes);
                idInmobiliariaNew = em.merge(idInmobiliariaNew);
            }
            if (tipoInmuebleOld != null && !tipoInmuebleOld.equals(tipoInmuebleNew)) {
                tipoInmuebleOld.getSolicitudesSet().remove(solicitudes);
                tipoInmuebleOld = em.merge(tipoInmuebleOld);
            }
            if (tipoInmuebleNew != null && !tipoInmuebleNew.equals(tipoInmuebleOld)) {
                tipoInmuebleNew.getSolicitudesSet().add(solicitudes);
                tipoInmuebleNew = em.merge(tipoInmuebleNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = solicitudes.getId();
                if (findSolicitudes(id) == null) {
                    throw new NonexistentEntityException("The solicitudes with id " + id + " no longer exists.");
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
            Solicitudes solicitudes;
            try {
                solicitudes = em.getReference(Solicitudes.class, id);
                solicitudes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solicitudes with id " + id + " no longer exists.", enfe);
            }
            Inmobiliarias idInmobiliaria = solicitudes.getIdInmobiliaria();
            if (idInmobiliaria != null) {
                idInmobiliaria.getSolicitudesSet().remove(solicitudes);
                idInmobiliaria = em.merge(idInmobiliaria);
            }
            ListasGrupo tipoInmueble = solicitudes.getTipoInmueble();
            if (tipoInmueble != null) {
                tipoInmueble.getSolicitudesSet().remove(solicitudes);
                tipoInmueble = em.merge(tipoInmueble);
            }
            em.remove(solicitudes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Solicitudes> findSolicitudesEntities() {
        return findSolicitudesEntities(true, -1, -1);
    }

    public List<Solicitudes> findSolicitudesEntities(int maxResults, int firstResult) {
        return findSolicitudesEntities(false, maxResults, firstResult);
    }

    private List<Solicitudes> findSolicitudesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Solicitudes.class));
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

    public Solicitudes findSolicitudes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Solicitudes.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolicitudesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Solicitudes> rt = cq.from(Solicitudes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
