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
import com.mycompany.fianza.entidades.Permisos;
import com.mycompany.fianza.entidades.Roles;
import java.util.HashSet;
import java.util.Set;
import com.mycompany.fianza.entidades.Usuarios;
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
public class RolesJpaController implements Serializable {

    public RolesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Roles roles) {
        if (roles.getPermisosSet() == null) {
            roles.setPermisosSet(new HashSet<Permisos>());
        }
        if (roles.getUsuariosSet() == null) {
            roles.setUsuariosSet(new HashSet<Usuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<Permisos> attachedPermisosSet = new HashSet<Permisos>();
            for (Permisos permisosSetPermisosToAttach : roles.getPermisosSet()) {
                permisosSetPermisosToAttach = em.getReference(permisosSetPermisosToAttach.getClass(), permisosSetPermisosToAttach.getId());
                attachedPermisosSet.add(permisosSetPermisosToAttach);
            }
            roles.setPermisosSet(attachedPermisosSet);
            Set<Usuarios> attachedUsuariosSet = new HashSet<Usuarios>();
            for (Usuarios usuariosSetUsuariosToAttach : roles.getUsuariosSet()) {
                usuariosSetUsuariosToAttach = em.getReference(usuariosSetUsuariosToAttach.getClass(), usuariosSetUsuariosToAttach.getId());
                attachedUsuariosSet.add(usuariosSetUsuariosToAttach);
            }
            roles.setUsuariosSet(attachedUsuariosSet);
            em.persist(roles);
            for (Permisos permisosSetPermisos : roles.getPermisosSet()) {
                Roles oldIdRolOfPermisosSetPermisos = permisosSetPermisos.getIdRol();
                permisosSetPermisos.setIdRol(roles);
                permisosSetPermisos = em.merge(permisosSetPermisos);
                if (oldIdRolOfPermisosSetPermisos != null) {
                    oldIdRolOfPermisosSetPermisos.getPermisosSet().remove(permisosSetPermisos);
                    oldIdRolOfPermisosSetPermisos = em.merge(oldIdRolOfPermisosSetPermisos);
                }
            }
            for (Usuarios usuariosSetUsuarios : roles.getUsuariosSet()) {
                Roles oldIdRolOfUsuariosSetUsuarios = usuariosSetUsuarios.getIdRol();
                usuariosSetUsuarios.setIdRol(roles);
                usuariosSetUsuarios = em.merge(usuariosSetUsuarios);
                if (oldIdRolOfUsuariosSetUsuarios != null) {
                    oldIdRolOfUsuariosSetUsuarios.getUsuariosSet().remove(usuariosSetUsuarios);
                    oldIdRolOfUsuariosSetUsuarios = em.merge(oldIdRolOfUsuariosSetUsuarios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Roles roles) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Roles persistentRoles = em.find(Roles.class, roles.getId());
            Set<Permisos> permisosSetOld = persistentRoles.getPermisosSet();
            Set<Permisos> permisosSetNew = roles.getPermisosSet();
            Set<Usuarios> usuariosSetOld = persistentRoles.getUsuariosSet();
            Set<Usuarios> usuariosSetNew = roles.getUsuariosSet();
            List<String> illegalOrphanMessages = null;
            for (Permisos permisosSetOldPermisos : permisosSetOld) {
                if (!permisosSetNew.contains(permisosSetOldPermisos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Permisos " + permisosSetOldPermisos + " since its idRol field is not nullable.");
                }
            }
            for (Usuarios usuariosSetOldUsuarios : usuariosSetOld) {
                if (!usuariosSetNew.contains(usuariosSetOldUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuarios " + usuariosSetOldUsuarios + " since its idRol field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<Permisos> attachedPermisosSetNew = new HashSet<Permisos>();
            for (Permisos permisosSetNewPermisosToAttach : permisosSetNew) {
                permisosSetNewPermisosToAttach = em.getReference(permisosSetNewPermisosToAttach.getClass(), permisosSetNewPermisosToAttach.getId());
                attachedPermisosSetNew.add(permisosSetNewPermisosToAttach);
            }
            permisosSetNew = attachedPermisosSetNew;
            roles.setPermisosSet(permisosSetNew);
            Set<Usuarios> attachedUsuariosSetNew = new HashSet<Usuarios>();
            for (Usuarios usuariosSetNewUsuariosToAttach : usuariosSetNew) {
                usuariosSetNewUsuariosToAttach = em.getReference(usuariosSetNewUsuariosToAttach.getClass(), usuariosSetNewUsuariosToAttach.getId());
                attachedUsuariosSetNew.add(usuariosSetNewUsuariosToAttach);
            }
            usuariosSetNew = attachedUsuariosSetNew;
            roles.setUsuariosSet(usuariosSetNew);
            roles = em.merge(roles);
            for (Permisos permisosSetNewPermisos : permisosSetNew) {
                if (!permisosSetOld.contains(permisosSetNewPermisos)) {
                    Roles oldIdRolOfPermisosSetNewPermisos = permisosSetNewPermisos.getIdRol();
                    permisosSetNewPermisos.setIdRol(roles);
                    permisosSetNewPermisos = em.merge(permisosSetNewPermisos);
                    if (oldIdRolOfPermisosSetNewPermisos != null && !oldIdRolOfPermisosSetNewPermisos.equals(roles)) {
                        oldIdRolOfPermisosSetNewPermisos.getPermisosSet().remove(permisosSetNewPermisos);
                        oldIdRolOfPermisosSetNewPermisos = em.merge(oldIdRolOfPermisosSetNewPermisos);
                    }
                }
            }
            for (Usuarios usuariosSetNewUsuarios : usuariosSetNew) {
                if (!usuariosSetOld.contains(usuariosSetNewUsuarios)) {
                    Roles oldIdRolOfUsuariosSetNewUsuarios = usuariosSetNewUsuarios.getIdRol();
                    usuariosSetNewUsuarios.setIdRol(roles);
                    usuariosSetNewUsuarios = em.merge(usuariosSetNewUsuarios);
                    if (oldIdRolOfUsuariosSetNewUsuarios != null && !oldIdRolOfUsuariosSetNewUsuarios.equals(roles)) {
                        oldIdRolOfUsuariosSetNewUsuarios.getUsuariosSet().remove(usuariosSetNewUsuarios);
                        oldIdRolOfUsuariosSetNewUsuarios = em.merge(oldIdRolOfUsuariosSetNewUsuarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = roles.getId();
                if (findRoles(id) == null) {
                    throw new NonexistentEntityException("The roles with id " + id + " no longer exists.");
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
            Roles roles;
            try {
                roles = em.getReference(Roles.class, id);
                roles.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The roles with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<Permisos> permisosSetOrphanCheck = roles.getPermisosSet();
            for (Permisos permisosSetOrphanCheckPermisos : permisosSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Roles (" + roles + ") cannot be destroyed since the Permisos " + permisosSetOrphanCheckPermisos + " in its permisosSet field has a non-nullable idRol field.");
            }
            Set<Usuarios> usuariosSetOrphanCheck = roles.getUsuariosSet();
            for (Usuarios usuariosSetOrphanCheckUsuarios : usuariosSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Roles (" + roles + ") cannot be destroyed since the Usuarios " + usuariosSetOrphanCheckUsuarios + " in its usuariosSet field has a non-nullable idRol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(roles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Roles> findRolesEntities() {
        return findRolesEntities(true, -1, -1);
    }

    public List<Roles> findRolesEntities(int maxResults, int firstResult) {
        return findRolesEntities(false, maxResults, firstResult);
    }

    private List<Roles> findRolesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Roles.class));
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

    public Roles findRoles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Roles.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Roles> rt = cq.from(Roles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
