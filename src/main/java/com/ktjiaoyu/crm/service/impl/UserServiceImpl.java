package com.ktjiaoyu.crm.service.impl;

import com.ktjiaoyu.crm.entity.Right;
import com.ktjiaoyu.crm.entity.Role;
import com.ktjiaoyu.crm.entity.User;
import com.ktjiaoyu.crm.repository.RightRepository;
import com.ktjiaoyu.crm.repository.RoleRepository;
import com.ktjiaoyu.crm.repository.UserRepository;
import com.ktjiaoyu.crm.service.IUserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private RightRepository rightRepository;

    @Override
    public User login(String usrName, String usrPassword) {
        List<User> list = userRepository.findByUsrNameAndUsrPassword(usrName, usrPassword);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long usrId) {
        userRepository.deleteById(usrId);
    }

    @Override
    public User getUser(Long usrId) {
        return userRepository.findById(usrId).get();
    }

    @Override
    public Page<User> findUsers(String usrName, Long roleId, Pageable pageable) {
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (usrName != null && !usrName.equals("")) {
                    predicates.add(criteriaBuilder.like(root.get("usrName"), "%" + usrName + "%"));
                }
                if (roleId != null && roleId.longValue() != 0l) {
                    Role role = new Role();
                    role.setRoleId(roleId);
                    predicates.add(criteriaBuilder.equal(root.get("role"), role));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return userRepository.findAll(specification, pageable);
    }

    //@Cacheable(value = "getUser", keyGenerator = "keyGenerator")
    public User getUser(String usrName) {
        return userRepository.findUserByUsrName(usrName);
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> findRoles(String roleName) {
        List<Role> list = null;
        if (roleName != null) {
            list = roleRepository.findRolesByRoleNameLike("%" + roleName + "%");
        } else {
            list = roleRepository.findAll();
        }
        return list;
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    /**
     * 根据User获得Role
     *
     * @param user
     * @return
     */
    @Override
    public Role getRoleByUser(User user) {
        return roleRepository.findRoleByUsers(user);
    }

    /**
     * 获得全部Right
     *
     * @return
     */
    @Override
    public List<Right> findAllRights() {
        return rightRepository.findAll();
    }

    /**
     * 根据Role获得Right
     *
     * @param role
     * @return
     */
    @Override
    public List<Right> findRightsByRole(Role role) {
        return rightRepository.findRightsByRoles(role);
    }
}
