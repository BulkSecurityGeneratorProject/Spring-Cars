package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.LinkType;

/**
 * A Link.
 */
@Entity
@Table(name = "link")
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "url")
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private LinkType type;

    @OneToMany(mappedBy = "link")
    @JsonIgnore
    private Set<Car> cars = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public Link url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LinkType getType() {
        return type;
    }

    public Link type(LinkType type) {
        this.type = type;
        return this;
    }

    public void setType(LinkType type) {
        this.type = type;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public Link cars(Set<Car> cars) {
        this.cars = cars;
        return this;
    }

    public Link addCar(Car car) {
        cars.add(car);
        car.setLink(this);
        return this;
    }

    public Link removeCar(Car car) {
        cars.remove(car);
        car.setLink(null);
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
        Link link = (Link) o;
        if(link.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, link.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Link{" +
            "id=" + id +
            ", url='" + url + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
