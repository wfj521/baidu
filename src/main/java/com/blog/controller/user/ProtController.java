package com.blog.controller.user;

import com.blog.model.User;
import com.blog.service.user.UserService;
import com.blog.utils.FilePortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("prot")
public class ProtController {

    @Autowired
    private UserService userService;

    //导出表格
    @RequestMapping("filePort")
    @ResponseBody
    public void filePort(HttpServletResponse response) {
        //导出的表名
        String title = "导出用户表数据";
        // 表中第一行表头字段
        String[] headers = {"主键id", "账号","密码","姓名","图片路径","电话号码","创建时间","盐值"};
        // 实际数据结果集
        List<User> users = userService.findUserList();
        // 具体需要写入excel需要哪些字段，这些字段取自UserReward类，也就是上面的实际数据结果集的泛型
        List<String> listColumn = Arrays.asList("userId", "userCode","password","userName","userImage","mobile","createTime","salt");
        try {
            FilePortUtil.exportExcel(response, title, headers, users, listColumn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
