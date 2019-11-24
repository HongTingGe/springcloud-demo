package com.ght.product_service.domain;

import lombok.Data;

import java.io.Serializable;
@Data
public class Product implements Serializable {

    private static final long serialVersionUID = -5972619117223688361L;

    private int id;

    private String name;

    private int price;

    private int store;

    public Product(){}

    public Product(int id,String name,int price,int store){
        this.id = id;
        this.name = name;
        this.price = price;
        this.store = store;
    }
}
