package com.seazen.consumerserver.gray;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.aspectj.lang.annotation.Aspect;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author zhangning
 * @date 2020/7/16
 */

public class GrayRule extends AbstractLoadBalancerRule {
    //    根据用户选出一个服务
    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        Server server = null;
        String version = "";
        //获取所有可达的服务
        List<Server> reachableServers = lb.getReachableServers();
        // 获取 当前线程的参数 用户id verion=1
        Map<String, String> map = RibbonParameters.get();
        if (map != null && map.containsKey("version")) {
            version = map.get("version");
        }
        System.out.println("当前rule version:" + version);
        //根据用户选服务
        for (int i = 0; i < reachableServers.size(); i++) {
            server = reachableServers.get(i);
            Map<String, String> metadata = ((DiscoveryEnabledServer) server).getInstanceInfo().getMetadata();
            String version1 = metadata.get("version");
            if (version.trim().equals(version1)) {
                return server;
            }
        }

        return server;

    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

}
