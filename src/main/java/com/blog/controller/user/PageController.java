package com.blog.controller.user;


import com.blog.model.Role;
import com.blog.model.User;
import com.blog.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/page")
public class PageController {

    @Autowired
    private UserService userService;

    //跳转到登录页面
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "index";
    }

    //跳转到主页面
    @RequestMapping("/toMain")
    public String toMain(Model model){
        return "tree/main";
    }

    //跳转到userList页面
    @RequestMapping("/toUserList")
    public String toUserList(Model model){
        return "user/userList";
    }

    //跳转到user新增页面
    @RequestMapping("/toAddUser")
    public String toAddUser(Model model){
        List<Role> roleList = userService.getRoleList();
        model.addAttribute("roleList", roleList);
        return "user/addUser";
    }

    //跳转到user修改页面
    @RequestMapping("/toEdit")
    public String toEdit(Model model, Integer userId){
        //修改的对象
        User user = userService.getUserInfo(userId);
        List<Role> roleList = userService.getRoleList();
        model.addAttribute("roleList", roleList);
        model.addAttribute("user", user);
        return "user/editUser";
    }

    //跳转到角色权限页面
    @RequestMapping("/toRoleList")
    public String toRoleList(){

        return "role/roleList";
    }

    //跳转到role新增页面
    @RequestMapping("/toAddRole")
    public String toAddRole(){
        return "role/addRole";
    }


}
