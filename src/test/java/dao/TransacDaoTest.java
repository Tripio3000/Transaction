package dao;

import edu.models.Transac;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransacDaoTest {

    @Test
    void save() {
        TransacDao transacDao = new TransacDao();
        Transac tr = new Transac();

        Transac transac = new Transac(100);
        transacDao.save(transac);
        tr = transacDao.getTransacById(transac.getId());

        assertEquals(transac, tr);
    }
}