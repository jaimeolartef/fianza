/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fianza.facade;

import com.mycompany.fianza.entidades.Menus;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.fianza.entidades.Permisos;
import com.mycompany.fianza.facade.exceptions.IllegalOrphanException;
import com.mycompany.fianza.facade.exceptions.NonexistentEntityException;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author dianaplata
 */
public class MenusJpaController implements Serializable {

    public MenusJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Menus menus) {
        if (menus.getPermisosSet() == null) {
            menus.setPermisosSet(new HashSet<Permisos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<Permisos> attachedPermisosSet = new HashSet<Permisos>();
            for (Permisos permisosSetPermisosToAttach : menus.getPermisosSet()) {
                permisosSetPermisosToAttach = em.getReference(permisosSetPermisosToAttach.getClass(), permisosSetPermisosToAttach.getId());
                attachedPermisosSet.add(permisosSetPermisosToAttach);
            }
            menus.setPermisosSet(attachedPermisosSet);
            em.persist(menus);
            for (Permisos permisosSetPermisos : menus.getPermisosSet()) {
                Menus oldIdMenuOfPermisosSetPermisos = permisosSetPermisos.getIdMenu();
                permisosSetPermisos.setIdMenu(menus);
                permisosSetPermisos = em.merge(permisosSetPermisos);
                if (oldIdMenuOfPermisosSetPermisos != null) {
                    oldIdMenuOfPermisosSetPermisos.getPermisosSet().remove(permisosSetPermisos);
                    oldIdMenuOfPermisosSetPermisos = em.merge(oldIdMenuOfPermisosSetPermisos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Menus menus) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Menus persistentMenus = em.find(Menus.class, menus.getId());
            Set<Permisos> permisosSetOld = persistentMenus.getPermisosSet();
            Set<Permisos> permisosSetNew = menus.getPermisosSet();
            List<String> illegalOrphanMessages = null;
            for (Permisos permisosSetOldPermisos : permisosSetOld) {
                if (!permisosSetNew.contains(permisosSetOldPermisos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Permisos " + permisosSetOldPermisos + " since its idMenu field is not nullable.");
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
            menus.setPermisosSet(permisosSetNew);
            menus = em.merge(menus);
            for (Permisos permisosSetNewPermisos : permisosSetNew) {
                if (!permisosSetOld.contains(permisosSetNewPermisos)) {
                    Menus oldIdMenuOfPermisosSetNewPermisos = permisosSetNewPermisos.getIdMenu();
                    permisosSetNewPermisos.setIdMenu(menus);
                    permisosSetNewPermisos = em.merge(permisosSetNewPermisos);
                    if (oldIdMenuOfPermisosSetNewPermisos != null && !oldIdMenuOfPermisosSetNewPermisos.equals(menus)) {
                        oldIdMenuOfPermisosSetNewPermisos.getPermisosSet().remove(permisosSetNewPermisos);
                        oldIdMenuOfPermisosSetNewPermisos = em.merge(oldIdMenuOfPermisosSetNewPermisos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = menus.getId();
                if (findMenus(id) == null) {
                    throw new NonexistentEntityException("The menus with id " + id + " no longer exists.");
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
            Menus menus;
            try {
                menus = em.getReference(Menus.class, id);
                menus.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The menus with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<Permisos> permisosSetOrphanCheck = menus.getPermisosSet();
            for (Permisos permisosSetOrphanCheckPermisos : permisosSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Menus (" + menus + ") cannot be destroyed since the Permisos " + permisosSetOrphanCheckPermisos + " in its permisosSet field has a non-nullable idMenu field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(menus);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Menus> findMenusEntities() {
        return findMenusEntities(true, -1, -1);
    }

    public List<Menus> findMenusEntities(int maxResults, int firstResult) {
        return findMenusEntities(false, maxResults, firstResult);
    }

    private List<Menus> findMenusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Menus.class));
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

    public Menus findMenus(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Menus.class, id);
        } finally {
            em.close();
        }
    }

    public int getMenusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Menus> rt = cq.from(Menus.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
