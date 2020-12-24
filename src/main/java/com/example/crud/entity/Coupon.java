package com.example.crud.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "coupon_id")
    private long couponId;

    @NotEmpty
    @Column(name = "coupon_code", unique = true)
    private String couponCode;

    @NotEmpty
    @Column(name = "date_start")
    private String dateStart;

    @NotEmpty
    @Column(name = "date_end")
    private String dateEnd;

    @Column(name = "percent_discount")
    private int percentDiscount;

    @Column(name = "discount")
    private double discount;

    public Coupon(long couponId, @NotEmpty String couponCode, @NotEmpty String dateStart, @NotEmpty String dateEnd, int percentDiscount, double discount) {
        this.couponId = couponId;
        this.couponCode = couponCode;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.percentDiscount = percentDiscount;
        this.discount = discount;
    }

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(int percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
