package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Photo.
 */
@Entity
@Table(name = "photo")
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Car car;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public Photo image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Photo imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getDescription() {
        return description;
    }

    public Photo description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Car getCar() {
        return car;
    }

    public Photo car(Car car) {
        this.car = car;
        return this;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Photo photo = (Photo) o;
        if(photo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, photo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Photo{" +
            "id=" + id +
            ", image='" + image + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
