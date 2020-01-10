package com.ktjiaoyu.crm.service.impl;

import com.ktjiaoyu.crm.entity.Role;
import com.ktjiaoyu.crm.repository.RoleRepository;
import com.ktjiaoyu.crm.service.IRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements IRoleService {
    @Resource
    private RoleRepository roleRepository;
    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
}
