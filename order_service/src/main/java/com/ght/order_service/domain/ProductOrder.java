package com.ght.order_service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ProductOrder {

    private int productId;
    private String productName;
    private String tradeNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    private Date createTime;
    private int userId;
    private String userName;

    private int price;

}
