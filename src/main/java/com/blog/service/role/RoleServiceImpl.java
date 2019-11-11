package com.blog.service.role;

import com.blog.mapper.role.RoleMapper;
import com.blog.model.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public Map<String, Object> queryRole(Integer page, Integer rows, Role role) {
        //构件总返回
        HashMap<String, Object> result = new HashMap<>();
        //构件搜索map
        HashMap<String, Object> params = new HashMap<>();
        params.put("role", role);
        //查询总条数
        int count = roleMapper.findRoleCount(params);
        //查询分页列表
        params.put("start", (page-1)*rows);
        params.put("end", rows);
        List<Role> users = roleMapper.queryRole(params);
        result.put("total", count);
        result.put("rows", users);
        return result;
    }

    @Override
    public void delRole(String[] ids) {
        for (String id:
             ids) {
            roleMapper.delRole(id);
        }

    }

    @Override
    public void addRole(String name) {
        roleMapper.addRole(name);
    }
}
