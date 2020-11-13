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
import com.mycompany.fianza.entidades.Promotores;
import com.mycompany.fianza.entidades.Usuarios;
import com.mycompany.fianza.facade.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author dianaplata
 */
public class PromotoresJpaController implements Serializable {

    public PromotoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Promotores promotores) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inmobiliarias idInmobiliaria = promotores.getIdInmobiliaria();
            if (idInmobiliaria != null) {
                idInmobiliaria = em.getReference(idInmobiliaria.getClass(), idInmobiliaria.getId());
                promotores.setIdInmobiliaria(idInmobiliaria);
            }
            ListasGrupo tipoDocumento = promotores.getTipoDocumento();
            if (tipoDocumento != null) {
                tipoDocumento = em.getReference(tipoDocumento.getClass(), tipoDocumento.getId());
                promotores.setTipoDocumento(tipoDocumento);
            }
            Usuarios idUsuario = promotores.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getId());
                promotores.setIdUsuario(idUsuario);
            }
            em.persist(promotores);
            if (idInmobiliaria != null) {
                idInmobiliaria.getPromotoresSet().add(promotores);
                idInmobiliaria = em.merge(idInmobiliaria);
            }
            if (tipoDocumento != null) {
                tipoDocumento.getPromotoresSet().add(promotores);
                tipoDocumento = em.merge(tipoDocumento);
            }
            if (idUsuario != null) {
                idUsuario.getPromotoresSet().add(promotores);
                idUsuario = em.merge(idUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Promotores promotores) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Promotores persistentPromotores = em.find(Promotores.class, promotores.getId());
            Inmobiliarias idInmobiliariaOld = persistentPromotores.getIdInmobiliaria();
            Inmobiliarias idInmobiliariaNew = promotores.getIdInmobiliaria();
            ListasGrupo tipoDocumentoOld = persistentPromotores.getTipoDocumento();
            ListasGrupo tipoDocumentoNew = promotores.getTipoDocumento();
            Usuarios idUsuarioOld = persistentPromotores.getIdUsuario();
            Usuarios idUsuarioNew = promotores.getIdUsuario();
            if (idInmobiliariaNew != null) {
                idInmobiliariaNew = em.getReference(idInmobiliariaNew.getClass(), idInmobiliariaNew.getId());
                promotores.setIdInmobiliaria(idInmobiliariaNew);
            }
            if (tipoDocumentoNew != null) {
                tipoDocumentoNew = em.getReference(tipoDocumentoNew.getClass(), tipoDocumentoNew.getId());
                promotores.setTipoDocumento(tipoDocumentoNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getId());
                promotores.setIdUsuario(idUsuarioNew);
            }
            promotores = em.merge(promotores);
            if (idInmobiliariaOld != null && !idInmobiliariaOld.equals(idInmobiliariaNew)) {
                idInmobiliariaOld.getPromotoresSet().remove(promotores);
                idInmobiliariaOld = em.merge(idInmobiliariaOld);
            }
            if (idInmobiliariaNew != null && !idInmobiliariaNew.equals(idInmobiliariaOld)) {
                idInmobiliariaNew.getPromotoresSet().add(promotores);
                idInmobiliariaNew = em.merge(idInmobiliariaNew);
            }
            if (tipoDocumentoOld != null && !tipoDocumentoOld.equals(tipoDocumentoNew)) {
                tipoDocumentoOld.getPromotoresSet().remove(promotores);
                tipoDocumentoOld = em.merge(tipoDocumentoOld);
            }
            if (tipoDocumentoNew != null && !tipoDocumentoNew.equals(tipoDocumentoOld)) {
                tipoDocumentoNew.getPromotoresSet().add(promotores);
                tipoDocumentoNew = em.merge(tipoDocumentoNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getPromotoresSet().remove(promotores);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getPromotoresSet().add(promotores);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = promotores.getId();
                if (findPromotores(id) == null) {
                    throw new NonexistentEntityException("The promotores with id " + id + " no longer exists.");
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
            Promotores promotores;
            try {
                promotores = em.getReference(Promotores.class, id);
                promotores.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The promotores with id " + id + " no longer exists.", enfe);
            }
            Inmobiliarias idInmobiliaria = promotores.getIdInmobiliaria();
            if (idInmobiliaria != null) {
                idInmobiliaria.getPromotoresSet().remove(promotores);
                idInmobiliaria = em.merge(idInmobiliaria);
            }
            ListasGrupo tipoDocumento = promotores.getTipoDocumento();
            if (tipoDocumento != null) {
                tipoDocumento.getPromotoresSet().remove(promotores);
                tipoDocumento = em.merge(tipoDocumento);
            }
            Usuarios idUsuario = promotores.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getPromotoresSet().remove(promotores);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(promotores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Promotores> findPromotoresEntities() {
        return findPromotoresEntities(true, -1, -1);
    }

    public List<Promotores> findPromotoresEntities(int maxResults, int firstResult) {
        return findPromotoresEntities(false, maxResults, firstResult);
    }

    private List<Promotores> findPromotoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Promotores.class));
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

    public Promotores findPromotores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Promotores.class, id);
        } finally {
            em.close();
        }
    }

    public int getPromotoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Promotores> rt = cq.from(Promotores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
