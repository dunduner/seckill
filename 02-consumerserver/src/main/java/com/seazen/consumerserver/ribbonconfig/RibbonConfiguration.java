package com.seazen.consumerserver.ribbonconfig;

import com.netflix.loadbalancer.RandomRule;
import com.seazen.consumerserver.annotation.ExcudeRibbonConfig;
import org.springframework.context.annotation.Bean;

import com.netflix.loadbalancer.IRule;

/**
 * 该类不应该在主应用程序的扫描之下，需要修改启动类的扫描配置。否则将被所有的Ribbon client共享，
 * 比如此例中：ribbonRule 对象 会共享。
 * @author yueyi2019
 *
 */
//@Configuration
//@ExcudeRibbonConfig
//public class RibbonConfiguration {
//
//	@Bean
//	public IRule ribbonRule() {
//		return new MsbRandomRule();
////		return new RandomRule();//此时已然是使用了Ribbon提供的随机算法，我们可以自定义返回该对象达到自定义负载均衡的目的
////		 打开轮训策略
////		return new RoundRobinRule();
//		//灰度发布Irule-自定义
////        return new GrayRule();
//
//	}
//
//}