package com.seazen.zuul;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class AndroidRequestTest {
	
	
	@Test
	public void userRequest() throws JSONException {

		//盐
		String secret = "7yuijhyt45de2-zhangning";
		
		RestTemplate userTemplate = new RestTemplate();
		
		// 时间戳
		String token = "token-zn";
		Long timestamp = Calendar.getInstance().getTimeInMillis();
		//签名就是3者的加密结果，盐只有双方代码知道
		String sign = DigestUtils.sha1Hex(token + timestamp + secret);
		
		// 设置header
		HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(HttpHeaders.CONTENT_TYPE,"application/json");
//        httpHeaders.add("timestamp", timestamp+"");
//        httpHeaders.add("sign", sign);
//        httpHeaders.add("token", token);
        
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
		
		String result = userTemplate.postForEntity("http://localhost:9000/v1/xiaofei-service/employee/list", request, String.class).getBody();
		System.out.println("网关返回："+result);
		
	}
	
	
}