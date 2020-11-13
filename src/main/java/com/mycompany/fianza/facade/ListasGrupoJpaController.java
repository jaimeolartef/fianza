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
import java.util.HashSet;
import java.util.Set;
import com.mycompany.fianza.entidades.Usuarios;
import com.mycompany.fianza.entidades.Personas;
import com.mycompany.fianza.entidades.Solicitudes;
import com.mycompany.fianza.entidades.Documentos;
import com.mycompany.fianza.entidades.Promotores;
import com.mycompany.fianza.entidades.Listas;
import com.mycompany.fianza.entidades.ListasGrupo;
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
public class ListasGrupoJpaController implements Serializable {

    public ListasGrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ListasGrupo listasGrupo) {
        if (listasGrupo.getDetallePersonasSet() == null) {
            listasGrupo.setDetallePersonasSet(new HashSet<DetallePersonas>());
        }
        if (listasGrupo.getDetallePersonasSet1() == null) {
            listasGrupo.setDetallePersonasSet1(new HashSet<DetallePersonas>());
        }
        if (listasGrupo.getUsuariosSet() == null) {
            listasGrupo.setUsuariosSet(new HashSet<Usuarios>());
        }
        if (listasGrupo.getPersonasSet() == null) {
            listasGrupo.setPersonasSet(new HashSet<Personas>());
        }
        if (listasGrupo.getSolicitudesSet() == null) {
            listasGrupo.setSolicitudesSet(new HashSet<Solicitudes>());
        }
        if (listasGrupo.getDocumentosSet() == null) {
            listasGrupo.setDocumentosSet(new HashSet<Documentos>());
        }
        if (listasGrupo.getPromotoresSet() == null) {
            listasGrupo.setPromotoresSet(new HashSet<Promotores>());
        }
        if (listasGrupo.getListasSet() == null) {
            listasGrupo.setListasSet(new HashSet<Listas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<DetallePersonas> attachedDetallePersonasSet = new HashSet<DetallePersonas>();
            for (DetallePersonas detallePersonasSetDetallePersonasToAttach : listasGrupo.getDetallePersonasSet()) {
                detallePersonasSetDetallePersonasToAttach = em.getReference(detallePersonasSetDetallePersonasToAttach.getClass(), detallePersonasSetDetallePersonasToAttach.getId());
                attachedDetallePersonasSet.add(detallePersonasSetDetallePersonasToAttach);
            }
            listasGrupo.setDetallePersonasSet(attachedDetallePersonasSet);
            Set<DetallePersonas> attachedDetallePersonasSet1 = new HashSet<DetallePersonas>();
            for (DetallePersonas detallePersonasSet1DetallePersonasToAttach : listasGrupo.getDetallePersonasSet1()) {
                detallePersonasSet1DetallePersonasToAttach = em.getReference(detallePersonasSet1DetallePersonasToAttach.getClass(), detallePersonasSet1DetallePersonasToAttach.getId());
                attachedDetallePersonasSet1.add(detallePersonasSet1DetallePersonasToAttach);
            }
            listasGrupo.setDetallePersonasSet1(attachedDetallePersonasSet1);
            Set<Usuarios> attachedUsuariosSet = new HashSet<Usuarios>();
            for (Usuarios usuariosSetUsuariosToAttach : listasGrupo.getUsuariosSet()) {
                usuariosSetUsuariosToAttach = em.getReference(usuariosSetUsuariosToAttach.getClass(), usuariosSetUsuariosToAttach.getId());
                attachedUsuariosSet.add(usuariosSetUsuariosToAttach);
            }
            listasGrupo.setUsuariosSet(attachedUsuariosSet);
            Set<Personas> attachedPersonasSet = new HashSet<Personas>();
            for (Personas personasSetPersonasToAttach : listasGrupo.getPersonasSet()) {
                personasSetPersonasToAttach = em.getReference(personasSetPersonasToAttach.getClass(), personasSetPersonasToAttach.getId());
                attachedPersonasSet.add(personasSetPersonasToAttach);
            }
            listasGrupo.setPersonasSet(attachedPersonasSet);
            Set<Solicitudes> attachedSolicitudesSet = new HashSet<Solicitudes>();
            for (Solicitudes solicitudesSetSolicitudesToAttach : listasGrupo.getSolicitudesSet()) {
                solicitudesSetSolicitudesToAttach = em.getReference(solicitudesSetSolicitudesToAttach.getClass(), solicitudesSetSolicitudesToAttach.getId());
                attachedSolicitudesSet.add(solicitudesSetSolicitudesToAttach);
            }
            listasGrupo.setSolicitudesSet(attachedSolicitudesSet);
            Set<Documentos> attachedDocumentosSet = new HashSet<Documentos>();
            for (Documentos documentosSetDocumentosToAttach : listasGrupo.getDocumentosSet()) {
                documentosSetDocumentosToAttach = em.getReference(documentosSetDocumentosToAttach.getClass(), documentosSetDocumentosToAttach.getId());
                attachedDocumentosSet.add(documentosSetDocumentosToAttach);
            }
            listasGrupo.setDocumentosSet(attachedDocumentosSet);
            Set<Promotores> attachedPromotoresSet = new HashSet<Promotores>();
            for (Promotores promotoresSetPromotoresToAttach : listasGrupo.getPromotoresSet()) {
                promotoresSetPromotoresToAttach = em.getReference(promotoresSetPromotoresToAttach.getClass(), promotoresSetPromotoresToAttach.getId());
                attachedPromotoresSet.add(promotoresSetPromotoresToAttach);
            }
            listasGrupo.setPromotoresSet(attachedPromotoresSet);
            Set<Listas> attachedListasSet = new HashSet<Listas>();
            for (Listas listasSetListasToAttach : listasGrupo.getListasSet()) {
                listasSetListasToAttach = em.getReference(listasSetListasToAttach.getClass(), listasSetListasToAttach.getId());
                attachedListasSet.add(listasSetListasToAttach);
            }
            listasGrupo.setListasSet(attachedListasSet);
            em.persist(listasGrupo);
            for (DetallePersonas detallePersonasSetDetallePersonas : listasGrupo.getDetallePersonasSet()) {
                ListasGrupo oldEstadoOfDetallePersonasSetDetallePersonas = detallePersonasSetDetallePersonas.getEstado();
                detallePersonasSetDetallePersonas.setEstado(listasGrupo);
                detallePersonasSetDetallePersonas = em.merge(detallePersonasSetDetallePersonas);
                if (oldEstadoOfDetallePersonasSetDetallePersonas != null) {
                    oldEstadoOfDetallePersonasSetDetallePersonas.getDetallePersonasSet().remove(detallePersonasSetDetallePersonas);
                    oldEstadoOfDetallePersonasSetDetallePersonas = em.merge(oldEstadoOfDetallePersonasSetDetallePersonas);
                }
            }
            for (DetallePersonas detallePersonasSet1DetallePersonas : listasGrupo.getDetallePersonasSet1()) {
                ListasGrupo oldTipoPersonaOfDetallePersonasSet1DetallePersonas = detallePersonasSet1DetallePersonas.getTipoPersona();
                detallePersonasSet1DetallePersonas.setTipoPersona(listasGrupo);
                detallePersonasSet1DetallePersonas = em.merge(detallePersonasSet1DetallePersonas);
                if (oldTipoPersonaOfDetallePersonasSet1DetallePersonas != null) {
                    oldTipoPersonaOfDetallePersonasSet1DetallePersonas.getDetallePersonasSet1().remove(detallePersonasSet1DetallePersonas);
                    oldTipoPersonaOfDetallePersonasSet1DetallePersonas = em.merge(oldTipoPersonaOfDetallePersonasSet1DetallePersonas);
                }
            }
            for (Usuarios usuariosSetUsuarios : listasGrupo.getUsuariosSet()) {
                ListasGrupo oldTipodocumentoOfUsuariosSetUsuarios = usuariosSetUsuarios.getTipodocumento();
                usuariosSetUsuarios.setTipodocumento(listasGrupo);
                usuariosSetUsuarios = em.merge(usuariosSetUsuarios);
                if (oldTipodocumentoOfUsuariosSetUsuarios != null) {
                    oldTipodocumentoOfUsuariosSetUsuarios.getUsuariosSet().remove(usuariosSetUsuarios);
                    oldTipodocumentoOfUsuariosSetUsuarios = em.merge(oldTipodocumentoOfUsuariosSetUsuarios);
                }
            }
            for (Personas personasSetPersonas : listasGrupo.getPersonasSet()) {
                ListasGrupo oldTipoDocumentoOfPersonasSetPersonas = personasSetPersonas.getTipoDocumento();
                personasSetPersonas.setTipoDocumento(listasGrupo);
                personasSetPersonas = em.merge(personasSetPersonas);
                if (oldTipoDocumentoOfPersonasSetPersonas != null) {
                    oldTipoDocumentoOfPersonasSetPersonas.getPersonasSet().remove(personasSetPersonas);
                    oldTipoDocumentoOfPersonasSetPersonas = em.merge(oldTipoDocumentoOfPersonasSetPersonas);
                }
            }
            for (Solicitudes solicitudesSetSolicitudes : listasGrupo.getSolicitudesSet()) {
                ListasGrupo oldTipoInmuebleOfSolicitudesSetSolicitudes = solicitudesSetSolicitudes.getTipoInmueble();
                solicitudesSetSolicitudes.setTipoInmueble(listasGrupo);
                solicitudesSetSolicitudes = em.merge(solicitudesSetSolicitudes);
                if (oldTipoInmuebleOfSolicitudesSetSolicitudes != null) {
                    oldTipoInmuebleOfSolicitudesSetSolicitudes.getSolicitudesSet().remove(solicitudesSetSolicitudes);
                    oldTipoInmuebleOfSolicitudesSetSolicitudes = em.merge(oldTipoInmuebleOfSolicitudesSetSolicitudes);
                }
            }
            for (Documentos documentosSetDocumentos : listasGrupo.getDocumentosSet()) {
                ListasGrupo oldTipoDocumentoOfDocumentosSetDocumentos = documentosSetDocumentos.getTipoDocumento();
                documentosSetDocumentos.setTipoDocumento(listasGrupo);
                documentosSetDocumentos = em.merge(documentosSetDocumentos);
                if (oldTipoDocumentoOfDocumentosSetDocumentos != null) {
                    oldTipoDocumentoOfDocumentosSetDocumentos.getDocumentosSet().remove(documentosSetDocumentos);
                    oldTipoDocumentoOfDocumentosSetDocumentos = em.merge(oldTipoDocumentoOfDocumentosSetDocumentos);
                }
            }
            for (Promotores promotoresSetPromotores : listasGrupo.getPromotoresSet()) {
                ListasGrupo oldTipoDocumentoOfPromotoresSetPromotores = promotoresSetPromotores.getTipoDocumento();
                promotoresSetPromotores.setTipoDocumento(listasGrupo);
                promotoresSetPromotores = em.merge(promotoresSetPromotores);
                if (oldTipoDocumentoOfPromotoresSetPromotores != null) {
                    oldTipoDocumentoOfPromotoresSetPromotores.getPromotoresSet().remove(promotoresSetPromotores);
                    oldTipoDocumentoOfPromotoresSetPromotores = em.merge(oldTipoDocumentoOfPromotoresSetPromotores);
                }
            }
            for (Listas listasSetListas : listasGrupo.getListasSet()) {
                ListasGrupo oldIdListasgrupoOfListasSetListas = listasSetListas.getIdListasgrupo();
                listasSetListas.setIdListasgrupo(listasGrupo);
                listasSetListas = em.merge(listasSetListas);
                if (oldIdListasgrupoOfListasSetListas != null) {
                    oldIdListasgrupoOfListasSetListas.getListasSet().remove(listasSetListas);
                    oldIdListasgrupoOfListasSetListas = em.merge(oldIdListasgrupoOfListasSetListas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ListasGrupo listasGrupo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ListasGrupo persistentListasGrupo = em.find(ListasGrupo.class, listasGrupo.getId());
            Set<DetallePersonas> detallePersonasSetOld = persistentListasGrupo.getDetallePersonasSet();
            Set<DetallePersonas> detallePersonasSetNew = listasGrupo.getDetallePersonasSet();
            Set<DetallePersonas> detallePersonasSet1Old = persistentListasGrupo.getDetallePersonasSet1();
            Set<DetallePersonas> detallePersonasSet1New = listasGrupo.getDetallePersonasSet1();
            Set<Usuarios> usuariosSetOld = persistentListasGrupo.getUsuariosSet();
            Set<Usuarios> usuariosSetNew = listasGrupo.getUsuariosSet();
            Set<Personas> personasSetOld = persistentListasGrupo.getPersonasSet();
            Set<Personas> personasSetNew = listasGrupo.getPersonasSet();
            Set<Solicitudes> solicitudesSetOld = persistentListasGrupo.getSolicitudesSet();
            Set<Solicitudes> solicitudesSetNew = listasGrupo.getSolicitudesSet();
            Set<Documentos> documentosSetOld = persistentListasGrupo.getDocumentosSet();
            Set<Documentos> documentosSetNew = listasGrupo.getDocumentosSet();
            Set<Promotores> promotoresSetOld = persistentListasGrupo.getPromotoresSet();
            Set<Promotores> promotoresSetNew = listasGrupo.getPromotoresSet();
            Set<Listas> listasSetOld = persistentListasGrupo.getListasSet();
            Set<Listas> listasSetNew = listasGrupo.getListasSet();
            List<String> illegalOrphanMessages = null;
            for (DetallePersonas detallePersonasSetOldDetallePersonas : detallePersonasSetOld) {
                if (!detallePersonasSetNew.contains(detallePersonasSetOldDetallePersonas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetallePersonas " + detallePersonasSetOldDetallePersonas + " since its estado field is not nullable.");
                }
            }
            for (DetallePersonas detallePersonasSet1OldDetallePersonas : detallePersonasSet1Old) {
                if (!detallePersonasSet1New.contains(detallePersonasSet1OldDetallePersonas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetallePersonas " + detallePersonasSet1OldDetallePersonas + " since its tipoPersona field is not nullable.");
                }
            }
            for (Usuarios usuariosSetOldUsuarios : usuariosSetOld) {
                if (!usuariosSetNew.contains(usuariosSetOldUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuarios " + usuariosSetOldUsuarios + " since its tipodocumento field is not nullable.");
                }
            }
            for (Personas personasSetOldPersonas : personasSetOld) {
                if (!personasSetNew.contains(personasSetOldPersonas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Personas " + personasSetOldPersonas + " since its tipoDocumento field is not nullable.");
                }
            }
            for (Solicitudes solicitudesSetOldSolicitudes : solicitudesSetOld) {
                if (!solicitudesSetNew.contains(solicitudesSetOldSolicitudes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solicitudes " + solicitudesSetOldSolicitudes + " since its tipoInmueble field is not nullable.");
                }
            }
            for (Documentos documentosSetOldDocumentos : documentosSetOld) {
                if (!documentosSetNew.contains(documentosSetOldDocumentos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Documentos " + documentosSetOldDocumentos + " since its tipoDocumento field is not nullable.");
                }
            }
            for (Promotores promotoresSetOldPromotores : promotoresSetOld) {
                if (!promotoresSetNew.contains(promotoresSetOldPromotores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Promotores " + promotoresSetOldPromotores + " since its tipoDocumento field is not nullable.");
                }
            }
            for (Listas listasSetOldListas : listasSetOld) {
                if (!listasSetNew.contains(listasSetOldListas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Listas " + listasSetOldListas + " since its idListasgrupo field is not nullable.");
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
            listasGrupo.setDetallePersonasSet(detallePersonasSetNew);
            Set<DetallePersonas> attachedDetallePersonasSet1New = new HashSet<DetallePersonas>();
            for (DetallePersonas detallePersonasSet1NewDetallePersonasToAttach : detallePersonasSet1New) {
                detallePersonasSet1NewDetallePersonasToAttach = em.getReference(detallePersonasSet1NewDetallePersonasToAttach.getClass(), detallePersonasSet1NewDetallePersonasToAttach.getId());
                attachedDetallePersonasSet1New.add(detallePersonasSet1NewDetallePersonasToAttach);
            }
            detallePersonasSet1New = attachedDetallePersonasSet1New;
            listasGrupo.setDetallePersonasSet1(detallePersonasSet1New);
            Set<Usuarios> attachedUsuariosSetNew = new HashSet<Usuarios>();
            for (Usuarios usuariosSetNewUsuariosToAttach : usuariosSetNew) {
                usuariosSetNewUsuariosToAttach = em.getReference(usuariosSetNewUsuariosToAttach.getClass(), usuariosSetNewUsuariosToAttach.getId());
                attachedUsuariosSetNew.add(usuariosSetNewUsuariosToAttach);
            }
            usuariosSetNew = attachedUsuariosSetNew;
            listasGrupo.setUsuariosSet(usuariosSetNew);
            Set<Personas> attachedPersonasSetNew = new HashSet<Personas>();
            for (Personas personasSetNewPersonasToAttach : personasSetNew) {
                personasSetNewPersonasToAttach = em.getReference(personasSetNewPersonasToAttach.getClass(), personasSetNewPersonasToAttach.getId());
                attachedPersonasSetNew.add(personasSetNewPersonasToAttach);
            }
            personasSetNew = attachedPersonasSetNew;
            listasGrupo.setPersonasSet(personasSetNew);
            Set<Solicitudes> attachedSolicitudesSetNew = new HashSet<Solicitudes>();
            for (Solicitudes solicitudesSetNewSolicitudesToAttach : solicitudesSetNew) {
                solicitudesSetNewSolicitudesToAttach = em.getReference(solicitudesSetNewSolicitudesToAttach.getClass(), solicitudesSetNewSolicitudesToAttach.getId());
                attachedSolicitudesSetNew.add(solicitudesSetNewSolicitudesToAttach);
            }
            solicitudesSetNew = attachedSolicitudesSetNew;
            listasGrupo.setSolicitudesSet(solicitudesSetNew);
            Set<Documentos> attachedDocumentosSetNew = new HashSet<Documentos>();
            for (Documentos documentosSetNewDocumentosToAttach : documentosSetNew) {
                documentosSetNewDocumentosToAttach = em.getReference(documentosSetNewDocumentosToAttach.getClass(), documentosSetNewDocumentosToAttach.getId());
                attachedDocumentosSetNew.add(documentosSetNewDocumentosToAttach);
            }
            documentosSetNew = attachedDocumentosSetNew;
            listasGrupo.setDocumentosSet(documentosSetNew);
            Set<Promotores> attachedPromotoresSetNew = new HashSet<Promotores>();
            for (Promotores promotoresSetNewPromotoresToAttach : promotoresSetNew) {
                promotoresSetNewPromotoresToAttach = em.getReference(promotoresSetNewPromotoresToAttach.getClass(), promotoresSetNewPromotoresToAttach.getId());
                attachedPromotoresSetNew.add(promotoresSetNewPromotoresToAttach);
            }
            promotoresSetNew = attachedPromotoresSetNew;
            listasGrupo.setPromotoresSet(promotoresSetNew);
            Set<Listas> attachedListasSetNew = new HashSet<Listas>();
            for (Listas listasSetNewListasToAttach : listasSetNew) {
                listasSetNewListasToAttach = em.getReference(listasSetNewListasToAttach.getClass(), listasSetNewListasToAttach.getId());
                attachedListasSetNew.add(listasSetNewListasToAttach);
            }
            listasSetNew = attachedListasSetNew;
            listasGrupo.setListasSet(listasSetNew);
            listasGrupo = em.merge(listasGrupo);
            for (DetallePersonas detallePersonasSetNewDetallePersonas : detallePersonasSetNew) {
                if (!detallePersonasSetOld.contains(detallePersonasSetNewDetallePersonas)) {
                    ListasGrupo oldEstadoOfDetallePersonasSetNewDetallePersonas = detallePersonasSetNewDetallePersonas.getEstado();
                    detallePersonasSetNewDetallePersonas.setEstado(listasGrupo);
                    detallePersonasSetNewDetallePersonas = em.merge(detallePersonasSetNewDetallePersonas);
                    if (oldEstadoOfDetallePersonasSetNewDetallePersonas != null && !oldEstadoOfDetallePersonasSetNewDetallePersonas.equals(listasGrupo)) {
                        oldEstadoOfDetallePersonasSetNewDetallePersonas.getDetallePersonasSet().remove(detallePersonasSetNewDetallePersonas);
                        oldEstadoOfDetallePersonasSetNewDetallePersonas = em.merge(oldEstadoOfDetallePersonasSetNewDetallePersonas);
                    }
                }
            }
            for (DetallePersonas detallePersonasSet1NewDetallePersonas : detallePersonasSet1New) {
                if (!detallePersonasSet1Old.contains(detallePersonasSet1NewDetallePersonas)) {
                    ListasGrupo oldTipoPersonaOfDetallePersonasSet1NewDetallePersonas = detallePersonasSet1NewDetallePersonas.getTipoPersona();
                    detallePersonasSet1NewDetallePersonas.setTipoPersona(listasGrupo);
                    detallePersonasSet1NewDetallePersonas = em.merge(detallePersonasSet1NewDetallePersonas);
                    if (oldTipoPersonaOfDetallePersonasSet1NewDetallePersonas != null && !oldTipoPersonaOfDetallePersonasSet1NewDetallePersonas.equals(listasGrupo)) {
                        oldTipoPersonaOfDetallePersonasSet1NewDetallePersonas.getDetallePersonasSet1().remove(detallePersonasSet1NewDetallePersonas);
                        oldTipoPersonaOfDetallePersonasSet1NewDetallePersonas = em.merge(oldTipoPersonaOfDetallePersonasSet1NewDetallePersonas);
                    }
                }
            }
            for (Usuarios usuariosSetNewUsuarios : usuariosSetNew) {
                if (!usuariosSetOld.contains(usuariosSetNewUsuarios)) {
                    ListasGrupo oldTipodocumentoOfUsuariosSetNewUsuarios = usuariosSetNewUsuarios.getTipodocumento();
                    usuariosSetNewUsuarios.setTipodocumento(listasGrupo);
                    usuariosSetNewUsuarios = em.merge(usuariosSetNewUsuarios);
                    if (oldTipodocumentoOfUsuariosSetNewUsuarios != null && !oldTipodocumentoOfUsuariosSetNewUsuarios.equals(listasGrupo)) {
                        oldTipodocumentoOfUsuariosSetNewUsuarios.getUsuariosSet().remove(usuariosSetNewUsuarios);
                        oldTipodocumentoOfUsuariosSetNewUsuarios = em.merge(oldTipodocumentoOfUsuariosSetNewUsuarios);
                    }
                }
            }
            for (Personas personasSetNewPersonas : personasSetNew) {
                if (!personasSetOld.contains(personasSetNewPersonas)) {
                    ListasGrupo oldTipoDocumentoOfPersonasSetNewPersonas = personasSetNewPersonas.getTipoDocumento();
                    personasSetNewPersonas.setTipoDocumento(listasGrupo);
                    personasSetNewPersonas = em.merge(personasSetNewPersonas);
                    if (oldTipoDocumentoOfPersonasSetNewPersonas != null && !oldTipoDocumentoOfPersonasSetNewPersonas.equals(listasGrupo)) {
                        oldTipoDocumentoOfPersonasSetNewPersonas.getPersonasSet().remove(personasSetNewPersonas);
                        oldTipoDocumentoOfPersonasSetNewPersonas = em.merge(oldTipoDocumentoOfPersonasSetNewPersonas);
                    }
                }
            }
            for (Solicitudes solicitudesSetNewSolicitudes : solicitudesSetNew) {
                if (!solicitudesSetOld.contains(solicitudesSetNewSolicitudes)) {
                    ListasGrupo oldTipoInmuebleOfSolicitudesSetNewSolicitudes = solicitudesSetNewSolicitudes.getTipoInmueble();
                    solicitudesSetNewSolicitudes.setTipoInmueble(listasGrupo);
                    solicitudesSetNewSolicitudes = em.merge(solicitudesSetNewSolicitudes);
                    if (oldTipoInmuebleOfSolicitudesSetNewSolicitudes != null && !oldTipoInmuebleOfSolicitudesSetNewSolicitudes.equals(listasGrupo)) {
                        oldTipoInmuebleOfSolicitudesSetNewSolicitudes.getSolicitudesSet().remove(solicitudesSetNewSolicitudes);
                        oldTipoInmuebleOfSolicitudesSetNewSolicitudes = em.merge(oldTipoInmuebleOfSolicitudesSetNewSolicitudes);
                    }
                }
            }
            for (Documentos documentosSetNewDocumentos : documentosSetNew) {
                if (!documentosSetOld.contains(documentosSetNewDocumentos)) {
                    ListasGrupo oldTipoDocumentoOfDocumentosSetNewDocumentos = documentosSetNewDocumentos.getTipoDocumento();
                    documentosSetNewDocumentos.setTipoDocumento(listasGrupo);
                    documentosSetNewDocumentos = em.merge(documentosSetNewDocumentos);
                    if (oldTipoDocumentoOfDocumentosSetNewDocumentos != null && !oldTipoDocumentoOfDocumentosSetNewDocumentos.equals(listasGrupo)) {
                        oldTipoDocumentoOfDocumentosSetNewDocumentos.getDocumentosSet().remove(documentosSetNewDocumentos);
                        oldTipoDocumentoOfDocumentosSetNewDocumentos = em.merge(oldTipoDocumentoOfDocumentosSetNewDocumentos);
                    }
                }
            }
            for (Promotores promotoresSetNewPromotores : promotoresSetNew) {
                if (!promotoresSetOld.contains(promotoresSetNewPromotores)) {
                    ListasGrupo oldTipoDocumentoOfPromotoresSetNewPromotores = promotoresSetNewPromotores.getTipoDocumento();
                    promotoresSetNewPromotores.setTipoDocumento(listasGrupo);
                    promotoresSetNewPromotores = em.merge(promotoresSetNewPromotores);
                    if (oldTipoDocumentoOfPromotoresSetNewPromotores != null && !oldTipoDocumentoOfPromotoresSetNewPromotores.equals(listasGrupo)) {
                        oldTipoDocumentoOfPromotoresSetNewPromotores.getPromotoresSet().remove(promotoresSetNewPromotores);
                        oldTipoDocumentoOfPromotoresSetNewPromotores = em.merge(oldTipoDocumentoOfPromotoresSetNewPromotores);
                    }
                }
            }
            for (Listas listasSetNewListas : listasSetNew) {
                if (!listasSetOld.contains(listasSetNewListas)) {
                    ListasGrupo oldIdListasgrupoOfListasSetNewListas = listasSetNewListas.getIdListasgrupo();
                    listasSetNewListas.setIdListasgrupo(listasGrupo);
                    listasSetNewListas = em.merge(listasSetNewListas);
                    if (oldIdListasgrupoOfListasSetNewListas != null && !oldIdListasgrupoOfListasSetNewListas.equals(listasGrupo)) {
                        oldIdListasgrupoOfListasSetNewListas.getListasSet().remove(listasSetNewListas);
                        oldIdListasgrupoOfListasSetNewListas = em.merge(oldIdListasgrupoOfListasSetNewListas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = listasGrupo.getId();
                if (findListasGrupo(id) == null) {
                    throw new NonexistentEntityException("The listasGrupo with id " + id + " no longer exists.");
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
            ListasGrupo listasGrupo;
            try {
                listasGrupo = em.getReference(ListasGrupo.class, id);
                listasGrupo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The listasGrupo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<DetallePersonas> detallePersonasSetOrphanCheck = listasGrupo.getDetallePersonasSet();
            for (DetallePersonas detallePersonasSetOrphanCheckDetallePersonas : detallePersonasSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ListasGrupo (" + listasGrupo + ") cannot be destroyed since the DetallePersonas " + detallePersonasSetOrphanCheckDetallePersonas + " in its detallePersonasSet field has a non-nullable estado field.");
            }
            Set<DetallePersonas> detallePersonasSet1OrphanCheck = listasGrupo.getDetallePersonasSet1();
            for (DetallePersonas detallePersonasSet1OrphanCheckDetallePersonas : detallePersonasSet1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ListasGrupo (" + listasGrupo + ") cannot be destroyed since the DetallePersonas " + detallePersonasSet1OrphanCheckDetallePersonas + " in its detallePersonasSet1 field has a non-nullable tipoPersona field.");
            }
            Set<Usuarios> usuariosSetOrphanCheck = listasGrupo.getUsuariosSet();
            for (Usuarios usuariosSetOrphanCheckUsuarios : usuariosSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ListasGrupo (" + listasGrupo + ") cannot be destroyed since the Usuarios " + usuariosSetOrphanCheckUsuarios + " in its usuariosSet field has a non-nullable tipodocumento field.");
            }
            Set<Personas> personasSetOrphanCheck = listasGrupo.getPersonasSet();
            for (Personas personasSetOrphanCheckPersonas : personasSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ListasGrupo (" + listasGrupo + ") cannot be destroyed since the Personas " + personasSetOrphanCheckPersonas + " in its personasSet field has a non-nullable tipoDocumento field.");
            }
            Set<Solicitudes> solicitudesSetOrphanCheck = listasGrupo.getSolicitudesSet();
            for (Solicitudes solicitudesSetOrphanCheckSolicitudes : solicitudesSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ListasGrupo (" + listasGrupo + ") cannot be destroyed since the Solicitudes " + solicitudesSetOrphanCheckSolicitudes + " in its solicitudesSet field has a non-nullable tipoInmueble field.");
            }
            Set<Documentos> documentosSetOrphanCheck = listasGrupo.getDocumentosSet();
            for (Documentos documentosSetOrphanCheckDocumentos : documentosSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ListasGrupo (" + listasGrupo + ") cannot be destroyed since the Documentos " + documentosSetOrphanCheckDocumentos + " in its documentosSet field has a non-nullable tipoDocumento field.");
            }
            Set<Promotores> promotoresSetOrphanCheck = listasGrupo.getPromotoresSet();
            for (Promotores promotoresSetOrphanCheckPromotores : promotoresSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ListasGrupo (" + listasGrupo + ") cannot be destroyed since the Promotores " + promotoresSetOrphanCheckPromotores + " in its promotoresSet field has a non-nullable tipoDocumento field.");
            }
            Set<Listas> listasSetOrphanCheck = listasGrupo.getListasSet();
            for (Listas listasSetOrphanCheckListas : listasSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ListasGrupo (" + listasGrupo + ") cannot be destroyed since the Listas " + listasSetOrphanCheckListas + " in its listasSet field has a non-nullable idListasgrupo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(listasGrupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ListasGrupo> findListasGrupoEntities() {
        return findListasGrupoEntities(true, -1, -1);
    }

    public List<ListasGrupo> findListasGrupoEntities(int maxResults, int firstResult) {
        return findListasGrupoEntities(false, maxResults, firstResult);
    }

    private List<ListasGrupo> findListasGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListasGrupo.class));
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

    public ListasGrupo findListasGrupo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListasGrupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getListasGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ListasGrupo> rt = cq.from(ListasGrupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
