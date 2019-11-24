package com.ght.product_service.service;

import com.ght.product_service.domain.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getProducts();
    public Product getProductById(int id);
}
