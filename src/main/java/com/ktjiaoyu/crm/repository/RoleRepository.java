package com.ktjiaoyu.crm.repository;

import com.ktjiaoyu.crm.entity.Role;
import com.ktjiaoyu.crm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {

    public Role findRoleByUsers(User user);

    public List<Role> findRolesByRoleNameLike(String roleName);
}
