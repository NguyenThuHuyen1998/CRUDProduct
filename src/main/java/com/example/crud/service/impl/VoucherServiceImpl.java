package com.example.crud.service.impl;

import com.example.crud.entity.Voucher;
import com.example.crud.repository.VoucherRepository;
import com.example.crud.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class VoucherServiceImpl implements VoucherService {

    private VoucherRepository voucherRepository;

    @Autowired
    public VoucherServiceImpl(VoucherRepository voucherRepository){
        this.voucherRepository = voucherRepository;
    }

    @Override
    public List<Voucher> getListVoucher() {
        return (List<Voucher>) voucherRepository.findAll();
    }

    @Override
    public void addVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    @Override
    public void deleteVoucher(Voucher voucher) {
        voucherRepository.delete(voucher);
    }

//    @Override
//    public Voucher getVoucherByCode(String code) {
//        Voucher voucher= voucherRepository.findVoucherByCode(code);
//        return voucher;
//    }
}
