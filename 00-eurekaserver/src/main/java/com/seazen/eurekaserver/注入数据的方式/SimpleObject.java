package com.seazen.eurekaserver.注入数据的方式;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


@Component
public class SimpleObject {


    @Value("注入普通字符串")
    private String normal;

    //关于属性的KEY可以查看System类说明
    @Value("#{systemProperties['java.version']}")//-->使用了SpEL表达式
    private String systemPropertiesName; // 注入操作系统属性

    @Value("#{T(java.lang.Math).random()*80}")//获取随机数
    private double randomNumber; //注入表达式结果

    @Value("#{1+2}")
    private double sum; //注入表达式结果 1+2的求和

    @Value("classpath:os.yaml")
    private Resource resourceFile; // 注入文件资源

    @Value("http://www.baidu.com")
    private Resource testUrl; // 注入URL资源

    /**
     * 注入某个bean的某个成员变量
     */
    @Value("#{st.name}")
    private String studentName;

    @Override
    public String toString() {
        return "SimpleObject{" +
                "normal='" + normal + '\'' +
                ", systemPropertiesName='" + systemPropertiesName + '\'' +
                ", randomNumber=" + randomNumber +
                ", sum=" + sum +
                ", resourceFile=" + resourceFile +
                ", testUrl=" + testUrl +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
