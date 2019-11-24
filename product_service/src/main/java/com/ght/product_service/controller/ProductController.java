package com.ght.product_service.controller;

import com.ght.product_service.domain.Product;
import com.ght.product_service.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Value("${server.port}")
    private String port;

    @Autowired
    private ProductService productService;

    @RequestMapping("/list")
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @RequestMapping("/{id}")
    public Product getProductById(@PathVariable int id){
        Product product = new Product();
        BeanUtils.copyProperties(productService.getProductById(id),product);
        product.setName(product.getName() + " from port " + port);
        return product;
    }


}
