package cn.yunhe.seckill.config;

import com.netflix.client.ClientFactory;
import com.netflix.client.config.IClientConfig;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.converters.Auto;
import com.netflix.loadbalancer.*;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.ribbon.LBClient;
import feign.ribbon.LBClientFactory;
import feign.ribbon.RibbonClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.support.SpringMvcContract;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * @program: ryx_springcloud
 *
 * @description:
 *
 * @author: renyuanxin
 *
 * @create: 2019-02-14 09:39
 **/
//@Component
public class Config {

    @Autowired
    private ConfigService configService;

    private static Config config;

    public void setUserInfo(ConfigService configService) {
        this.configService = configService;
    }

    @PostConstruct
    public void init() {
        config = this;
        config.configService = this.configService;

    }


    static {
        try {
            ConfigurationManager.loadPropertiesFromResources("properties/sample-client.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ProductService productService(){
        RibbonClient.builder().lbClientFactory(new LBClientFactory() {
            @Override
            public LBClient create(String clientName) {
                IClientConfig config = ClientFactory.getNamedConfig(clientName);
                ILoadBalancer lb = ClientFactory.getNamedLoadBalancer(clientName);
                ZoneAwareLoadBalancer zb = (ZoneAwareLoadBalancer) lb;
                zb.setRule(zoneAvoidanceRule());
                return LBClient.create(lb, config);
            }
        }).build();
        ProductService service = Feign.builder().encoder(new JacksonEncoder())
                .contract(new SpringMvcContract())
//                .decoder(new JacksonDecoder()).target(ProductService.class, config.configService.getConfigByPrimaryKey("feignClientUrl").getValue());
                .decoder(new JacksonDecoder()).target(ProductService.class, "http://localhost:8761");

        return  service;
}


    /**
     * Ribbon负载均衡策略实现
     * 使用ZoneAvoidancePredicate和AvailabilityPredicate来判断是否选择某个server，前一个判断判定一个zone的运行性能是否可用，
     * 剔除不可用的zone（的所有server），AvailabilityPredicate用于过滤掉连接数过多的Server。
     * @return
     */
    private static IRule zoneAvoidanceRule() {
        return new ZoneAvoidanceRule();
    }

    /**
     * Ribbon负载均衡策略实现
     * 随机选择一个server。
     * @return
     */
    private static IRule randomRule() {
        return new RandomRule();
    }


}