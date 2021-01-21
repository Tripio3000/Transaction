import edu.models.Transac;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;
import dao.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QueryWithSession {
//    private SessionFactory sessionFactory;

//    public Transac getTransacById(int id) {
//        Session session = sessionFactory.openSession();
//        Criteria criteria = session.createCriteria(Transac.class);
//        criteria.add(Restrictions.eq("priorityId", id));
//        Transac priority = (Transac) criteria.uniqueResult();
//        session.close();
//        return priority;
//
//    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Transac tr = new Transac();
                    System.out.println("qwerty");
                    TransacDao transacDao = new TransacDao();

//                    transacDao.update(2);

                    tr = transacDao.getTransacById(2);
                    System.out.println("id: " + tr.getId() + " am: " + tr.getAmount());
                }
            });

        }

//        System.out.println("smth");

//        Transac tr = new Transac();
//        TransacDao transacDao = new TransacDao();
//
//
//        Transac transac1 = new Transac(200);
//        transacDao.save(transac1);
//
//        Transac transac2 = new Transac(300);
//        transacDao.save(transac2);

//        tr = transacDao.getTransacById(2);
//        System.out.println("id: " + tr.getId() + " am: " + tr.getAmount());

    }

    public static int getRandomDiceNumber()
    {
        return (int) (Math.random() * 5) + 1;
    }
}
