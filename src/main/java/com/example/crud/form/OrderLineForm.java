package com.example.crud.form;

import com.example.crud.entity.OrderLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    created by HuyenNgTn on 03/12/2020
*/
public class OrderLineForm {
    public static final Logger logger = LoggerFactory.getLogger(OrderLineForm.class);
    private long orderLineId;
    private long productId;
    private String productName;
    private int quantity;

    public OrderLineForm() {
    }

    public OrderLineForm(long orderLineId, long productId, String productName, int quantity) {
        this.orderLineId = orderLineId;
        this.productId = productId;
        this.productName= productName;
        this.quantity = quantity;
    }

    public OrderLineForm(OrderLine orderLine){
        this.orderLineId= orderLine.getOrderLineId();
        this.productId= orderLine.getProduct().getId();
        this.quantity= orderLine.getAmount();
        this.productName= orderLine.getProduct().getName();
    }

    public long getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
