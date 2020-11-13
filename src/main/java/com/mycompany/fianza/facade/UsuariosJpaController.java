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
import com.mycompany.fianza.entidades.Roles;
import com.mycompany.fianza.entidades.Permisos;
import java.util.HashSet;
import java.util.Set;
import com.mycompany.fianza.entidades.Promotores;
import com.mycompany.fianza.entidades.Usuarios;
import com.mycompany.fianza.facade.exceptions.IllegalOrphanException;
import com.mycompany.fianza.facade.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 *
 * @author dianaplata
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    EntityManager em = null;
    
    
    public Usuarios getUsuario(String codigo) {
        Usuarios usuario = new Usuarios();

        try {           
            Query resultado = getEntityManager().createNamedQuery("Usuarios.findByCodigo", Usuarios.class);
            resultado.setParameter("codUsua", codigo);
            usuario = (Usuarios) resultado.getSingleResult();

        } catch (NoResultException ex) {
            System.out.println(ex.toString());
            return null;
        }

        return usuario;
    }
    
    public Usuarios getUsuarioByCorreo(String correo) {
        Usuarios usuario = new Usuarios();

        try {           
            Query resultado = getEntityManager().createNamedQuery("Usuarios.findByCorreo", Usuarios.class);
            resultado.setParameter("correo", correo);
            usuario = (Usuarios) resultado.getSingleResult();

        } catch (NoResultException ex) {
            System.out.println(ex.toString());
            return null;
        }

        return usuario;
    }
    
     public Usuarios getUsuarioByDocumento(String documento) {
        
        Usuarios usuario = new Usuarios();
        try {
            Query resultado = getEntityManager().createNamedQuery("Usuarios.findByIdentificacion", Usuarios.class);
            resultado.setParameter("idDocumento", documento);
            usuario = (Usuarios) resultado.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        
        return usuario;
    }
     
    public Boolean guardar(Usuarios usuario) {
        Boolean guardar = false;

        if (usuario != null) {
            em.persist(usuario);
            guardar = true;
        }

        return guardar;
    }

    public Boolean actualizar(Usuarios usuario) {
        Boolean actualizar = false;

        if (usuario != null) {
            em.merge(usuario);
            actualizar = true;
        }

        return actualizar;
    } 

    public void create(Usuarios usuarios) {
        if (usuarios.getPermisosSet() == null) {
            usuarios.setPermisosSet(new HashSet<Permisos>());
        }
        if (usuarios.getPromotoresSet() == null) {
            usuarios.setPromotoresSet(new HashSet<Promotores>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inmobiliarias idInmobiliaria = usuarios.getIdInmobiliaria();
            if (idInmobiliaria != null) {
                idInmobiliaria = em.getReference(idInmobiliaria.getClass(), idInmobiliaria.getId());
                usuarios.setIdInmobiliaria(idInmobiliaria);
            }
            ListasGrupo tipodocumento = usuarios.getTipodocumento();
            if (tipodocumento != null) {
                tipodocumento = em.getReference(tipodocumento.getClass(), tipodocumento.getId());
                usuarios.setTipodocumento(tipodocumento);
            }
            Roles idRol = usuarios.getIdRol();
            if (idRol != null) {
                idRol = em.getReference(idRol.getClass(), idRol.getId());
                usuarios.setIdRol(idRol);
            }
            Set<Permisos> attachedPermisosSet = new HashSet<Permisos>();
            for (Permisos permisosSetPermisosToAttach : usuarios.getPermisosSet()) {
                permisosSetPermisosToAttach = em.getReference(permisosSetPermisosToAttach.getClass(), permisosSetPermisosToAttach.getId());
                attachedPermisosSet.add(permisosSetPermisosToAttach);
            }
            usuarios.setPermisosSet(attachedPermisosSet);
            Set<Promotores> attachedPromotoresSet = new HashSet<Promotores>();
            for (Promotores promotoresSetPromotoresToAttach : usuarios.getPromotoresSet()) {
                promotoresSetPromotoresToAttach = em.getReference(promotoresSetPromotoresToAttach.getClass(), promotoresSetPromotoresToAttach.getId());
                attachedPromotoresSet.add(promotoresSetPromotoresToAttach);
            }
            usuarios.setPromotoresSet(attachedPromotoresSet);
            em.persist(usuarios);
            if (idInmobiliaria != null) {
                idInmobiliaria.getUsuariosSet().add(usuarios);
                idInmobiliaria = em.merge(idInmobiliaria);
            }
            if (tipodocumento != null) {
                tipodocumento.getUsuariosSet().add(usuarios);
                tipodocumento = em.merge(tipodocumento);
            }
            if (idRol != null) {
                idRol.getUsuariosSet().add(usuarios);
                idRol = em.merge(idRol);
            }
            for (Permisos permisosSetPermisos : usuarios.getPermisosSet()) {
                Usuarios oldIdUsuarioOfPermisosSetPermisos = permisosSetPermisos.getIdUsuario();
                permisosSetPermisos.setIdUsuario(usuarios);
                permisosSetPermisos = em.merge(permisosSetPermisos);
                if (oldIdUsuarioOfPermisosSetPermisos != null) {
                    oldIdUsuarioOfPermisosSetPermisos.getPermisosSet().remove(permisosSetPermisos);
                    oldIdUsuarioOfPermisosSetPermisos = em.merge(oldIdUsuarioOfPermisosSetPermisos);
                }
            }
            for (Promotores promotoresSetPromotores : usuarios.getPromotoresSet()) {
                Usuarios oldIdUsuarioOfPromotoresSetPromotores = promotoresSetPromotores.getIdUsuario();
                promotoresSetPromotores.setIdUsuario(usuarios);
                promotoresSetPromotores = em.merge(promotoresSetPromotores);
                if (oldIdUsuarioOfPromotoresSetPromotores != null) {
                    oldIdUsuarioOfPromotoresSetPromotores.getPromotoresSet().remove(promotoresSetPromotores);
                    oldIdUsuarioOfPromotoresSetPromotores = em.merge(oldIdUsuarioOfPromotoresSetPromotores);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    

    public void edit(Usuarios usuarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getId());
            Inmobiliarias idInmobiliariaOld = persistentUsuarios.getIdInmobiliaria();
            Inmobiliarias idInmobiliariaNew = usuarios.getIdInmobiliaria();
            ListasGrupo tipodocumentoOld = persistentUsuarios.getTipodocumento();
            ListasGrupo tipodocumentoNew = usuarios.getTipodocumento();
            Roles idRolOld = persistentUsuarios.getIdRol();
            Roles idRolNew = usuarios.getIdRol();
            Set<Permisos> permisosSetOld = persistentUsuarios.getPermisosSet();
            Set<Permisos> permisosSetNew = usuarios.getPermisosSet();
            Set<Promotores> promotoresSetOld = persistentUsuarios.getPromotoresSet();
            Set<Promotores> promotoresSetNew = usuarios.getPromotoresSet();
            List<String> illegalOrphanMessages = null;
            for (Permisos permisosSetOldPermisos : permisosSetOld) {
                if (!permisosSetNew.contains(permisosSetOldPermisos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Permisos " + permisosSetOldPermisos + " since its idUsuario field is not nullable.");
                }
            }
            for (Promotores promotoresSetOldPromotores : promotoresSetOld) {
                if (!promotoresSetNew.contains(promotoresSetOldPromotores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Promotores " + promotoresSetOldPromotores + " since its idUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idInmobiliariaNew != null) {
                idInmobiliariaNew = em.getReference(idInmobiliariaNew.getClass(), idInmobiliariaNew.getId());
                usuarios.setIdInmobiliaria(idInmobiliariaNew);
            }
            if (tipodocumentoNew != null) {
                tipodocumentoNew = em.getReference(tipodocumentoNew.getClass(), tipodocumentoNew.getId());
                usuarios.setTipodocumento(tipodocumentoNew);
            }
            if (idRolNew != null) {
                idRolNew = em.getReference(idRolNew.getClass(), idRolNew.getId());
                usuarios.setIdRol(idRolNew);
            }
            Set<Permisos> attachedPermisosSetNew = new HashSet<Permisos>();
            for (Permisos permisosSetNewPermisosToAttach : permisosSetNew) {
                permisosSetNewPermisosToAttach = em.getReference(permisosSetNewPermisosToAttach.getClass(), permisosSetNewPermisosToAttach.getId());
                attachedPermisosSetNew.add(permisosSetNewPermisosToAttach);
            }
            permisosSetNew = attachedPermisosSetNew;
            usuarios.setPermisosSet(permisosSetNew);
            Set<Promotores> attachedPromotoresSetNew = new HashSet<Promotores>();
            for (Promotores promotoresSetNewPromotoresToAttach : promotoresSetNew) {
                promotoresSetNewPromotoresToAttach = em.getReference(promotoresSetNewPromotoresToAttach.getClass(), promotoresSetNewPromotoresToAttach.getId());
                attachedPromotoresSetNew.add(promotoresSetNewPromotoresToAttach);
            }
            promotoresSetNew = attachedPromotoresSetNew;
            usuarios.setPromotoresSet(promotoresSetNew);
            usuarios = em.merge(usuarios);
            if (idInmobiliariaOld != null && !idInmobiliariaOld.equals(idInmobiliariaNew)) {
                idInmobiliariaOld.getUsuariosSet().remove(usuarios);
                idInmobiliariaOld = em.merge(idInmobiliariaOld);
            }
            if (idInmobiliariaNew != null && !idInmobiliariaNew.equals(idInmobiliariaOld)) {
                idInmobiliariaNew.getUsuariosSet().add(usuarios);
                idInmobiliariaNew = em.merge(idInmobiliariaNew);
            }
            if (tipodocumentoOld != null && !tipodocumentoOld.equals(tipodocumentoNew)) {
                tipodocumentoOld.getUsuariosSet().remove(usuarios);
                tipodocumentoOld = em.merge(tipodocumentoOld);
            }
            if (tipodocumentoNew != null && !tipodocumentoNew.equals(tipodocumentoOld)) {
                tipodocumentoNew.getUsuariosSet().add(usuarios);
                tipodocumentoNew = em.merge(tipodocumentoNew);
            }
            if (idRolOld != null && !idRolOld.equals(idRolNew)) {
                idRolOld.getUsuariosSet().remove(usuarios);
                idRolOld = em.merge(idRolOld);
            }
            if (idRolNew != null && !idRolNew.equals(idRolOld)) {
                idRolNew.getUsuariosSet().add(usuarios);
                idRolNew = em.merge(idRolNew);
            }
            for (Permisos permisosSetNewPermisos : permisosSetNew) {
                if (!permisosSetOld.contains(permisosSetNewPermisos)) {
                    Usuarios oldIdUsuarioOfPermisosSetNewPermisos = permisosSetNewPermisos.getIdUsuario();
                    permisosSetNewPermisos.setIdUsuario(usuarios);
                    permisosSetNewPermisos = em.merge(permisosSetNewPermisos);
                    if (oldIdUsuarioOfPermisosSetNewPermisos != null && !oldIdUsuarioOfPermisosSetNewPermisos.equals(usuarios)) {
                        oldIdUsuarioOfPermisosSetNewPermisos.getPermisosSet().remove(permisosSetNewPermisos);
                        oldIdUsuarioOfPermisosSetNewPermisos = em.merge(oldIdUsuarioOfPermisosSetNewPermisos);
                    }
                }
            }
            for (Promotores promotoresSetNewPromotores : promotoresSetNew) {
                if (!promotoresSetOld.contains(promotoresSetNewPromotores)) {
                    Usuarios oldIdUsuarioOfPromotoresSetNewPromotores = promotoresSetNewPromotores.getIdUsuario();
                    promotoresSetNewPromotores.setIdUsuario(usuarios);
                    promotoresSetNewPromotores = em.merge(promotoresSetNewPromotores);
                    if (oldIdUsuarioOfPromotoresSetNewPromotores != null && !oldIdUsuarioOfPromotoresSetNewPromotores.equals(usuarios)) {
                        oldIdUsuarioOfPromotoresSetNewPromotores.getPromotoresSet().remove(promotoresSetNewPromotores);
                        oldIdUsuarioOfPromotoresSetNewPromotores = em.merge(oldIdUsuarioOfPromotoresSetNewPromotores);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getId();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<Permisos> permisosSetOrphanCheck = usuarios.getPermisosSet();
            for (Permisos permisosSetOrphanCheckPermisos : permisosSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Permisos " + permisosSetOrphanCheckPermisos + " in its permisosSet field has a non-nullable idUsuario field.");
            }
            Set<Promotores> promotoresSetOrphanCheck = usuarios.getPromotoresSet();
            for (Promotores promotoresSetOrphanCheckPromotores : promotoresSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Promotores " + promotoresSetOrphanCheckPromotores + " in its promotoresSet field has a non-nullable idUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Inmobiliarias idInmobiliaria = usuarios.getIdInmobiliaria();
            if (idInmobiliaria != null) {
                idInmobiliaria.getUsuariosSet().remove(usuarios);
                idInmobiliaria = em.merge(idInmobiliaria);
            }
            ListasGrupo tipodocumento = usuarios.getTipodocumento();
            if (tipodocumento != null) {
                tipodocumento.getUsuariosSet().remove(usuarios);
                tipodocumento = em.merge(tipodocumento);
            }
            Roles idRol = usuarios.getIdRol();
            if (idRol != null) {
                idRol.getUsuariosSet().remove(usuarios);
                idRol = em.merge(idRol);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
