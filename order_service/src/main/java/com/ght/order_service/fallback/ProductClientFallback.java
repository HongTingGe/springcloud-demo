package com.ght.order_service.fallback;

import com.ght.order_service.service.ProductClient;
import org.springframework.stereotype.Component;

@Component
public class ProductClientFallback implements ProductClient {

    @Override
    public String findProductById(int id) {
        System.out.println("feign调用发生异常..");
        return null;
    }
}
