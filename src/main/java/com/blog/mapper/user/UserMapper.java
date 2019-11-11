package com.blog.mapper.user;

import com.blog.model.Role;
import com.blog.model.Tree;
import com.blog.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserMapper {


    List<String> findUserName();

    List<String> selectPowerKeyList(Integer userId);

    User selectUserInfoByCode(String userCode);

    int findUserCount(HashMap<String, Object> params);

    List<User> findUserList(HashMap<String, Object> params);

    void delUser(String id);

    void delUserRole(String id);

    List<Role> getRoleList();

    void saveUser(User user);

    void addUserRole(@Param("userId") String userId,@Param("arrId")String[] arrId);

    User getUserInfo(Integer userId);

    void edit(User user);

    List<Tree> queryTreeList(Integer id, Integer userId);

    List<User> findUserList2();
}
