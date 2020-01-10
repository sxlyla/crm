package com.ktjiaoyu.crm.repository;

import com.ktjiaoyu.crm.entity.Right;
import com.ktjiaoyu.crm.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RightRepository extends JpaRepository<Right, String> {
    public List<Right> findRightsByRoles(Role role);
}
