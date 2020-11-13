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
import com.mycompany.fianza.entidades.ListasGrupo;
import com.mycompany.fianza.entidades.Inmuebles;
import java.util.HashSet;
import java.util.Set;
import com.mycompany.fianza.entidades.Documentos;
import com.mycompany.fianza.entidades.Personas;
import com.mycompany.fianza.facade.exceptions.IllegalOrphanException;
import com.mycompany.fianza.facade.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author dianaplata
 */
public class PersonasJpaController implements Serializable {

    public PersonasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Personas personas) {
        if (personas.getInmueblesSet() == null) {
            personas.setInmueblesSet(new HashSet<Inmuebles>());
        }
        if (personas.getDocumentosSet() == null) {
            personas.setDocumentosSet(new HashSet<Documentos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ListasGrupo tipoDocumento = personas.getTipoDocumento();
            if (tipoDocumento != null) {
                tipoDocumento = em.getReference(tipoDocumento.getClass(), tipoDocumento.getId());
                personas.setTipoDocumento(tipoDocumento);
            }
            Set<Inmuebles> attachedInmueblesSet = new HashSet<Inmuebles>();
            for (Inmuebles inmueblesSetInmueblesToAttach : personas.getInmueblesSet()) {
                inmueblesSetInmueblesToAttach = em.getReference(inmueblesSetInmueblesToAttach.getClass(), inmueblesSetInmueblesToAttach.getId());
                attachedInmueblesSet.add(inmueblesSetInmueblesToAttach);
            }
            personas.setInmueblesSet(attachedInmueblesSet);
            Set<Documentos> attachedDocumentosSet = new HashSet<Documentos>();
            for (Documentos documentosSetDocumentosToAttach : personas.getDocumentosSet()) {
                documentosSetDocumentosToAttach = em.getReference(documentosSetDocumentosToAttach.getClass(), documentosSetDocumentosToAttach.getId());
                attachedDocumentosSet.add(documentosSetDocumentosToAttach);
            }
            personas.setDocumentosSet(attachedDocumentosSet);
            em.persist(personas);
            if (tipoDocumento != null) {
                tipoDocumento.getPersonasSet().add(personas);
                tipoDocumento = em.merge(tipoDocumento);
            }
            for (Inmuebles inmueblesSetInmuebles : personas.getInmueblesSet()) {
                Personas oldIdPersonaOfInmueblesSetInmuebles = inmueblesSetInmuebles.getIdPersona();
                inmueblesSetInmuebles.setIdPersona(personas);
                inmueblesSetInmuebles = em.merge(inmueblesSetInmuebles);
                if (oldIdPersonaOfInmueblesSetInmuebles != null) {
                    oldIdPersonaOfInmueblesSetInmuebles.getInmueblesSet().remove(inmueblesSetInmuebles);
                    oldIdPersonaOfInmueblesSetInmuebles = em.merge(oldIdPersonaOfInmueblesSetInmuebles);
                }
            }
            for (Documentos documentosSetDocumentos : personas.getDocumentosSet()) {
                Personas oldIdPersonaOfDocumentosSetDocumentos = documentosSetDocumentos.getIdPersona();
                documentosSetDocumentos.setIdPersona(personas);
                documentosSetDocumentos = em.merge(documentosSetDocumentos);
                if (oldIdPersonaOfDocumentosSetDocumentos != null) {
                    oldIdPersonaOfDocumentosSetDocumentos.getDocumentosSet().remove(documentosSetDocumentos);
                    oldIdPersonaOfDocumentosSetDocumentos = em.merge(oldIdPersonaOfDocumentosSetDocumentos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Personas personas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personas persistentPersonas = em.find(Personas.class, personas.getId());
            ListasGrupo tipoDocumentoOld = persistentPersonas.getTipoDocumento();
            ListasGrupo tipoDocumentoNew = personas.getTipoDocumento();
            Set<Inmuebles> inmueblesSetOld = persistentPersonas.getInmueblesSet();
            Set<Inmuebles> inmueblesSetNew = personas.getInmueblesSet();
            Set<Documentos> documentosSetOld = persistentPersonas.getDocumentosSet();
            Set<Documentos> documentosSetNew = personas.getDocumentosSet();
            List<String> illegalOrphanMessages = null;
            for (Inmuebles inmueblesSetOldInmuebles : inmueblesSetOld) {
                if (!inmueblesSetNew.contains(inmueblesSetOldInmuebles)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inmuebles " + inmueblesSetOldInmuebles + " since its idPersona field is not nullable.");
                }
            }
            for (Documentos documentosSetOldDocumentos : documentosSetOld) {
                if (!documentosSetNew.contains(documentosSetOldDocumentos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Documentos " + documentosSetOldDocumentos + " since its idPersona field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tipoDocumentoNew != null) {
                tipoDocumentoNew = em.getReference(tipoDocumentoNew.getClass(), tipoDocumentoNew.getId());
                personas.setTipoDocumento(tipoDocumentoNew);
            }
            Set<Inmuebles> attachedInmueblesSetNew = new HashSet<Inmuebles>();
            for (Inmuebles inmueblesSetNewInmueblesToAttach : inmueblesSetNew) {
                inmueblesSetNewInmueblesToAttach = em.getReference(inmueblesSetNewInmueblesToAttach.getClass(), inmueblesSetNewInmueblesToAttach.getId());
                attachedInmueblesSetNew.add(inmueblesSetNewInmueblesToAttach);
            }
            inmueblesSetNew = attachedInmueblesSetNew;
            personas.setInmueblesSet(inmueblesSetNew);
            Set<Documentos> attachedDocumentosSetNew = new HashSet<Documentos>();
            for (Documentos documentosSetNewDocumentosToAttach : documentosSetNew) {
                documentosSetNewDocumentosToAttach = em.getReference(documentosSetNewDocumentosToAttach.getClass(), documentosSetNewDocumentosToAttach.getId());
                attachedDocumentosSetNew.add(documentosSetNewDocumentosToAttach);
            }
            documentosSetNew = attachedDocumentosSetNew;
            personas.setDocumentosSet(documentosSetNew);
            personas = em.merge(personas);
            if (tipoDocumentoOld != null && !tipoDocumentoOld.equals(tipoDocumentoNew)) {
                tipoDocumentoOld.getPersonasSet().remove(personas);
                tipoDocumentoOld = em.merge(tipoDocumentoOld);
            }
            if (tipoDocumentoNew != null && !tipoDocumentoNew.equals(tipoDocumentoOld)) {
                tipoDocumentoNew.getPersonasSet().add(personas);
                tipoDocumentoNew = em.merge(tipoDocumentoNew);
            }
            for (Inmuebles inmueblesSetNewInmuebles : inmueblesSetNew) {
                if (!inmueblesSetOld.contains(inmueblesSetNewInmuebles)) {
                    Personas oldIdPersonaOfInmueblesSetNewInmuebles = inmueblesSetNewInmuebles.getIdPersona();
                    inmueblesSetNewInmuebles.setIdPersona(personas);
                    inmueblesSetNewInmuebles = em.merge(inmueblesSetNewInmuebles);
                    if (oldIdPersonaOfInmueblesSetNewInmuebles != null && !oldIdPersonaOfInmueblesSetNewInmuebles.equals(personas)) {
                        oldIdPersonaOfInmueblesSetNewInmuebles.getInmueblesSet().remove(inmueblesSetNewInmuebles);
                        oldIdPersonaOfInmueblesSetNewInmuebles = em.merge(oldIdPersonaOfInmueblesSetNewInmuebles);
                    }
                }
            }
            for (Documentos documentosSetNewDocumentos : documentosSetNew) {
                if (!documentosSetOld.contains(documentosSetNewDocumentos)) {
                    Personas oldIdPersonaOfDocumentosSetNewDocumentos = documentosSetNewDocumentos.getIdPersona();
                    documentosSetNewDocumentos.setIdPersona(personas);
                    documentosSetNewDocumentos = em.merge(documentosSetNewDocumentos);
                    if (oldIdPersonaOfDocumentosSetNewDocumentos != null && !oldIdPersonaOfDocumentosSetNewDocumentos.equals(personas)) {
                        oldIdPersonaOfDocumentosSetNewDocumentos.getDocumentosSet().remove(documentosSetNewDocumentos);
                        oldIdPersonaOfDocumentosSetNewDocumentos = em.merge(oldIdPersonaOfDocumentosSetNewDocumentos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = personas.getId();
                if (findPersonas(id) == null) {
                    throw new NonexistentEntityException("The personas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personas personas;
            try {
                personas = em.getReference(Personas.class, id);
                personas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<Inmuebles> inmueblesSetOrphanCheck = personas.getInmueblesSet();
            for (Inmuebles inmueblesSetOrphanCheckInmuebles : inmueblesSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Personas (" + personas + ") cannot be destroyed since the Inmuebles " + inmueblesSetOrphanCheckInmuebles + " in its inmueblesSet field has a non-nullable idPersona field.");
            }
            Set<Documentos> documentosSetOrphanCheck = personas.getDocumentosSet();
            for (Documentos documentosSetOrphanCheckDocumentos : documentosSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Personas (" + personas + ") cannot be destroyed since the Documentos " + documentosSetOrphanCheckDocumentos + " in its documentosSet field has a non-nullable idPersona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ListasGrupo tipoDocumento = personas.getTipoDocumento();
            if (tipoDocumento != null) {
                tipoDocumento.getPersonasSet().remove(personas);
                tipoDocumento = em.merge(tipoDocumento);
            }
            em.remove(personas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Personas> findPersonasEntities() {
        return findPersonasEntities(true, -1, -1);
    }

    public List<Personas> findPersonasEntities(int maxResults, int firstResult) {
        return findPersonasEntities(false, maxResults, firstResult);
    }

    private List<Personas> findPersonasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Personas.class));
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

    public Personas findPersonas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Personas.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Personas> rt = cq.from(Personas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
