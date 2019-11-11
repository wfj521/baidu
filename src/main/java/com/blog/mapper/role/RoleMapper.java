package com.blog.mapper.role;

import com.blog.model.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface RoleMapper {

    int findRoleCount(HashMap<String, Object> params);

    List<Role> queryRole(HashMap<String, Object> params);

    @Delete("delete from sys_role where id=#{value}")
    void delRole(String id);
    @Insert("insert into sys_role(name) values(#{name})")
    void addRole(String name);
}
