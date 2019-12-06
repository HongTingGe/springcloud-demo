package com.ght.order_service.service;

import com.ght.order_service.fallback.ProductClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service",fallback = ProductClientFallback.class)
public interface ProductClient {

    /**
     *这里的@RequestParam("id")会加到/product/find？id=xxx中
     * 如果是@RequestParam("sid"),则/product/find？sid=xxx,然后参数要跟product_service中的findProductById（）一致
     * @param id
     * @return
     */
    @GetMapping("/product/find")
    public String findProductById(@RequestParam("id") int id);

}
