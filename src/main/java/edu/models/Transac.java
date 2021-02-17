package edu.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "Transac{" +
                "version=" + version +
                ", id=" + id +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transac transac = (Transac) o;
        return version == transac.version &&
                id == transac.id &&
                amount == transac.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, id, amount);
    }
}
