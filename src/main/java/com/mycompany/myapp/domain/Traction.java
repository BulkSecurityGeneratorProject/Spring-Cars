package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Traction.
 */
@Entity
@Table(name = "traction")
public class Traction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ref")
    private String ref;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "tractions")
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

    public Traction name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRef() {
        return ref;
    }

    public Traction ref(String ref) {
        this.ref = ref;
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getDescription() {
        return description;
    }

    public Traction description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public Traction cars(Set<Car> cars) {
        this.cars = cars;
        return this;
    }

    public Traction addCar(Car car) {
        cars.add(car);
        car.getTractions().add(this);
        return this;
    }

    public Traction removeCar(Car car) {
        cars.remove(car);
        car.getTractions().remove(this);
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
        Traction traction = (Traction) o;
        if(traction.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Traction{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", ref='" + ref + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
