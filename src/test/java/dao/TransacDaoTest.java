package dao;

import edu.models.Transac;
import org.junit.jupiter.api.Test;
import org.junit.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.ParameterizedType;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(Parameterized.class)
class TransacDaoTest {

    @ParameterizedTest
    @ValueSource(ints = {0, -100, 500, Integer.MAX_VALUE})
    void save(int num) {
        TransacDao transacDao = new TransacDao();
        Transac tr;

        Transac transac = new Transac(num);
        transacDao.save(transac);
        tr = transacDao.getTransacById(transac.getId());

        assertEquals(transac, tr);
    }

    @ParameterizedTest
    @CsvSource({"1,100", "2,100", "3,100"})
    void merge(String rec, String num) {
        TransacDao transacDao = new TransacDao();
        Transac tr;
        Transac tr1;

        tr = transacDao.getTransacById(Integer.parseInt(rec));
        transacDao.merge(Integer.parseInt(rec), Integer.parseInt(num));
        tr.setAmount(tr.getAmount() + Integer.parseInt(num));
        tr.setVersion(tr.getVersion() + 1);
        tr1 = transacDao.getTransacById(Integer.parseInt(rec));

        assertEquals(tr1, tr);
    }
}