import dao.TransacDao;
import edu.models.Transac;

public class SaveDataToDB {
    public static void main(String[] args) {
        Transac tr = new Transac();
        TransacDao transacDao = new TransacDao();


        Transac transac1 = new Transac(100);
        transacDao.save(transac1);

        Transac transac2 = new Transac(100);
        transacDao.save(transac2);

        Transac transac3 = new Transac(100);
        transacDao.save(transac3);

        Transac transac4 = new Transac(100);
        transacDao.save(transac4);

        Transac transac5 = new Transac(100);
        transacDao.save(transac5);

        tr = transacDao.getTransacById(2);
        System.out.println("id: " + tr.getId() + " am: " + tr.getAmount());
    }
}
