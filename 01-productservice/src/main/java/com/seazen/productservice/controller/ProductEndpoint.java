package com.seazen.productservice.controller;


import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.seazen.productservice.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;

import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductEndpoint {
	protected Logger logger = LoggerFactory.getLogger(ProductEndpoint.class);

	@Autowired
	private LoadBalancerClient loadBalancerClient;
	@Autowired
	private EurekaClient eurekaClient;

	@RequestMapping(method = RequestMethod.GET)
	public List<Product> list() {
		return this.buildProducts();
	}

	@RequestMapping(value = "/{itemCode}", method = RequestMethod.GET)
	public Product detail(@PathVariable String itemCode) {
		List<Product> products = this.buildProducts();
		for (Product product : products) {
			if (product.getItemCode().equalsIgnoreCase(itemCode))
				return product;
		}
		return null;
	}

	@GetMapping(value = "/helloEx")
	public String helloEx() {
		ServiceInstance instance = this.loadBalancerClient.choose("PRODUCT-SERVER");
		System.out.println(instance.getUri());
		System.out.println(instance.getHost());
		System.out.println(instance.getPort());
		System.out.println(instance.getServiceId());
		URI helloUri = URI.create(String.format("http://%s:%s/products", instance.getHost(), instance.getPort()));
//		logger.info("Target service uri = {}. ", helloUri.toString());
		return "Target service uri =  "+helloUri.toString() ;
//		return new RestTemplate().getForEntity(helloUri, String.class).getBody();
	}

	@RequestMapping(value = "/getMicroServices", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	public String testController1(){
		StringBuilder sb = new StringBuilder();
		Applications applications = eurekaClient.getApplications();
		for(Application application : applications.getRegisteredApplications()) {
			System.out.println(application.getName());
			try {
				//通过swagger查询服务接口的接口来获取该服务节点下的所有服务接口明细
//				String rsp = restTemplate.getForEntity("http://" + application.getName() + "/v2/api-docs", String.class).getBody();
				//使用fastjson进行解析
//				JSONObject apiObject = JSON.parseObject(rsp);
//				JSONObject pathsObject = apiObject.getJSONObject("paths");
//				for (Map.Entry<String, Object> path : pathsObject.entrySet()) {
//					System.out.println(path.getKey());
//				}
//				sb.append(rsp);
			}catch(Exception ex){
				//这里需要注意对于没有使用swagger的服务是无法调用swagger接口的，会抛出异常，需要对异常捕获后继续执行
				continue;
			}
		}
		return applications.toString();
	}


	//创建一个商品的集合对象
	protected List<Product> buildProducts() {
		List<Product> products = new ArrayList<>();
		products.add(new Product("item-1", "测试商品-1", "TwoStepsFromJava", 100));
		products.add(new Product("item-2", "测试商品-2", "TwoStepsFromJava", 200));
		products.add(new Product("item-3", "测试商品-3", "TwoStepsFromJava", 300));
		products.add(new Product("item-4", "测试商品-4", "TwoStepsFromJava", 400));
		products.add(new Product("item-5", "测试商品-5", "TwoStepsFromJava", 500));
		products.add(new Product("item-6", "测试商品-6", "TwoStepsFromJava", 600));
		return products;
	}
}