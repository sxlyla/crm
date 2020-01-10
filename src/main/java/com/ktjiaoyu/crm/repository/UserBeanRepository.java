package com.ktjiaoyu.crm.repository;

import com.ktjiaoyu.crm.entity.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBeanRepository extends JpaRepository<UserBean, Long> {
    public UserBean findUserByUsrName(String usrName);
}
