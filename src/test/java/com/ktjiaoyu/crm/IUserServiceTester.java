package com.ktjiaoyu.crm;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ktjiaoyu.crm.entity.User;
import com.ktjiaoyu.crm.entity.UserBean;
import com.ktjiaoyu.crm.service.IUserBeanService;
import com.ktjiaoyu.crm.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IUserServiceTester {
    @Resource
    private IUserService userBeanService;

    @Test
    public void testGetUser() {
        User user = userBeanService.getUser("bdqn");
        System.out.println("对象输出>>" + user);
        //使用缓存,第二次数据是从redis中获取的
        User user1 = userBeanService.getUser("bdqn");
        System.out.println("对象输出>>" + user1);
    }

}
