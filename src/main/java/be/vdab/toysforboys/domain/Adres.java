package be.vdab.toysforboys.domain;

import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
public class Adres {
    private String streetAndNumber;
    private String city;
    private String state;
    private String postalCode;


    protected Adres() {}

    public Adres(String streetAndNumber, String city, String state, String postalCode) {
        this.streetAndNumber = streetAndNumber;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Adres)) return false;
        Adres adres = (Adres) o;
        return Objects.equals(streetAndNumber, adres.streetAndNumber) && Objects.equals(city, adres.city) && Objects.equals(state, adres.state) && Objects.equals(postalCode, adres.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetAndNumber, city, state, postalCode);
    }
}
