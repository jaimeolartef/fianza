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
import com.mycompany.fianza.entidades.Menus;
import com.mycompany.fianza.entidades.Permisos;
import com.mycompany.fianza.entidades.Roles;
import com.mycompany.fianza.entidades.Usuarios;
import com.mycompany.fianza.facade.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author dianaplata
 */
public class PermisosJpaController implements Serializable {

    public PermisosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Permisos permisos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Menus idMenu = permisos.getIdMenu();
            if (idMenu != null) {
                idMenu = em.getReference(idMenu.getClass(), idMenu.getId());
                permisos.setIdMenu(idMenu);
            }
            Roles idRol = permisos.getIdRol();
            if (idRol != null) {
                idRol = em.getReference(idRol.getClass(), idRol.getId());
                permisos.setIdRol(idRol);
            }
            Usuarios idUsuario = permisos.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getId());
                permisos.setIdUsuario(idUsuario);
            }
            em.persist(permisos);
            if (idMenu != null) {
                idMenu.getPermisosSet().add(permisos);
                idMenu = em.merge(idMenu);
            }
            if (idRol != null) {
                idRol.getPermisosSet().add(permisos);
                idRol = em.merge(idRol);
            }
            if (idUsuario != null) {
                idUsuario.getPermisosSet().add(permisos);
                idUsuario = em.merge(idUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Permisos permisos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Permisos persistentPermisos = em.find(Permisos.class, permisos.getId());
            Menus idMenuOld = persistentPermisos.getIdMenu();
            Menus idMenuNew = permisos.getIdMenu();
            Roles idRolOld = persistentPermisos.getIdRol();
            Roles idRolNew = permisos.getIdRol();
            Usuarios idUsuarioOld = persistentPermisos.getIdUsuario();
            Usuarios idUsuarioNew = permisos.getIdUsuario();
            if (idMenuNew != null) {
                idMenuNew = em.getReference(idMenuNew.getClass(), idMenuNew.getId());
                permisos.setIdMenu(idMenuNew);
            }
            if (idRolNew != null) {
                idRolNew = em.getReference(idRolNew.getClass(), idRolNew.getId());
                permisos.setIdRol(idRolNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getId());
                permisos.setIdUsuario(idUsuarioNew);
            }
            permisos = em.merge(permisos);
            if (idMenuOld != null && !idMenuOld.equals(idMenuNew)) {
                idMenuOld.getPermisosSet().remove(permisos);
                idMenuOld = em.merge(idMenuOld);
            }
            if (idMenuNew != null && !idMenuNew.equals(idMenuOld)) {
                idMenuNew.getPermisosSet().add(permisos);
                idMenuNew = em.merge(idMenuNew);
            }
            if (idRolOld != null && !idRolOld.equals(idRolNew)) {
                idRolOld.getPermisosSet().remove(permisos);
                idRolOld = em.merge(idRolOld);
            }
            if (idRolNew != null && !idRolNew.equals(idRolOld)) {
                idRolNew.getPermisosSet().add(permisos);
                idRolNew = em.merge(idRolNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getPermisosSet().remove(permisos);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getPermisosSet().add(permisos);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = permisos.getId();
                if (findPermisos(id) == null) {
                    throw new NonexistentEntityException("The permisos with id " + id + " no longer exists.");
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
            Permisos permisos;
            try {
                permisos = em.getReference(Permisos.class, id);
                permisos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The permisos with id " + id + " no longer exists.", enfe);
            }
            Menus idMenu = permisos.getIdMenu();
            if (idMenu != null) {
                idMenu.getPermisosSet().remove(permisos);
                idMenu = em.merge(idMenu);
            }
            Roles idRol = permisos.getIdRol();
            if (idRol != null) {
                idRol.getPermisosSet().remove(permisos);
                idRol = em.merge(idRol);
            }
            Usuarios idUsuario = permisos.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getPermisosSet().remove(permisos);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(permisos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Permisos> findPermisosEntities() {
        return findPermisosEntities(true, -1, -1);
    }

    public List<Permisos> findPermisosEntities(int maxResults, int firstResult) {
        return findPermisosEntities(false, maxResults, firstResult);
    }

    private List<Permisos> findPermisosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Permisos.class));
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

    public Permisos findPermisos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Permisos.class, id);
        } finally {
            em.close();
        }
    }

    public int getPermisosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Permisos> rt = cq.from(Permisos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
