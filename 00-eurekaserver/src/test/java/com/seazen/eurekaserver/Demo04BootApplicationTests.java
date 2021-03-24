package com.seazen.eurekaserver;

import com.seazen.eurekaserver.注入数据的方式.LoadByProperties;
import com.seazen.eurekaserver.注入数据的方式.SimpleObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo04BootApplicationTests {

    @Autowired
    private SimpleObject so;

    @Autowired
    private LoadByProperties loadPropsTest;

    @Test
    public void contextLoads()  {
        System.out.println(so);
        System.out.println(loadPropsTest.getBookName());
        System.out.println(loadPropsTest.getAuthor());
    }
}