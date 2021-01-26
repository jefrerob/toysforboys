package be.vdab.toysforboys.domain;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "customers")
@NamedEntityGraph(name = Customer.WITH_COUNTRY,
        attributeNodes = @NamedAttributeNode("country") )
public class Customer {
    public static final String WITH_COUNTRY = "Customer.withCountry";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Embedded
    private Adres adres;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "countryid")
    private Country country;
    @OneToMany(mappedBy = "customer")
    @OrderBy("ordered")
    private Set<Order> orders = new LinkedHashSet<>();
    @Version
    private int version;

    protected Customer(){}

    public Customer(String name, Adres adres, Country country) {
        this.name = name;
        this.adres = adres;
        this.country = country;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Adres getAdres() {
        return adres;
    }

    public Country getCountry() {
        return country;
    }

    public Set<Order> getOrders() {
        return Collections.unmodifiableSet(orders);
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return name.toUpperCase().equals(customer.name.toUpperCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toUpperCase());
    }
}
