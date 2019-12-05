package com.ght.order_service.service;

public interface ProductService {
    public Object saveByRibbon(int userId,int productId);
    public Object saveByFeign(int userId,int productId);
}
