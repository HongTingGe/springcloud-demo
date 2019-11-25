package com.ght.order_service.service.impl;

import com.ght.order_service.domain.ProductOrder;
import com.ght.order_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Object save(int userId, int productId) {
        Map<String,Object> productMap = restTemplate.getForObject("http://product-service/product/"+productId,Map.class);
        ProductOrder productOrder = new ProductOrder();
        productOrder.setProductId(productId);
        productOrder.setProductName(productMap.get("name").toString());
        productOrder.setCreateTime(new Date());
        productOrder.setTradeNo(UUID.randomUUID().toString());
        productOrder.setUserId(userId);
        productOrder.setUserName("ght");
        return productOrder;
    }
}
