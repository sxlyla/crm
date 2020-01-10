package com.ktjiaoyu.crm.service.impl;

import com.ktjiaoyu.crm.entity.UserBean;
import com.ktjiaoyu.crm.repository.UserBeanRepository;
import com.ktjiaoyu.crm.service.IUserBeanService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
//配置缓存:当我们需要缓存的地方越来越多，
//你可以使用@CacheConfig(cacheNames = {"myCache"})注解来统一指定value的值，
//这时可省略value，
// 如果你在你的方法依旧写上了value，那么依然以方法的value值为准
public class UserBeanServiceImpl implements IUserBeanService {
    @Resource
    private UserBeanRepository userBeanRepository;

    //配置缓存:注解会先查询是否已经有缓存，有会使用缓存，没有则会执行方法并缓存
    //此处的value是必需的，它指定了你的缓存存放在哪块命名空间。
    //keyGenerator:缓存数据时key生成策略
    @Cacheable(value = "getUser", keyGenerator = "keyGenerator")
    public UserBean getUser(String usrName) {
        return userBeanRepository.findUserByUsrName(usrName);
    }
}
