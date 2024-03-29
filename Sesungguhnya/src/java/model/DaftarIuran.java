/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Iuran;
import entity.Rumah;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


/**
 *
 * @author 1015
 */
public class DaftarIuran {
     private int jumlahiuran = -1;
    
    public DaftarIuran() {
        emf = Persistence.createEntityManagerFactory("SiORpertama");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    
   public List<Iuran> getIuran() {
        List<Iuran> hunian = new ArrayList<Iuran>();

        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT a FROM Hunian AS a");
            hunian = q.getResultList();

        } finally {
            em.close();
        }
        return hunian;
    }
    public Iuran findIuran(String kodeiuran) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Iuran.class, kodeiuran);
        } finally {
            em.close();
        }
    }
    
 public void editIuran(Iuran iuran) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            iuran = em.merge(iuran);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rumah> seluruhDaftarIuran () {
        List<Rumah> rumah = null;
        EntityManager em = null;
        try {
            em = getEntityManager();
            Query q = em.createQuery("SELECT object(o) FROM rumah as o ORDER BY o.id DESC");
            q.setMaxResults(10);
            rumah = q.getResultList();

        } catch (javax.persistence.EntityNotFoundException e) {
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return rumah;
    }
    
     public void tambahIuran(Iuran iuran) {

        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            iuran.getBulaniuran();

            iuran.setKodeiuran(null);
            em.persist(iuran);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }
     
     public boolean isKodeExist(String kode) {
        DaftarIuran daftarIuran = new DaftarIuran();
        List<Iuran> listIuran = daftarIuran.getIuran();
        Iterator<Iuran> iterator = listIuran.iterator();
        Iuran tes = new Iuran();

        while (iterator.hasNext()) {
            tes = iterator.next();
            if (kode.equalsIgnoreCase(tes.getKodeiuran())) {
                return true;
            }
        } return false;
    }
     
}
