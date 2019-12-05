package com.ght.order_service.controller;

import com.ght.order_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/save1")
    public Object saveByRibbon(@RequestParam("user_id") int userId,@RequestParam("product_id") int productId){
        return productService.saveByRibbon(userId,productId);
    }

    @RequestMapping("/save2")
    public Object saveByFeign(@RequestParam("user_id") int userId,@RequestParam("product_id") int productId){
        return productService.saveByFeign(userId,productId);
    }



}
