package com.example.crud.controller;

import com.example.crud.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

/*
    created by HuyenNgTn on 01/12/2020
*/
class DataResult<T> extends HttpEntity<T>{
    public static final Logger logger = LoggerFactory.getLogger(DataResult.class);
    public  DataPage dataPage;
    public  List<Product> totalProduct;
    public HttpStatus httpStatus;

    public DataResult(List<Product> totalProduct, DataPage dataPage, HttpStatus httpStatus) {
        this.totalProduct=totalProduct;
        this.dataPage=dataPage;
        this.httpStatus= httpStatus;
    }

    public DataResult(HttpStatus httpStatus) {
        this.httpStatus= httpStatus;
    }

}

class DataPage {
    public static final Logger logger = LoggerFactory.getLogger(DataPage.class);
    public  int total_count;
    public  int record_in_page;
    public  int total_page;
    public  int current_page;

    public DataPage(int total_count, int record_in_page, int total_page, int current_page) {
        this.total_count=total_count;
        this.record_in_page=record_in_page;
        this.total_page=total_page;
        this.current_page=current_page;
    }
}
