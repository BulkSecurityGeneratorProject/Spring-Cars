package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Manufacturer.
 */
@Entity
@Table(name = "manufacturer")
public class Manufacturer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "description")
    private String description;

    @Column(name = "year_fund")
    private ZonedDateTime yearFund;

    @OneToMany(mappedBy = "manufacturer")
    @JsonIgnore
    private Set<Car> cars = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Manufacturer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public Manufacturer country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public Manufacturer description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getYearFund() {
        return yearFund;
    }

    public Manufacturer yearFund(ZonedDateTime yearFund) {
        this.yearFund = yearFund;
        return this;
    }

    public void setYearFund(ZonedDateTime yearFund) {
        this.yearFund = yearFund;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public Manufacturer cars(Set<Car> cars) {
        this.cars = cars;
        return this;
    }

    public Manufacturer addCar(Car car) {
        cars.add(car);
        car.setManufacturer(this);
        return this;
    }

    public Manufacturer removeCar(Car car) {
        cars.remove(car);
        car.setManufacturer(null);
        return this;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Manufacturer manufacturer = (Manufacturer) o;
        if(manufacturer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, manufacturer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", country='" + country + "'" +
            ", description='" + description + "'" +
            ", yearFund='" + yearFund + "'" +
            '}';
    }
}
