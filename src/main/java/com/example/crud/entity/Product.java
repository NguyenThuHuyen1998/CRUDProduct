package com.example.crud.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "tblPRODUCTS")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cate_id", nullable = false)
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Category category;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "description")
    private String description;

    @Column(name = "dateAdd")
    private long dateAdd;

    @Column(name = "image")
    private String image;

    public Product() {
    }

    public Product(long id, Category category, String name, double price, String description, long dateAdd, String image) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.description = description;
        this.dateAdd= dateAdd;
        this.image= image;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(long dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
