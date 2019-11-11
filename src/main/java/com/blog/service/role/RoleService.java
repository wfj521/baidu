package com.blog.service.role;

import com.blog.model.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {
    Map<String, Object> queryRole(Integer page, Integer rows, Role role);

    void delRole(String[] ids);

    void addRole(String name);
}
