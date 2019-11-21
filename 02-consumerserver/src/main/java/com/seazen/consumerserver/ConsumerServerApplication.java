package com.seazen.consumerserver;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableHystrix // 注解表示开启断路器
@EnableCircuitBreaker
@EnableHystrixDashboard//开启Hystrix监控
@EnableDiscoveryClient
@SpringBootApplication
//@EnableFeignClients(basePackages = "com.yunhe.product_api.service.**")
@EnableFeignClients
public class ConsumerServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerServerApplication.class, args);
	}
	// 指定ribbon的轮询策略
	public IRule loadBalanceIRule() {
		// 打开随机策略
		return new RandomRule();
	}

	// @EnableDiscoveryClient并且加入restTemplate以消费相关的服务。
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	//hystrix监控
	//http://localhost:8080/hystrix
	//http://localhost:8080/actuator/hystrix.stream
	@Bean
	public ServletRegistrationBean getServlet(){
		HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
		registrationBean.setLoadOnStartup(1);
		registrationBean.addUrlMappings("/actuator/hystrix.stream");
		registrationBean.setName("HystrixMetricsStreamServlet");
		return registrationBean;
	}

}
