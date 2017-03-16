package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Segment;

/**
 * A Car.
 */
@Entity
@Table(name = "car")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "model")
    private String model;

    @Column(name = "country")
    private String country;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "segment")
    private Segment segment;

    @Column(name = "sales")
    private Integer sales;

    @Column(name = "min_price")
    private Double minPrice;

    @Column(name = "max_price")
    private Double maxPrice;

    @ManyToOne
    private Manufacturer manufacturer;

    @ManyToOne
    private Link link;

    @ManyToMany
    @JoinTable(name = "car_classification",
               joinColumns = @JoinColumn(name="cars_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="classifications_id", referencedColumnName="ID"))
    private Set<Classification> classifications = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "car_traction",
               joinColumns = @JoinColumn(name="cars_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="tractions_id", referencedColumnName="ID"))
    private Set<Traction> tractions = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "car_gas",
               joinColumns = @JoinColumn(name="cars_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="gases_id", referencedColumnName="ID"))
    private Set<Gas> gases = new HashSet<>();

    @OneToMany(mappedBy = "car")
    @JsonIgnore
    private Set<Photo> photos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public Car model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCountry() {
        return country;
    }

    public Car country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public Car description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Segment getSegment() {
        return segment;
    }

    public Car segment(Segment segment) {
        this.segment = segment;
        return this;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public Integer getSales() {
        return sales;
    }

    public Car sales(Integer sales) {
        this.sales = sales;
        return this;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public Car minPrice(Double minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public Car maxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public Car manufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Link getLink() {
        return link;
    }

    public Car link(Link link) {
        this.link = link;
        return this;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Set<Classification> getClassifications() {
        return classifications;
    }

    public Car classifications(Set<Classification> classifications) {
        this.classifications = classifications;
        return this;
    }

    public Car addClassification(Classification classification) {
        classifications.add(classification);
        classification.getCars().add(this);
        return this;
    }

    public Car removeClassification(Classification classification) {
        classifications.remove(classification);
        classification.getCars().remove(this);
        return this;
    }

    public void setClassifications(Set<Classification> classifications) {
        this.classifications = classifications;
    }

    public Set<Traction> getTractions() {
        return tractions;
    }

    public Car tractions(Set<Traction> tractions) {
        this.tractions = tractions;
        return this;
    }

    public Car addTraction(Traction traction) {
        tractions.add(traction);
        traction.getCars().add(this);
        return this;
    }

    public Car removeTraction(Traction traction) {
        tractions.remove(traction);
        traction.getCars().remove(this);
        return this;
    }

    public void setTractions(Set<Traction> tractions) {
        this.tractions = tractions;
    }

    public Set<Gas> getGases() {
        return gases;
    }

    public Car gases(Set<Gas> gases) {
        this.gases = gases;
        return this;
    }

    public Car addGas(Gas gas) {
        gases.add(gas);
        gas.getCars().add(this);
        return this;
    }

    public Car removeGas(Gas gas) {
        gases.remove(gas);
        gas.getCars().remove(this);
        return this;
    }

    public void setGases(Set<Gas> gases) {
        this.gases = gases;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public Car photos(Set<Photo> photos) {
        this.photos = photos;
        return this;
    }

    public Car addPhoto(Photo photo) {
        photos.add(photo);
        photo.setCar(this);
        return this;
    }

    public Car removePhoto(Photo photo) {
        photos.remove(photo);
        photo.setCar(null);
        return this;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        if(car.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Car{" +
            "id=" + id +
            ", model='" + model + "'" +
            ", country='" + country + "'" +
            ", description='" + description + "'" +
            ", segment='" + segment + "'" +
            ", sales='" + sales + "'" +
            ", minPrice='" + minPrice + "'" +
            ", maxPrice='" + maxPrice + "'" +
            '}';
    }
}
