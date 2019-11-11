package com.blog.service.user;

import com.blog.mapper.user.UserMapper;
import com.blog.model.Role;
import com.blog.model.Tree;
import com.blog.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<String> findUserName() {
        return userMapper.findUserName();
    }

    @Override
    public List<String> selectPowerKeyList(Integer userId) {
        return userMapper.selectPowerKeyList(userId);
    }

    @Override
    public User selectUserInfoByCode(String userCode) {
        return userMapper.selectUserInfoByCode(userCode);
    }

    @Override
    public Map<String, Object> getUserList(Integer page, Integer rows, User user) {
        //构件总返回
        HashMap<String, Object> result = new HashMap<>();
        //构件搜索map
        HashMap<String, Object> params = new HashMap<>();
        params.put("user", user);
        //查询总条数
        int count = userMapper.findUserCount(params);
        //查询分页列表
        params.put("start", (page-1)*rows);
        params.put("end", rows);
        List<User> users = userMapper.findUserList(params);
        result.put("total", count);
        result.put("rows", users);
        return result;
    }

    @Override
    public void delUser(String[] ids) {
        for (String id:
             ids) {
            userMapper.delUser(id);
            userMapper.delUserRole(id);
        }
    }

    @Override
    public List<Role> getRoleList() {
        return userMapper.getRoleList();
    }

    @Override
    public void saveUser(User user) {
        //新增用户
        userMapper.saveUser(user);
        //新增用户角色中间表：批量新增
        String roleids = user.getUserRole();
        String[] arrId = roleids.split(",");
        String userId = user.getUserId()+"";
        userMapper.addUserRole(userId,arrId);
    }

    @Override
    public User getUserInfo(Integer userId) {
        return userMapper.getUserInfo(userId);
    }

    @Override
    public void edit(User user) {
        //修改用户表数据
        userMapper.edit(user);
        //修改用户角色表数据
            //1删除用户角色表数据
            userMapper.delUserRole(user.getUserId()+"");
            //2新增用户角色中间表：批量新增
            String roleids = user.getUserRole();
            String[] arrId = roleids.split(",");
            String userId = user.getUserId()+"";
            userMapper.addUserRole(userId,arrId);

    }

    @Override
    public List<Tree> selectTreeList(Integer userId) {
        List<Tree> treeList = queryTreeByPid(1,userId);
        return treeList;
    }

    @Override
    public List<User> findUserList() {
        return userMapper.findUserList2();
    }

    private List<Tree> queryTreeByPid(Integer id,Integer userId){
        List<Tree> treeList = userMapper.queryTreeList(id,userId);
        for (Tree tree : treeList) {
            List<Tree> nodeList = queryTreeByPid(tree.getId(),userId);
            if(nodeList == null || nodeList.size() <= 0) {
                tree.setSelectable(true);
                tree.setLeaf(true);
            } else {
                tree.setSelectable(false);
                tree.setNodes(nodeList);
            }
        }
        return treeList;
    }


}
