import edu.models.Transac;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;
import dao.*;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QueryWithSession {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 1; i < 6; i++) {
            int finalI = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Transac tr = new Transac();
                    TransacDao transacDao = new TransacDao();

                    while (true) {
                        tr = transacDao.getTransacById(finalI);
                        if (tr.getAmount() <= 0) {
                            break;
                        }
                        int rec = finalI;
                        while (rec == finalI) {
                            rec = getRandomDiceNumber();
                        }
                        transacDao.merge(finalI, -10);
                        transacDao.merge(rec, 10);
                        log("Transaction updated", tr);
//                        System.out.println("Thread: " + finalI + " id: " + tr.getId() + " am: " + tr.getAmount());

                    }
                    executorService.shutdown();
                }
            });

        }
    }

    public static int getRandomDiceNumber() {
        return (int) (Math.random() * 5) + 1;
    }

    private static void log(Object... msgs) {
        System.out.println(LocalTime.now() + " - " + Thread.currentThread().getName() +
                " - " + Arrays.toString(msgs));
    }
}
