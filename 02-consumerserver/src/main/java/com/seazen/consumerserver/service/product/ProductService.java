package com.seazen.consumerserver.service.product;


import com.seazen.consumerserver.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Service
@FeignClient(value = "product-server",fallbackFactory = ProductServiceFallBack.class)
public interface ProductService {

    @RequestMapping(value="/products")
    List<Product> getAlllist();

    @RequestMapping(value="/products/{itemCode}")
    Product getProductByitemCode(@PathVariable  String itemCode);
}
