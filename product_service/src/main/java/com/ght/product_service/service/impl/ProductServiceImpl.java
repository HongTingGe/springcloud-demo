package com.ght.product_service.service.impl;

import com.ght.product_service.domain.Product;
import com.ght.product_service.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Map<Integer,Product> mapDao = new HashMap<>();

    static {
        mapDao.put(1,new Product(1,"iPhone6",600,60));
        mapDao.put(2,new Product(2,"iPhone7",700,70));
        mapDao.put(3,new Product(3,"iPhone8",800,80));
        mapDao.put(4,new Product(4,"iPhoneX",1000,100));
    }

    @Override
    public List<Product> getProducts() {
        Collection<Product> collection = mapDao.values();
        return new ArrayList<>(collection);
    }

    @Override
    public Product getProductById(int id) {
        return mapDao.get(id);
    }
}
