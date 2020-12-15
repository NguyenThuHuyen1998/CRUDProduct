package com.example.crud.form;

public class ReportProduct {
    private String productName;
    private int quantity;
    private double revenue;

    public ReportProduct(String productName, int quantity, double revenue) {
        this.productName = productName;
        this.quantity = quantity;
        this.revenue = revenue;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
