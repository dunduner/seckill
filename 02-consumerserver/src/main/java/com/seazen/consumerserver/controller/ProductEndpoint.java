package com.seazen.consumerserver.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.seazen.consumerserver.entity.Product;
import com.seazen.consumerserver.service.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductEndpoint {
    protected Logger logger = LoggerFactory.getLogger(ProductEndpoint.class);

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/list")
    public List<Product> list() {
        return productService.getAlllist();
    }

    @RequestMapping(value = "/{itemCode}", method = RequestMethod.GET)
    public Product detail(@PathVariable String itemCode) {
        return productService.getProductByitemCode(itemCode);
    }

}
