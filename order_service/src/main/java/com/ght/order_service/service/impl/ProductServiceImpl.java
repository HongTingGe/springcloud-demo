package com.ght.order_service.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ght.order_service.domain.ProductOrder;
import com.ght.order_service.service.ProductClient;
import com.ght.order_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private LoadBalancerClient balancerClient;

    @Override
    public Object saveByRibbon(int userId, int productId) {
//        Map<String,Object> productMap = restTemplate.getForObject("http://product-service/product/"+productId,Map.class);
        ServiceInstance serviceInstance = balancerClient.choose("product-service");
        String url = String.format("http://%s:%s/product/"+productId,serviceInstance.getHost(),serviceInstance.getPort());
        RestTemplate restTemplate = new RestTemplate();
        Map<String,Object> productMap = restTemplate.getForObject(url,Map.class);

        ProductOrder productOrder = new ProductOrder();
        productOrder.setProductId(productId);
        productOrder.setProductName(productMap.get("name").toString());
        productOrder.setCreateTime(new Date());
        productOrder.setTradeNo(UUID.randomUUID().toString());
        productOrder.setUserId(userId);
        productOrder.setUserName("ght");
        return productOrder;
    }

    @Override
    public ProductOrder saveByFeign(int userId, int productId) {
        String res = productClient.findProductById(productId);

        JSONObject jsonObject = JSON.parseObject(res);

        ProductOrder productOrder = new ProductOrder();
        productOrder.setUserId(userId);
        productOrder.setUserName("ght");
        productOrder.setProductId(productId);
        productOrder.setProductName(jsonObject.getString("name"));
        productOrder.setPrice(Integer.parseInt(jsonObject.getString("price")));
        productOrder.setTradeNo(UUID.randomUUID().toString());
        productOrder.setCreateTime(new Date());



        return productOrder;
    }
}
