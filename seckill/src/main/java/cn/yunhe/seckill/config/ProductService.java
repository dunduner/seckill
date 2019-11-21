package cn.yunhe.seckill.config;

import cn.yunhe.seckill.entity.Seckill;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author renyuanxin
 * http://localhost:2100
 * @Title: ProductService
 * @ProjectName ryx_springcloud
 * @Description: TODO
 * @date 2019/2/117:28
 */
@FeignClient(name = "PRODUCT-SERVER",configuration = FeignClientsConfiguration.class)
@EnableCircuitBreaker
public interface ProductService {

    /**
     * 查询商品列表
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public  List<Seckill> getLisByRyxProduct();



}