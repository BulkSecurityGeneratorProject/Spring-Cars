package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Classification.
 */
@Entity
@Table(name = "classification")
public class Classification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Classification parent;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    private Set<Classification> children = new HashSet<>();

    @ManyToMany(mappedBy = "classifications")
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

    public Classification name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Classification description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Classification getParent() {
        return parent;
    }

    public Classification parent(Classification classification) {
        this.parent = classification;
        return this;
    }

    public void setParent(Classification classification) {
        this.parent = classification;
    }

    public Set<Classification> getChildren() {
        return children;
    }

    public Classification children(Set<Classification> classifications) {
        this.children = classifications;
        return this;
    }

    public Classification addChildren(Classification classification) {
        children.add(classification);
        classification.setParent(this);
        return this;
    }

    public Classification removeChildren(Classification classification) {
        children.remove(classification);
        classification.setParent(null);
        return this;
    }

    public void setChildren(Set<Classification> classifications) {
        this.children = classifications;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public Classification cars(Set<Car> cars) {
        this.cars = cars;
        return this;
    }

    public Classification addCar(Car car) {
        cars.add(car);
        car.getClassifications().add(this);
        return this;
    }

    public Classification removeCar(Car car) {
        cars.remove(car);
        car.getClassifications().remove(this);
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
        Classification classification = (Classification) o;
        if(classification.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, classification.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Classification{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
