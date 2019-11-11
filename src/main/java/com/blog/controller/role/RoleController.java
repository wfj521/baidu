package com.blog.controller.role;

import com.blog.model.Role;
import com.blog.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //分页条查role数据
    @RequestMapping("/queryrole")
    @ResponseBody
    public Map<String, Object> queryRole(Integer page, Integer rows, Role role){
        Map<String, Object> list=roleService.queryRole(page,rows,role);
        return list;
    }

    //批量删除role
    @RequestMapping("delRole")
    @ResponseBody
    public String delRole(String[] ids){
            roleService.delRole(ids);
        return "删除成功";
    }

    //新增角色
    @RequestMapping("/addRole")
    public String addRole(String name){
        roleService.addRole(name);
        return "redirect:../page/toRoleList";
    }

    @RequestMapping("queryRoleId")
    public String toTree(Integer id, Model model){
        model.addAttribute("id",id);
        return "role/nopowerr";
    }

}
