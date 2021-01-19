import edu.models.Transac;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.LockModeType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class InterruptableTasks {

    private static class InterruptableTask implements Runnable {
        Object o = new Object();
        private volatile boolean suspended = false;

        public void suspend() {
            suspended = true;
        }

        public void resume() {
            suspended = false;
            synchronized (o) {
                o.notifyAll();
            }
        }

        @Override
        public void run() {
            Transaction tx =  null;
            Transac transac;
            try(Session session = HibernateUtil.getSessionFactory().openSession() ) {
                tx = session.beginTransaction();
                transac = new Transac(100);
                session.save(transac);
                tx.commit();
                session.close();



            while (!Thread.currentThread().isInterrupted()) {
                if (!suspended) {
                    System.out.println(transac.getId());
                    int recipient;
                    int am = 100;
                    while (am > 0) {
                        recipient = transac.getId();
                        while (recipient == transac.getId()) {
                            recipient = getRandomDiceNumber();
                        }
                        am = getQuery("select id, amount from transaction", session, recipient);
                        if (am == 0) {
                            this.suspend();
                        }
                        System.out.println("am: " + am + " recip: " + recipient + " cur_id: " + transac.getId() + " cur_am: " + transac.getAmount());

                        tx = session.beginTransaction();
                        transac.setAmount(transac.getAmount() + 10);
                        session.update(transac);
                        tx.commit();

                        updateQuery(session, recipient, am);
                    }


                } else {
                    System.out.println("suspended");
                    try {
                        while (suspended) {
                            synchronized (o) {
                                o.wait();
                            }
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }
            } catch (Exception e) {
                if(tx != null && tx.isActive())
                    tx.rollback();
                throw e;
            }
            System.out.println("Cancelled");
        }

    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        InterruptableTask task1 = new InterruptableTask();
        InterruptableTask task2 = new InterruptableTask();
        Map<Integer, InterruptableTask> tasks = new HashMap<Integer, InterruptableTask>();
        tasks.put(1, task1);
        tasks.put(2, task2);


        threadPool.submit(task1);
        threadPool.submit(task2);
//        TimeUnit.SECONDS.sleep(2);
//        InterruptableTask theTask = tasks.get(1);//get task by id
//        theTask.suspend();
//        TimeUnit.SECONDS.sleep(2);
//        theTask.resume();
//        TimeUnit.SECONDS.sleep(4);
//        threadPool.shutdownNow();
    }

    public synchronized static int getQuery (String string, Session session, int recip) {
        int am = 0;
        String tmp = String.valueOf(recip);
        List<Object[]> obj = session.createSQLQuery(string).list();
        for (Object[] o : obj) {
            String i = o[0].toString();
            if (i.equals(tmp)) {
                String str = o[1].toString();
                am = Integer.parseInt(str);
            }
        }
        return am;
    }

    public synchronized static void updateQuery(Session session, int recipient, int am) {
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("update Transac set amount = :param where id = :idParam");
        query.setParameter("idParam", recipient);
        query.setParameter("param", (am - 10));
        query.executeUpdate();
        query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        tx.commit();

    }

    public static int getRandomDiceNumber()
    {
        return (int) (Math.random() * 2) + 1;
    }
}