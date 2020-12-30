package com.example.crud.service;

import com.example.crud.entity.Voucher;

import java.util.List;

public interface VoucherService {

    List<Voucher> getListVoucher();
    void addVoucher(Voucher voucher);
    void deleteVoucher(Voucher voucher);
//    Voucher getVoucherByCode(String code);
}
