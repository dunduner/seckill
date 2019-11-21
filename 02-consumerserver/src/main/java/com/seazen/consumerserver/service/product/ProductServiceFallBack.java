package com.seazen.consumerserver.service.product;


import com.seazen.consumerserver.entity.Product;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ProductServiceFallBack  implements FallbackFactory<ProductService> {

    @Override
    public ProductService create(Throwable throwable) {
        return new ProductService() {
            @Override
            public List<Product> getAlllist() {
                // TODO Auto-generated method stub
                System.out.println("getAlllist-类级别的服务熔断");
                return null;
            }

            @Override
            public Product getProductByitemCode(String itemCode) {
                Product product = new Product();
                product.setName("zn类级别的服务熔断");
                // TODO Auto-generated method stub
                System.out.println("类级别的服务熔断");
                return product;
            }
        };
    }
}
