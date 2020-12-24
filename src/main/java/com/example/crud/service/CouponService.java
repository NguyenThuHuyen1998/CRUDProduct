package com.example.crud.service;

import com.example.crud.entity.Coupon;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CouponService {

    List<Coupon> getListCounpon();
    void addCoupon(Coupon coupon);
    void deleteCoupon(Coupon coupon);
}
