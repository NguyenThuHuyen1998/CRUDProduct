package com.example.crud.entity;

import org.json.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name = "tblAddress")
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

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

    @Column(name = "note")
    private String note;

    @NotEmpty
    @Column(name = "delivery")
    private String delivery;

    public Address(long addressId, @NotEmpty(message = "*Please provider your city") String city, @NotEmpty(message = "*Please provider your district") String district, @NotEmpty(message = "*Please provider your street") String street, @NotEmpty(message = "*Please provider your phone") String phone, User user, @NotEmpty String delivery, String note) {
        this.addressId = addressId;
        this.city = city;
        this.district = district;
        this.street = street;
        this.phone = phone;
        this.user = user;
        this.delivery= delivery;
        this.note= note;
    }

    public Address(String city, String district, String street, String phone, String delivery){
        this.city= city;
        this.delivery= delivery;
        this.district= district;
        this.street= street;
        this.phone= phone;
    }

    public Address(String data){
        JSONObject jsonObject= new JSONObject(data);
        this.city= jsonObject.getString("city");
        this.delivery= jsonObject.getString("delivery");
        this.district= jsonObject.getString("district");
        this.street= jsonObject.getString("street");
        this.phone= jsonObject.getString("phone");
    }

    public Address() {

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

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
