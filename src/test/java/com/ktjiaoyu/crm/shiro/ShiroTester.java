package com.ktjiaoyu.crm.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class ShiroTester {
    @Test
    public void testLoginAndLogout() {
        //通过shiro.ini配置文件创建Realm
        IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
        //创建SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);
        //将securityManager设置当前的运行环境中
        //SecurityUtils：是shiro的一个工具类。
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //从SecurityUtils里边创建一个subject
        // 通过SecurityUtils获取getSubject 得到一个返回值
        Subject subject = SecurityUtils.getSubject();
        //在认证提交前准备token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "123456");
        //执行认证提交
        try {
            //subject的返回值里有两个方法logout：登出 login：登入。
            //这里我们使用登录方法，通过方法我们可以看到需要一个返回值：AuthenticationToken的类型，
            //又因为AuthenticationToken是一个接口，所以我们使用他下面的UsernamePasswordToken的实现类来写
            subject.login(token);
        } catch (UnknownAccountException uae) {
            System.out.println("未知账户异常" + uae);
        } catch (IncorrectCredentialsException ice) {
            System.out.println("密码错误异常" + ice);
        } catch (LockedAccountException lae) {
            System.out.println("账户锁定异常" + lae);
        } catch (ExcessiveAttemptsException eae) {
            System.out.println("过多尝试次数异常" + eae);
        } catch (AuthenticationException ae) {
            System.out.println("其它异常" + ae);
        }

        //是否认证通过
        boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("是否认证通过：" + isAuthenticated);
        //退出操作
        subject.logout();
        //是否认证通过
        isAuthenticated = subject.isAuthenticated();
        System.out.println("是否认证通过：" + isAuthenticated);
    }
}
