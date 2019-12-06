package com.ght.order_service.controller;

import com.ght.order_service.service.ProductService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ProductService productService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/save1")
    public Object saveByRibbon(@RequestParam("user_id") int userId,@RequestParam("product_id") int productId){
        return productService.saveByRibbon(userId,productId);
    }

    @RequestMapping("/save2")
    @HystrixCommand(fallbackMethod = "saveOrderFail")
    public Map<String,Object> saveByFeign(@RequestParam("user_id") int userId,
                                          @RequestParam("product_id") int productId,
                                          HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("data",productService.saveByFeign(userId,productId));
        return map;
    }


    public Map<String,Object> saveOrderFail (int userId,int productId,HttpServletRequest request){

        String ip = request.getRemoteAddr();
        //监控报警
        String saveOrderKey = "order:save:"+userId;

        String sendValue = stringRedisTemplate.opsForValue().get(saveOrderKey);

        new Thread(()->{
            if (StringUtils.isBlank(sendValue)){
                //发送短信告警

                System.out.println("紧急通知,用户下单失败,请尽快查找原因！ip地址是:"+ip);
                stringRedisTemplate.opsForValue().set(saveOrderKey,"save-order-fail",20, TimeUnit.SECONDS);
            }else {
                System.out.println("已经发送过短信,20s内不能重复发送!");
            }
        }).start();

        Map<String,Object> map = new HashMap<>();
        map.put("code",-1);
        map.put("msg","因抢购人数过多，您被挤出来了，请稍后重试");
        return map;
    }



}
