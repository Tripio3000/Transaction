import edu.models.Transac;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        for(int i = 0; i < 2 ; i++) {
            service.submit(new Runnable() {
                public void run() {
                    Transaction tx =  null;

                    try(Session session = HibernateUtil.getSessionFactory().openSession() ) {
                        tx = session.beginTransaction();
                        Transac transac = new Transac(100);
                        session.save(transac);
                        tx.commit();

//                        CriteriaBuilder cb = session.getCriteriaBuilder();
//                        CriteriaQuery<Transac> cq = cb.createQuery(Transac.class);
//                        Root<Transac> root = cq.from(Transac.class);
//                        cq.select(root);
//                        Query<Transac> query = session.createQuery(cq);
//                        List<Transac> result = query.getResultList();

//                        Query query = session.createQuery("from Transac where id = :paramName");
//                        query.setParameter("paramName", 1);
//                        List result = query.list();

//                        obj.forEach(p -> System.out.println("T id: "+p[0]+" Amount: "+p[1]));









                        int rec_amount = 0;
                        int recipient;
                        while (transac.getAmount() > 0) {
                            recipient = transac.getId();
                            while (recipient == transac.getId()) {
                                recipient = getRandomDiceNumber();
                            }
                            int am = getQuery("select id, amount from transaction", session);
                            tx = session.beginTransaction();
                            Query query = session.createQuery("update Transac set amount = :param where id = :idParam");
                            query.setParameter("idParam", recipient);
                            query.setParameter("param", (am - 10));

                            int result = query.executeUpdate();
//                            System.out.println("res: " + result + " id: " + transac.getId());
                            tx.commit();
                            transac.setAmount(transac.getAmount() + 10);


                        }

                        session.close();
//                        System.out.println("id:  " + transac.getId());
                    } catch (Exception e) {
                        if(tx != null && tx.isActive())
                            tx.rollback();
                        throw e;
                    }
                }
            });
        }
    }

    public static int getRandomDiceNumber()
    {
        return (int) (Math.random() * 2);
    }

    public static int getQuery (String string, Session session, int recip) {
        int am = 0;
        List<Object[]> obj = session.createSQLQuery(string).list();
        for (Object[] o : obj) {
            String i = o[0].toString();
            if (i.equals("1")) {
                String str = o[1].toString();
                am = Integer.parseInt(str);
                System.out.println("am: " + am);
            }
        }
        return am;
    }
}
