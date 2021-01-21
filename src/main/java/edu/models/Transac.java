package edu.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "transaction")
public class Transac implements Serializable {

    @Version
    private long version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int amount;

    public Transac() {
    }

    public Transac(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
