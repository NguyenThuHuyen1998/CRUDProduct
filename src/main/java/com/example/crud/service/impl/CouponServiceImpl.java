package com.example.crud.service.impl;

import com.example.crud.entity.Coupon;
import com.example.crud.repository.CouponRepository;
import com.example.crud.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    private CouponRepository couponRepository;

    @Autowired
    public CouponServiceImpl(CouponRepository couponRepository){
        this.couponRepository= couponRepository;
    }

    @Override
    public List<Coupon> getListCounpon() {
        return (List<Coupon>) couponRepository.findAll();
    }

    @Override
    public void addCoupon(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Override
    public void deleteCoupon(Coupon coupon) {
        couponRepository.delete(coupon);
    }
}
