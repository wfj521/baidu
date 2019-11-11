package com.blog.service.user;

import com.blog.model.Role;
import com.blog.model.Tree;
import com.blog.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<String> findUserName();

    List<String> selectPowerKeyList(Integer userId);

    User selectUserInfoByCode(String userCode);

    Map<String, Object> getUserList(Integer page, Integer rows, User user);

    void delUser(String[] ids);

    List<Role> getRoleList();

    void saveUser(User user);

    User getUserInfo(Integer userId);

    void edit(User user);

    List<Tree> selectTreeList(Integer userId);

    List<User> findUserList();
}
