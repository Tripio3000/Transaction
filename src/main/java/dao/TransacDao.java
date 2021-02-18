package dao;

import edu.models.Transac;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import java.sql.SQLOutput;

public class TransacDao {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.baeldung.movie__catalog");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Transac getTransacById(int id) {

        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        Transac tr = em.find(Transac.class, id, LockModeType.PESSIMISTIC_WRITE);

        em.getTransaction().commit();
        em.close();

        return tr;

    }

    public void save(Transac Transac) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(Transac);
        tx1.commit();
        session.close();
    }

    public Transac findById(int id) {
        return HibernateUtil.getSessionFactory().openSession().get(Transac.class, id);
    }

    public void merge(int id, int delta) {

        try {
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            Transac tr = em.find(Transac.class, id, LockModeType.PESSIMISTIC_WRITE);
            tr.setAmount(tr.getAmount() + delta);

            em.getTransaction().commit();
            em.close();

        } catch (Exception e) {
            System.out.println("Exeption!!");
            e.printStackTrace();
        }
    }

    public void update(int id) {

        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

//            int perosnId = 2;
            Transac tr = session.get(Transac.class, id);
            if (tr != null) {
                tx = session.beginTransaction();
                tr.setAmount(tr.getAmount() + 10);
                session.update(tr);
                tx.commit();
            } else {
                System.out.println("Person details not found with ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("Exeption!!");
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    public void delete(Transac Transac) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(Transac);
        tx1.commit();
        session.close();
    }
}
