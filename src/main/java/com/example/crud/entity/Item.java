package com.example.crud.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import java.io.Serializable;

/*
    created by HuyenNgTn on 18/11/2020
*/

public class Item implements Serializable {
    private Long productId;
    private int count;

    public Item() {
    }

    public Item(Long productId, int count) {
        this.productId = productId;
        this.count = count;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
