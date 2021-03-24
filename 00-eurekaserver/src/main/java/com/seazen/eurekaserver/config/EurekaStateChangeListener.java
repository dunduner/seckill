package com.seazen.eurekaserver.config;


import com.netflix.appinfo.InstanceInfo;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRenewedEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaRegistryAvailableEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaServerStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by wuweifeng on 2017/10/10.
 */
@Component
public class EurekaStateChangeListener {

    @EventListener
    public void listen(EurekaInstanceCanceledEvent eurekaInstanceCanceledEvent) {
        //服务断线事件
        String appName = eurekaInstanceCanceledEvent.getAppName();
        String serverId = eurekaInstanceCanceledEvent.getServerId();
        System.out.println("appName："+appName+",serverId:"+serverId+"断线了！！！！！！！！！！！！！！！");
    }

    @EventListener
    //服务注册事件
    public void listen(EurekaInstanceRegisteredEvent event) {
        InstanceInfo instanceInfo = event.getInstanceInfo();
        System.out.println(instanceInfo.getHostName()+"|"+instanceInfo.getPort()+"|"+instanceInfo.getAppName()+"注册了！");
    }

    @EventListener
    //续约事件
    public void listen(EurekaInstanceRenewedEvent event) {
        String appName = event.getAppName();
        String serverId = event.getServerId();
        System.out.println("appName："+appName+",serverId:"+serverId+"续约了！！！！！！！！！！！！！！！");
    }

    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        //Eureka注册中心启动事件
    }

    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        //Eureka Server启动事件
        System.out.println("Eureka Server启动事件!!!");
    }
}