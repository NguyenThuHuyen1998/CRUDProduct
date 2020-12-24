package com.example.crud.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "address_ship")
public class AddressShip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private long addressId;

    @NotEmpty(message = "*Please provider your city")
    @Column(name = "city")
    private String city;

    @NotEmpty(message = "*Please provider your district")
    @Column(name = "district")
    private String district;

    @NotEmpty(message = "*Please provider your street")
    @Column(name = "street")
    private String street;

    @NotEmpty(message = "*Please provider your phone")
    @Column(name = "phone")
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public AddressShip(long addressId, @NotEmpty(message = "*Please provider your city") String city, @NotEmpty(message = "*Please provider your district") String district, @NotEmpty(message = "*Please provider your street") String street, @NotEmpty(message = "*Please provider your phone") String phone, User user) {
        this.addressId = addressId;
        this.city = city;
        this.district = district;
        this.street = street;
        this.phone = phone;
        this.user = user;
    }

    public AddressShip() {

    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
