/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fianza.facade;

import com.mycompany.fianza.entidades.Listas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.fianza.entidades.ListasGrupo;
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
public class ListasJpaController implements Serializable {

    public ListasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public List<Listas> getListas (String codigo){
        List<Listas> listas = new ArrayList<Listas>();
        List<Object[]> listasTmp = new ArrayList<Object[]>();
        
        try{
            String sql = " SELECT l.* FROM Listas l "
                       + " INNER JOIN listas_grupo lg ON lg.id = l.id_lista_grupo "
                       + " WHERE lg.codigo = ? ";
            Query resultado = getEntityManager().createNativeQuery(sql); 
            resultado.setParameter(1, codigo);
            listasTmp = resultado.getResultList();
            
            for (Object[] object : listasTmp) {
                Listas lista = new Listas();
                lista.setId((Integer) object[0]);
                lista.setDescripcion((String) object[1]);
                lista.setIdListasgrupo(new ListasGrupo());
                lista.getIdListasgrupo().setId((Integer) object[2]);
                listas.add(lista);
            }

        } catch (NoResultException ex) {
            System.out.println(ex.toString());
            return null;
        }
         return listas;   
        }
    

    public void create(Listas listas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ListasGrupo idListasgrupo = listas.getIdListasgrupo();
            if (idListasgrupo != null) {
                idListasgrupo = em.getReference(idListasgrupo.getClass(), idListasgrupo.getId());
                listas.setIdListasgrupo(idListasgrupo);
            }
            em.persist(listas);
            if (idListasgrupo != null) {
                idListasgrupo.getListasSet().add(listas);
                idListasgrupo = em.merge(idListasgrupo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Listas listas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Listas persistentListas = em.find(Listas.class, listas.getId());
            ListasGrupo idListasgrupoOld = persistentListas.getIdListasgrupo();
            ListasGrupo idListasgrupoNew = listas.getIdListasgrupo();
            if (idListasgrupoNew != null) {
                idListasgrupoNew = em.getReference(idListasgrupoNew.getClass(), idListasgrupoNew.getId());
                listas.setIdListasgrupo(idListasgrupoNew);
            }
            listas = em.merge(listas);
            if (idListasgrupoOld != null && !idListasgrupoOld.equals(idListasgrupoNew)) {
                idListasgrupoOld.getListasSet().remove(listas);
                idListasgrupoOld = em.merge(idListasgrupoOld);
            }
            if (idListasgrupoNew != null && !idListasgrupoNew.equals(idListasgrupoOld)) {
                idListasgrupoNew.getListasSet().add(listas);
                idListasgrupoNew = em.merge(idListasgrupoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = listas.getId();
                if (findListas(id) == null) {
                    throw new NonexistentEntityException("The listas with id " + id + " no longer exists.");
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
            Listas listas;
            try {
                listas = em.getReference(Listas.class, id);
                listas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The listas with id " + id + " no longer exists.", enfe);
            }
            ListasGrupo idListasgrupo = listas.getIdListasgrupo();
            if (idListasgrupo != null) {
                idListasgrupo.getListasSet().remove(listas);
                idListasgrupo = em.merge(idListasgrupo);
            }
            em.remove(listas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Listas> findListasEntities() {
        return findListasEntities(true, -1, -1);
    }

    public List<Listas> findListasEntities(int maxResults, int firstResult) {
        return findListasEntities(false, maxResults, firstResult);
    }

    private List<Listas> findListasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Listas.class));
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

    public Listas findListas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Listas.class, id);
        } finally {
            em.close();
        }
    }

    public int getListasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Listas> rt = cq.from(Listas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
