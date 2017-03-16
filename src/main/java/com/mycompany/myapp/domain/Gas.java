package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Gas.
 */
@Entity
@Table(name = "gas")
public class Gas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "gases")
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

    public Gas name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public Gas cars(Set<Car> cars) {
        this.cars = cars;
        return this;
    }

    public Gas addCar(Car car) {
        cars.add(car);
        car.getGases().add(this);
        return this;
    }

    public Gas removeCar(Car car) {
        cars.remove(car);
        car.getGases().remove(this);
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
        Gas gas = (Gas) o;
        if(gas.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, gas.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Gas{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
