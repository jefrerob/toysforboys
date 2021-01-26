package be.vdab.toysforboys.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date ordered;
    private Date required;
    private Date shipped;
    private String comments;
    @ManyToOne(optional = false)
    @JoinColumn(name = "customerid")
    private Customer customer;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Version
    private int version;

    protected Order(){}

    public Order(Date ordered, Date required, Date shipped, String comments, Customer customer, Status status) {
        this.ordered = ordered;
        this.required = required;
        this.shipped = shipped;
        this.comments = comments;
        this.customer = customer;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public Date getOrdered() {
        return ordered;
    }

    public Date getRequired() {
        return required;
    }

    public Date getShipped() {
        return shipped;
    }

    public String getComments() {
        return comments;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Status getStatus() {
        return status;
    }



}
