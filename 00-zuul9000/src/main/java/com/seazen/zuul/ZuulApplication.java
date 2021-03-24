package com.seazen.zuul;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableZuulProxy // 开启zuul代理模式
@EnableDiscoveryClient
//@ComponentScan(basePackages = {"com.seazen.zuul.zuulfilter","com.seazen.zuul.config"})//扫描过滤器包
public class ZuulApplication {

    public static void main(String[] args) {
		initFlowRules();;//限流的业务逻辑
        SpringApplication.run(ZuulApplication.class, args);
    }

	//解决跨域过滤器
	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.setMaxAge(3600L);
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}


	//限流的业务逻辑
	//创建规则
	private static void initFlowRules(){
		List<FlowRule> rules = new ArrayList<FlowRule>();

		FlowRule rule1 = new FlowRule();
		rule1.setResource("SentinelService.success");  //资源名称， 自定义
//		rule1.setResource("helloworld");  //资源名称， 自定义
		//限流的类型
		rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
		//qps
		rule1.setCount(2);//一分钟内发生的异常数量
		rules.add(rule1);
		FlowRuleManager.loadRules(rules);
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


