package com.ktjiaoyu.crm.web.controller;

import com.ktjiaoyu.crm.entity.Role;
import com.ktjiaoyu.crm.entity.User;
import com.ktjiaoyu.crm.service.IRoleService;
import com.ktjiaoyu.crm.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Resource
    private IUserService userService;
    @Resource
    private IRoleService roleService;
    @RequestMapping(value = "/dologin")
    public String login(String usrName, String usrPassword, HttpServletRequest request, Map<String, Object> map, HttpSession session){
/*        User user = userService.login(usrName,usrPassword);
        if(user!=null){
            request.getSession().setAttribute("loginUser",user);
            return "main";
        }else{
            request.setAttribute("message","登录失败，用户名或密码错误！");
            return "login";
        }*/
        User user = null;
        try {
            // 此处不再处理登录,由shiro进行处理
            AuthenticationToken token = new UsernamePasswordToken(usrName, usrPassword);
            SecurityUtils.getSubject().login(token);//调用Shiro认证
            user = (User) SecurityUtils.getSubject().getPrincipal();
            //注意此处session.setAttribute中key的值
            //需要和AuthorizationInterceptor拦截器session的key一直
            session.setAttribute("loginUser", user);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            return "login";
        }
        return "main";

    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginUser");
        return "login";
    }

    @RequestMapping(value = "/user/list")
    public String list(Model model,String usrName,
                       @RequestParam(required = false,defaultValue = "0") Long roleId,
                       @RequestParam(required = false,defaultValue = "1") int pageIndex){
        Sort sort = new Sort(Sort.Direction.ASC, "usrId");
        //控制分页的辅助类，可以设置页码(从0开始！！！)、每页的数据条数、排序等
        Pageable pageable = PageRequest.of(pageIndex-1, 5, sort);
        Page<User> userPager = userService.findUsers(usrName,roleId,pageable);
        model.addAttribute("userPager",userPager);
        model.addAttribute("usrName",usrName);
        model.addAttribute("roleId",roleId);
        List<Role> roles = roleService.findAllRoles();
        model.addAttribute("roles",roles);
        return "user/list";
    }

    @RequestMapping(value = "/user/add")
    public String add(Model model){
        List<Role> roles = roleService.findAllRoles();
        model.addAttribute("roles",roles);
        return "user/add";
    }

    @RequestMapping(value = "/user/edit")
    public String edit(Long usrId,Model model){
        User user = userService.getUser(usrId);
        List<Role> roles = roleService.findAllRoles();
        model.addAttribute("user",user);
        model.addAttribute("roles",roles);
        return "user/edit";
    }

    @RequestMapping(value = "/user/save")
    public String save(User user){
        userService.saveUser(user);
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/user/del")
    @ResponseBody
    public Map del(Long usrId){
        userService.deleteUser(usrId);
        Map map = new HashMap();
        map.put("delResult","true");
        return map;
    }

    @RequestMapping(value = "/role/json")
    @ResponseBody
    public List<Role> findAllRoles(){
        List<Role> list = roleService.findAllRoles();
        return list;
    }
}
