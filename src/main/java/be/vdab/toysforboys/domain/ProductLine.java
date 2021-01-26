package be.vdab.toysforboys.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "productlines")
public class ProductLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    @Version
    private int version;

    protected ProductLine(){}

    public ProductLine(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductLine)) return false;
        ProductLine that = (ProductLine) o;
        return Objects.equals(name.toUpperCase(), that.name.toUpperCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toUpperCase());
    }
}
