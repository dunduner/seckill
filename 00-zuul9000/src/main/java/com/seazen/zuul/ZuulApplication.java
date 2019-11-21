package com.seazen.zuul;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableZuulProxy // 开启zuul代理模式
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.seazen.zuul.zuulfilter"})//扫描过滤器包
public class ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}

//	@Bean
//	public PreTypeZuulFilter preTypeZuulFilter() {
//		return new PreTypeZuulFilter();
//	}
	/**
	 * 自定义路由规则
	 */
//	@Bean
//	public PatternServiceRouteMapper serviceRouteMapper(){
//		return new PatternServiceRouteMapper(
//				"(?<name>^.+)-(?<version>v.+$)",
//				"${version}/${name}");
//	}
}


