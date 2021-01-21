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


//        Transac tr = em.createQuery("select t.amount from Transac t where t + :param")
//                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
//                .setParameter("param", );




//        Session session = null;
//        Transac tr = null;
//        try {
//            session = HibernateUtil.getSessionFactory().openSession();
//            tr =  (Transac) session.get(Transac.class, id);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (session != null && session.isOpen()) {
//                session.close();
//            }
//        }
//        return tr;



//        Session session = HibernateUtil.getSessionFactory().openSession();
//        Criteria criteria = session.createCriteria(Transac.class);
//        criteria.add(Restrictions.eq("id", id));
//        Transac priority = (Transac) criteria.uniqueResult();
//        session.close();
//        return priority;

    }

    public Transac findById(int id) {
        return HibernateUtil.getSessionFactory().openSession().get(Transac.class, id);
    }

    public void save(Transac Transac) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(Transac);
//        session.lock(Transac, LockMode.PESSIMISTIC_WRITE);
        tx1.commit();
        session.close();
    }

    public void update(int id) {

        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

//            int perosnId = 2;
            Transac tr = session.get(Transac.class, id);
            if(tr != null){
                tx = session.beginTransaction();
                tr.setAmount(tr.getAmount() + 10);
                session.update(tr);
                tx.commit();
            }else{
                System.out.println("Person details not found with ID: "+ id);
            }
        }catch(Exception e){
            System.out.println("Exeption!!");
            e.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        }

//        Session session = HibernateUtil.getSessionFactory().openSession();
//        Transaction tx1 = session.beginTransaction();
//        session.update(Transac);
//        tx1.commit();
//        session.close();
    }

    public void delete(Transac Transac) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(Transac);
        tx1.commit();
        session.close();
    }
}
