/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fianza.facade;

import com.mycompany.fianza.entidades.Documentos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.fianza.entidades.ListasGrupo;
import com.mycompany.fianza.entidades.Personas;
import com.mycompany.fianza.facade.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author dianaplata
 */
public class DocumentosJpaController implements Serializable {

    public DocumentosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Documentos documentos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ListasGrupo tipoDocumento = documentos.getTipoDocumento();
            if (tipoDocumento != null) {
                tipoDocumento = em.getReference(tipoDocumento.getClass(), tipoDocumento.getId());
                documentos.setTipoDocumento(tipoDocumento);
            }
            Personas idPersona = documentos.getIdPersona();
            if (idPersona != null) {
                idPersona = em.getReference(idPersona.getClass(), idPersona.getId());
                documentos.setIdPersona(idPersona);
            }
            em.persist(documentos);
            if (tipoDocumento != null) {
                tipoDocumento.getDocumentosSet().add(documentos);
                tipoDocumento = em.merge(tipoDocumento);
            }
            if (idPersona != null) {
                idPersona.getDocumentosSet().add(documentos);
                idPersona = em.merge(idPersona);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Documentos documentos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Documentos persistentDocumentos = em.find(Documentos.class, documentos.getId());
            ListasGrupo tipoDocumentoOld = persistentDocumentos.getTipoDocumento();
            ListasGrupo tipoDocumentoNew = documentos.getTipoDocumento();
            Personas idPersonaOld = persistentDocumentos.getIdPersona();
            Personas idPersonaNew = documentos.getIdPersona();
            if (tipoDocumentoNew != null) {
                tipoDocumentoNew = em.getReference(tipoDocumentoNew.getClass(), tipoDocumentoNew.getId());
                documentos.setTipoDocumento(tipoDocumentoNew);
            }
            if (idPersonaNew != null) {
                idPersonaNew = em.getReference(idPersonaNew.getClass(), idPersonaNew.getId());
                documentos.setIdPersona(idPersonaNew);
            }
            documentos = em.merge(documentos);
            if (tipoDocumentoOld != null && !tipoDocumentoOld.equals(tipoDocumentoNew)) {
                tipoDocumentoOld.getDocumentosSet().remove(documentos);
                tipoDocumentoOld = em.merge(tipoDocumentoOld);
            }
            if (tipoDocumentoNew != null && !tipoDocumentoNew.equals(tipoDocumentoOld)) {
                tipoDocumentoNew.getDocumentosSet().add(documentos);
                tipoDocumentoNew = em.merge(tipoDocumentoNew);
            }
            if (idPersonaOld != null && !idPersonaOld.equals(idPersonaNew)) {
                idPersonaOld.getDocumentosSet().remove(documentos);
                idPersonaOld = em.merge(idPersonaOld);
            }
            if (idPersonaNew != null && !idPersonaNew.equals(idPersonaOld)) {
                idPersonaNew.getDocumentosSet().add(documentos);
                idPersonaNew = em.merge(idPersonaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = documentos.getId();
                if (findDocumentos(id) == null) {
                    throw new NonexistentEntityException("The documentos with id " + id + " no longer exists.");
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
            Documentos documentos;
            try {
                documentos = em.getReference(Documentos.class, id);
                documentos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The documentos with id " + id + " no longer exists.", enfe);
            }
            ListasGrupo tipoDocumento = documentos.getTipoDocumento();
            if (tipoDocumento != null) {
                tipoDocumento.getDocumentosSet().remove(documentos);
                tipoDocumento = em.merge(tipoDocumento);
            }
            Personas idPersona = documentos.getIdPersona();
            if (idPersona != null) {
                idPersona.getDocumentosSet().remove(documentos);
                idPersona = em.merge(idPersona);
            }
            em.remove(documentos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Documentos> findDocumentosEntities() {
        return findDocumentosEntities(true, -1, -1);
    }

    public List<Documentos> findDocumentosEntities(int maxResults, int firstResult) {
        return findDocumentosEntities(false, maxResults, firstResult);
    }

    private List<Documentos> findDocumentosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Documentos.class));
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

    public Documentos findDocumentos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Documentos.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocumentosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Documentos> rt = cq.from(Documentos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
