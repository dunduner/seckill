package com.seazen.dynamicroutezuul.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author laizh
 * 加上这个代码
 * github的钩子就可以动态调的通了
 */
@Component
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter httpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setCharset(StandardCharsets.UTF_8);
        //禁止循环引用
        config.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
        httpMessageConverter.setFastJsonConfig(config);
        //所有序列化组件均保存在converters集合中
        //添加扩展的组件到索引0的位置,便于Spring首先尝试
        converters.add(0, httpMessageConverter);
    }
}
