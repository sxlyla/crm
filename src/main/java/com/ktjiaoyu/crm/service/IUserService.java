package com.ktjiaoyu.crm.service;

import com.ktjiaoyu.crm.entity.Right;
import com.ktjiaoyu.crm.entity.Role;
import com.ktjiaoyu.crm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    public User login(String usrName, String usrPassword);

    public void saveUser(User user);//适用新增和修改

    public void deleteUser(Long usrId);

    public User getUser(Long usrId);

    //按条件进行分页查询(动态SQL)
    public Page<User> findUsers(String usrName, Long roleId, Pageable pageable);

    //根据用户名查询用户
    public User getUser(String usrName);

    //获取所有角色
    public List<Role> findAllRoles();
    //获取用户-->角色
    public Role getRoleByUser(User user);
    //保存角色
    public void saveRole(Role role);
    //获取角色
    public List<Role> findRoles(String roleName);
    //获取所有权限
    public List<Right> findAllRights();
    //获取用户-->角色-->权限
    public List<Right> findRightsByRole(Role role);

}
