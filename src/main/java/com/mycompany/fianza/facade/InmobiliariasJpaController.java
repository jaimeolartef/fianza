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
import com.mycompany.fianza.entidades.DetallePersonas;
import com.mycompany.fianza.entidades.Inmobiliarias;
import java.util.HashSet;
import java.util.Set;
import com.mycompany.fianza.entidades.Usuarios;
import com.mycompany.fianza.entidades.Inmuebles;
import com.mycompany.fianza.entidades.Solicitudes;
import com.mycompany.fianza.entidades.Promotores;
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
public class InmobiliariasJpaController implements Serializable {

    public InmobiliariasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inmobiliarias inmobiliarias) {
        if (inmobiliarias.getDetallePersonasSet() == null) {
            inmobiliarias.setDetallePersonasSet(new HashSet<DetallePersonas>());
        }
        if (inmobiliarias.getUsuariosSet() == null) {
            inmobiliarias.setUsuariosSet(new HashSet<Usuarios>());
        }
        if (inmobiliarias.getInmueblesSet() == null) {
            inmobiliarias.setInmueblesSet(new HashSet<Inmuebles>());
        }
        if (inmobiliarias.getSolicitudesSet() == null) {
            inmobiliarias.setSolicitudesSet(new HashSet<Solicitudes>());
        }
        if (inmobiliarias.getPromotoresSet() == null) {
            inmobiliarias.setPromotoresSet(new HashSet<Promotores>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<DetallePersonas> attachedDetallePersonasSet = new HashSet<DetallePersonas>();
            for (DetallePersonas detallePersonasSetDetallePersonasToAttach : inmobiliarias.getDetallePersonasSet()) {
                detallePersonasSetDetallePersonasToAttach = em.getReference(detallePersonasSetDetallePersonasToAttach.getClass(), detallePersonasSetDetallePersonasToAttach.getId());
                attachedDetallePersonasSet.add(detallePersonasSetDetallePersonasToAttach);
            }
            inmobiliarias.setDetallePersonasSet(attachedDetallePersonasSet);
            Set<Usuarios> attachedUsuariosSet = new HashSet<Usuarios>();
            for (Usuarios usuariosSetUsuariosToAttach : inmobiliarias.getUsuariosSet()) {
                usuariosSetUsuariosToAttach = em.getReference(usuariosSetUsuariosToAttach.getClass(), usuariosSetUsuariosToAttach.getId());
                attachedUsuariosSet.add(usuariosSetUsuariosToAttach);
            }
            inmobiliarias.setUsuariosSet(attachedUsuariosSet);
            Set<Inmuebles> attachedInmueblesSet = new HashSet<Inmuebles>();
            for (Inmuebles inmueblesSetInmueblesToAttach : inmobiliarias.getInmueblesSet()) {
                inmueblesSetInmueblesToAttach = em.getReference(inmueblesSetInmueblesToAttach.getClass(), inmueblesSetInmueblesToAttach.getId());
                attachedInmueblesSet.add(inmueblesSetInmueblesToAttach);
            }
            inmobiliarias.setInmueblesSet(attachedInmueblesSet);
            Set<Solicitudes> attachedSolicitudesSet = new HashSet<Solicitudes>();
            for (Solicitudes solicitudesSetSolicitudesToAttach : inmobiliarias.getSolicitudesSet()) {
                solicitudesSetSolicitudesToAttach = em.getReference(solicitudesSetSolicitudesToAttach.getClass(), solicitudesSetSolicitudesToAttach.getId());
                attachedSolicitudesSet.add(solicitudesSetSolicitudesToAttach);
            }
            inmobiliarias.setSolicitudesSet(attachedSolicitudesSet);
            Set<Promotores> attachedPromotoresSet = new HashSet<Promotores>();
            for (Promotores promotoresSetPromotoresToAttach : inmobiliarias.getPromotoresSet()) {
                promotoresSetPromotoresToAttach = em.getReference(promotoresSetPromotoresToAttach.getClass(), promotoresSetPromotoresToAttach.getId());
                attachedPromotoresSet.add(promotoresSetPromotoresToAttach);
            }
            inmobiliarias.setPromotoresSet(attachedPromotoresSet);
            em.persist(inmobiliarias);
            for (DetallePersonas detallePersonasSetDetallePersonas : inmobiliarias.getDetallePersonasSet()) {
                Inmobiliarias oldIdInmobiliariaOfDetallePersonasSetDetallePersonas = detallePersonasSetDetallePersonas.getIdInmobiliaria();
                detallePersonasSetDetallePersonas.setIdInmobiliaria(inmobiliarias);
                detallePersonasSetDetallePersonas = em.merge(detallePersonasSetDetallePersonas);
                if (oldIdInmobiliariaOfDetallePersonasSetDetallePersonas != null) {
                    oldIdInmobiliariaOfDetallePersonasSetDetallePersonas.getDetallePersonasSet().remove(detallePersonasSetDetallePersonas);
                    oldIdInmobiliariaOfDetallePersonasSetDetallePersonas = em.merge(oldIdInmobiliariaOfDetallePersonasSetDetallePersonas);
                }
            }
            for (Usuarios usuariosSetUsuarios : inmobiliarias.getUsuariosSet()) {
                Inmobiliarias oldIdInmobiliariaOfUsuariosSetUsuarios = usuariosSetUsuarios.getIdInmobiliaria();
                usuariosSetUsuarios.setIdInmobiliaria(inmobiliarias);
                usuariosSetUsuarios = em.merge(usuariosSetUsuarios);
                if (oldIdInmobiliariaOfUsuariosSetUsuarios != null) {
                    oldIdInmobiliariaOfUsuariosSetUsuarios.getUsuariosSet().remove(usuariosSetUsuarios);
                    oldIdInmobiliariaOfUsuariosSetUsuarios = em.merge(oldIdInmobiliariaOfUsuariosSetUsuarios);
                }
            }
            for (Inmuebles inmueblesSetInmuebles : inmobiliarias.getInmueblesSet()) {
                Inmobiliarias oldIdInmobiliariaOfInmueblesSetInmuebles = inmueblesSetInmuebles.getIdInmobiliaria();
                inmueblesSetInmuebles.setIdInmobiliaria(inmobiliarias);
                inmueblesSetInmuebles = em.merge(inmueblesSetInmuebles);
                if (oldIdInmobiliariaOfInmueblesSetInmuebles != null) {
                    oldIdInmobiliariaOfInmueblesSetInmuebles.getInmueblesSet().remove(inmueblesSetInmuebles);
                    oldIdInmobiliariaOfInmueblesSetInmuebles = em.merge(oldIdInmobiliariaOfInmueblesSetInmuebles);
                }
            }
            for (Solicitudes solicitudesSetSolicitudes : inmobiliarias.getSolicitudesSet()) {
                Inmobiliarias oldIdInmobiliariaOfSolicitudesSetSolicitudes = solicitudesSetSolicitudes.getIdInmobiliaria();
                solicitudesSetSolicitudes.setIdInmobiliaria(inmobiliarias);
                solicitudesSetSolicitudes = em.merge(solicitudesSetSolicitudes);
                if (oldIdInmobiliariaOfSolicitudesSetSolicitudes != null) {
                    oldIdInmobiliariaOfSolicitudesSetSolicitudes.getSolicitudesSet().remove(solicitudesSetSolicitudes);
                    oldIdInmobiliariaOfSolicitudesSetSolicitudes = em.merge(oldIdInmobiliariaOfSolicitudesSetSolicitudes);
                }
            }
            for (Promotores promotoresSetPromotores : inmobiliarias.getPromotoresSet()) {
                Inmobiliarias oldIdInmobiliariaOfPromotoresSetPromotores = promotoresSetPromotores.getIdInmobiliaria();
                promotoresSetPromotores.setIdInmobiliaria(inmobiliarias);
                promotoresSetPromotores = em.merge(promotoresSetPromotores);
                if (oldIdInmobiliariaOfPromotoresSetPromotores != null) {
                    oldIdInmobiliariaOfPromotoresSetPromotores.getPromotoresSet().remove(promotoresSetPromotores);
                    oldIdInmobiliariaOfPromotoresSetPromotores = em.merge(oldIdInmobiliariaOfPromotoresSetPromotores);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inmobiliarias inmobiliarias) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inmobiliarias persistentInmobiliarias = em.find(Inmobiliarias.class, inmobiliarias.getId());
            Set<DetallePersonas> detallePersonasSetOld = persistentInmobiliarias.getDetallePersonasSet();
            Set<DetallePersonas> detallePersonasSetNew = inmobiliarias.getDetallePersonasSet();
            Set<Usuarios> usuariosSetOld = persistentInmobiliarias.getUsuariosSet();
            Set<Usuarios> usuariosSetNew = inmobiliarias.getUsuariosSet();
            Set<Inmuebles> inmueblesSetOld = persistentInmobiliarias.getInmueblesSet();
            Set<Inmuebles> inmueblesSetNew = inmobiliarias.getInmueblesSet();
            Set<Solicitudes> solicitudesSetOld = persistentInmobiliarias.getSolicitudesSet();
            Set<Solicitudes> solicitudesSetNew = inmobiliarias.getSolicitudesSet();
            Set<Promotores> promotoresSetOld = persistentInmobiliarias.getPromotoresSet();
            Set<Promotores> promotoresSetNew = inmobiliarias.getPromotoresSet();
            List<String> illegalOrphanMessages = null;
            for (DetallePersonas detallePersonasSetOldDetallePersonas : detallePersonasSetOld) {
                if (!detallePersonasSetNew.contains(detallePersonasSetOldDetallePersonas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetallePersonas " + detallePersonasSetOldDetallePersonas + " since its idInmobiliaria field is not nullable.");
                }
            }
            for (Usuarios usuariosSetOldUsuarios : usuariosSetOld) {
                if (!usuariosSetNew.contains(usuariosSetOldUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuarios " + usuariosSetOldUsuarios + " since its idInmobiliaria field is not nullable.");
                }
            }
            for (Inmuebles inmueblesSetOldInmuebles : inmueblesSetOld) {
                if (!inmueblesSetNew.contains(inmueblesSetOldInmuebles)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inmuebles " + inmueblesSetOldInmuebles + " since its idInmobiliaria field is not nullable.");
                }
            }
            for (Solicitudes solicitudesSetOldSolicitudes : solicitudesSetOld) {
                if (!solicitudesSetNew.contains(solicitudesSetOldSolicitudes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solicitudes " + solicitudesSetOldSolicitudes + " since its idInmobiliaria field is not nullable.");
                }
            }
            for (Promotores promotoresSetOldPromotores : promotoresSetOld) {
                if (!promotoresSetNew.contains(promotoresSetOldPromotores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Promotores " + promotoresSetOldPromotores + " since its idInmobiliaria field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<DetallePersonas> attachedDetallePersonasSetNew = new HashSet<DetallePersonas>();
            for (DetallePersonas detallePersonasSetNewDetallePersonasToAttach : detallePersonasSetNew) {
                detallePersonasSetNewDetallePersonasToAttach = em.getReference(detallePersonasSetNewDetallePersonasToAttach.getClass(), detallePersonasSetNewDetallePersonasToAttach.getId());
                attachedDetallePersonasSetNew.add(detallePersonasSetNewDetallePersonasToAttach);
            }
            detallePersonasSetNew = attachedDetallePersonasSetNew;
            inmobiliarias.setDetallePersonasSet(detallePersonasSetNew);
            Set<Usuarios> attachedUsuariosSetNew = new HashSet<Usuarios>();
            for (Usuarios usuariosSetNewUsuariosToAttach : usuariosSetNew) {
                usuariosSetNewUsuariosToAttach = em.getReference(usuariosSetNewUsuariosToAttach.getClass(), usuariosSetNewUsuariosToAttach.getId());
                attachedUsuariosSetNew.add(usuariosSetNewUsuariosToAttach);
            }
            usuariosSetNew = attachedUsuariosSetNew;
            inmobiliarias.setUsuariosSet(usuariosSetNew);
            Set<Inmuebles> attachedInmueblesSetNew = new HashSet<Inmuebles>();
            for (Inmuebles inmueblesSetNewInmueblesToAttach : inmueblesSetNew) {
                inmueblesSetNewInmueblesToAttach = em.getReference(inmueblesSetNewInmueblesToAttach.getClass(), inmueblesSetNewInmueblesToAttach.getId());
                attachedInmueblesSetNew.add(inmueblesSetNewInmueblesToAttach);
            }
            inmueblesSetNew = attachedInmueblesSetNew;
            inmobiliarias.setInmueblesSet(inmueblesSetNew);
            Set<Solicitudes> attachedSolicitudesSetNew = new HashSet<Solicitudes>();
            for (Solicitudes solicitudesSetNewSolicitudesToAttach : solicitudesSetNew) {
                solicitudesSetNewSolicitudesToAttach = em.getReference(solicitudesSetNewSolicitudesToAttach.getClass(), solicitudesSetNewSolicitudesToAttach.getId());
                attachedSolicitudesSetNew.add(solicitudesSetNewSolicitudesToAttach);
            }
            solicitudesSetNew = attachedSolicitudesSetNew;
            inmobiliarias.setSolicitudesSet(solicitudesSetNew);
            Set<Promotores> attachedPromotoresSetNew = new HashSet<Promotores>();
            for (Promotores promotoresSetNewPromotoresToAttach : promotoresSetNew) {
                promotoresSetNewPromotoresToAttach = em.getReference(promotoresSetNewPromotoresToAttach.getClass(), promotoresSetNewPromotoresToAttach.getId());
                attachedPromotoresSetNew.add(promotoresSetNewPromotoresToAttach);
            }
            promotoresSetNew = attachedPromotoresSetNew;
            inmobiliarias.setPromotoresSet(promotoresSetNew);
            inmobiliarias = em.merge(inmobiliarias);
            for (DetallePersonas detallePersonasSetNewDetallePersonas : detallePersonasSetNew) {
                if (!detallePersonasSetOld.contains(detallePersonasSetNewDetallePersonas)) {
                    Inmobiliarias oldIdInmobiliariaOfDetallePersonasSetNewDetallePersonas = detallePersonasSetNewDetallePersonas.getIdInmobiliaria();
                    detallePersonasSetNewDetallePersonas.setIdInmobiliaria(inmobiliarias);
                    detallePersonasSetNewDetallePersonas = em.merge(detallePersonasSetNewDetallePersonas);
                    if (oldIdInmobiliariaOfDetallePersonasSetNewDetallePersonas != null && !oldIdInmobiliariaOfDetallePersonasSetNewDetallePersonas.equals(inmobiliarias)) {
                        oldIdInmobiliariaOfDetallePersonasSetNewDetallePersonas.getDetallePersonasSet().remove(detallePersonasSetNewDetallePersonas);
                        oldIdInmobiliariaOfDetallePersonasSetNewDetallePersonas = em.merge(oldIdInmobiliariaOfDetallePersonasSetNewDetallePersonas);
                    }
                }
            }
            for (Usuarios usuariosSetNewUsuarios : usuariosSetNew) {
                if (!usuariosSetOld.contains(usuariosSetNewUsuarios)) {
                    Inmobiliarias oldIdInmobiliariaOfUsuariosSetNewUsuarios = usuariosSetNewUsuarios.getIdInmobiliaria();
                    usuariosSetNewUsuarios.setIdInmobiliaria(inmobiliarias);
                    usuariosSetNewUsuarios = em.merge(usuariosSetNewUsuarios);
                    if (oldIdInmobiliariaOfUsuariosSetNewUsuarios != null && !oldIdInmobiliariaOfUsuariosSetNewUsuarios.equals(inmobiliarias)) {
                        oldIdInmobiliariaOfUsuariosSetNewUsuarios.getUsuariosSet().remove(usuariosSetNewUsuarios);
                        oldIdInmobiliariaOfUsuariosSetNewUsuarios = em.merge(oldIdInmobiliariaOfUsuariosSetNewUsuarios);
                    }
                }
            }
            for (Inmuebles inmueblesSetNewInmuebles : inmueblesSetNew) {
                if (!inmueblesSetOld.contains(inmueblesSetNewInmuebles)) {
                    Inmobiliarias oldIdInmobiliariaOfInmueblesSetNewInmuebles = inmueblesSetNewInmuebles.getIdInmobiliaria();
                    inmueblesSetNewInmuebles.setIdInmobiliaria(inmobiliarias);
                    inmueblesSetNewInmuebles = em.merge(inmueblesSetNewInmuebles);
                    if (oldIdInmobiliariaOfInmueblesSetNewInmuebles != null && !oldIdInmobiliariaOfInmueblesSetNewInmuebles.equals(inmobiliarias)) {
                        oldIdInmobiliariaOfInmueblesSetNewInmuebles.getInmueblesSet().remove(inmueblesSetNewInmuebles);
                        oldIdInmobiliariaOfInmueblesSetNewInmuebles = em.merge(oldIdInmobiliariaOfInmueblesSetNewInmuebles);
                    }
                }
            }
            for (Solicitudes solicitudesSetNewSolicitudes : solicitudesSetNew) {
                if (!solicitudesSetOld.contains(solicitudesSetNewSolicitudes)) {
                    Inmobiliarias oldIdInmobiliariaOfSolicitudesSetNewSolicitudes = solicitudesSetNewSolicitudes.getIdInmobiliaria();
                    solicitudesSetNewSolicitudes.setIdInmobiliaria(inmobiliarias);
                    solicitudesSetNewSolicitudes = em.merge(solicitudesSetNewSolicitudes);
                    if (oldIdInmobiliariaOfSolicitudesSetNewSolicitudes != null && !oldIdInmobiliariaOfSolicitudesSetNewSolicitudes.equals(inmobiliarias)) {
                        oldIdInmobiliariaOfSolicitudesSetNewSolicitudes.getSolicitudesSet().remove(solicitudesSetNewSolicitudes);
                        oldIdInmobiliariaOfSolicitudesSetNewSolicitudes = em.merge(oldIdInmobiliariaOfSolicitudesSetNewSolicitudes);
                    }
                }
            }
            for (Promotores promotoresSetNewPromotores : promotoresSetNew) {
                if (!promotoresSetOld.contains(promotoresSetNewPromotores)) {
                    Inmobiliarias oldIdInmobiliariaOfPromotoresSetNewPromotores = promotoresSetNewPromotores.getIdInmobiliaria();
                    promotoresSetNewPromotores.setIdInmobiliaria(inmobiliarias);
                    promotoresSetNewPromotores = em.merge(promotoresSetNewPromotores);
                    if (oldIdInmobiliariaOfPromotoresSetNewPromotores != null && !oldIdInmobiliariaOfPromotoresSetNewPromotores.equals(inmobiliarias)) {
                        oldIdInmobiliariaOfPromotoresSetNewPromotores.getPromotoresSet().remove(promotoresSetNewPromotores);
                        oldIdInmobiliariaOfPromotoresSetNewPromotores = em.merge(oldIdInmobiliariaOfPromotoresSetNewPromotores);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = inmobiliarias.getId();
                if (findInmobiliarias(id) == null) {
                    throw new NonexistentEntityException("The inmobiliarias with id " + id + " no longer exists.");
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
            Inmobiliarias inmobiliarias;
            try {
                inmobiliarias = em.getReference(Inmobiliarias.class, id);
                inmobiliarias.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inmobiliarias with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<DetallePersonas> detallePersonasSetOrphanCheck = inmobiliarias.getDetallePersonasSet();
            for (DetallePersonas detallePersonasSetOrphanCheckDetallePersonas : detallePersonasSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Inmobiliarias (" + inmobiliarias + ") cannot be destroyed since the DetallePersonas " + detallePersonasSetOrphanCheckDetallePersonas + " in its detallePersonasSet field has a non-nullable idInmobiliaria field.");
            }
            Set<Usuarios> usuariosSetOrphanCheck = inmobiliarias.getUsuariosSet();
            for (Usuarios usuariosSetOrphanCheckUsuarios : usuariosSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Inmobiliarias (" + inmobiliarias + ") cannot be destroyed since the Usuarios " + usuariosSetOrphanCheckUsuarios + " in its usuariosSet field has a non-nullable idInmobiliaria field.");
            }
            Set<Inmuebles> inmueblesSetOrphanCheck = inmobiliarias.getInmueblesSet();
            for (Inmuebles inmueblesSetOrphanCheckInmuebles : inmueblesSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Inmobiliarias (" + inmobiliarias + ") cannot be destroyed since the Inmuebles " + inmueblesSetOrphanCheckInmuebles + " in its inmueblesSet field has a non-nullable idInmobiliaria field.");
            }
            Set<Solicitudes> solicitudesSetOrphanCheck = inmobiliarias.getSolicitudesSet();
            for (Solicitudes solicitudesSetOrphanCheckSolicitudes : solicitudesSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Inmobiliarias (" + inmobiliarias + ") cannot be destroyed since the Solicitudes " + solicitudesSetOrphanCheckSolicitudes + " in its solicitudesSet field has a non-nullable idInmobiliaria field.");
            }
            Set<Promotores> promotoresSetOrphanCheck = inmobiliarias.getPromotoresSet();
            for (Promotores promotoresSetOrphanCheckPromotores : promotoresSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Inmobiliarias (" + inmobiliarias + ") cannot be destroyed since the Promotores " + promotoresSetOrphanCheckPromotores + " in its promotoresSet field has a non-nullable idInmobiliaria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(inmobiliarias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Inmobiliarias> findInmobiliariasEntities() {
        return findInmobiliariasEntities(true, -1, -1);
    }

    public List<Inmobiliarias> findInmobiliariasEntities(int maxResults, int firstResult) {
        return findInmobiliariasEntities(false, maxResults, firstResult);
    }

    private List<Inmobiliarias> findInmobiliariasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inmobiliarias.class));
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

    public Inmobiliarias findInmobiliarias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inmobiliarias.class, id);
        } finally {
            em.close();
        }
    }

    public int getInmobiliariasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inmobiliarias> rt = cq.from(Inmobiliarias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
