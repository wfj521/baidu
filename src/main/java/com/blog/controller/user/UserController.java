package com.blog.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blog.model.Role;
import com.blog.model.Tree;
import com.blog.model.User;
import com.blog.service.user.UserService;
import com.blog.utils.AliyunOSSUtil;
import com.blog.utils.HttpClientUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;


    @Bean(name = "userService")
    public UserService getUserService(){
        return userService;
    }

    @RequestMapping("/")
    @ResponseBody
    public String toList(){
        List<String> userName = userService.findUserName();
        return null;
    }

    //登录
    @RequestMapping("/login")
    @ResponseBody
    public String login(User user){

        // 拿到subject对象 调用login方法 跳转到realm对象认证方法中
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken upt = new UsernamePasswordToken(user.getUserCode(), user.getPassword());

        try{
            // 将账号密码传进去
            subject.login(upt);
            return "登录成功";
        } catch (UnknownAccountException uae){
            return "账号不存在";
        } catch (IncorrectCredentialsException ice){
            return "密码错误";
        } catch (AuthenticationException e){
            return "登录失败，网络错误";
        }
    }

    //树
    @RequestMapping("/queryTree")
    @ResponseBody
    public List<Tree> queryTree(){
        //获取登录用户的信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        List<Tree> trees = userService.selectTreeList(user.getUserId());
        return trees;
    }

    //天气接口
    @RequestMapping("/queryWeather")
    @ResponseBody
    public Map queryWeather(){
        //天气接口
        String url =  "http://t.weather.sojson.com/api/weather/city/101110101";
        HashMap<String, Object> params = new HashMap<String, Object>();
        String string = HttpClientUtil.get(url, params);
        //System.out.println(string);
        JSONObject jsonObj = JSON.parseObject(string);
        JSONObject data = jsonObj.getJSONObject("data");
        JSONArray forecast = data.getJSONArray("forecast");
        JSONObject today = forecast.getJSONObject(0);
        String high = today.getString("high");
        //System.out.println(high);
        String low = today.getString("low");
        //System.out.println(low);
        String notice = today.getString("notice");
        String type = today.getString("type");
        Map mapResult = new HashMap<String,Object>();

        mapResult.put("high",high);
        mapResult.put("low",low);
        mapResult.put("notice",notice);
        mapResult.put("type",type);
        return mapResult;
    }

    //分页条查List
    @RequestMapping("/queryUser")
    @ResponseBody
    public Map<String, Object> queryUser(Integer page, Integer rows, User user){
        String userName = user.getUserName();
        System.out.println(userName);
        Map<String, Object> result = userService.getUserList(page,rows,user);
        return result;
    }

    //批量删除用户
    @RequestMapping("delUser")
    @ResponseBody
    public String delUser(String[] ids){
        userService.delUser(ids);
        return "删除成功";
    }

    //图片上传
    @RequestMapping(value = "uploadImg",method = RequestMethod.POST)
    @ResponseBody
    public String uploadBlog(MultipartFile file){
        String uploadUrl="";



        try {

            if(null != file){
                String filename = file.getOriginalFilename();
                if(!"".equals(filename.trim())){
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    //上传到OSS
                    uploadUrl = AliyunOSSUtil.upload(newFile);
                    System.out.println("___________");
                    System.out.println(uploadUrl);
                    System.out.println("___________");
                }

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        uploadUrl="https://blogimg-group2.oss-cn-chengdu.aliyuncs.com/"+uploadUrl;
        return JSON.toJSONString(uploadUrl);
    }

    //新增user
    @RequestMapping("/add")
    public String add(@Valid User user, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            List<Role> roleList = userService.getRoleList();
            model.addAttribute("roleList", roleList);
            return "user/addUser";
        }
        String password = user.getPassword();
        String userCode = user.getUserCode();
        user.setSalt(userCode);

        String hashAlgorithmName = "MD5";//加密方式 MD5  SHA
        Object crdentials = password;//密码原值
        Object salt = user.getSalt();//盐值 (用户账号、UUID、时间戳、随机字母 作为盐值，盐值必须保持唯一)
        ByteSource saltByte = ByteSource.Util.bytes(salt);
        int hashIterations = 1024;//加密1024次  散列次数
        Object result = new SimpleHash(hashAlgorithmName,crdentials,saltByte,hashIterations);
        //result 加密后的值设置为数据库中的密码
        user.setPassword(result.toString());

        userService.saveUser(user);
        //System.out.println(user);
        return "redirect:../page/toUserList";
    }

    //修改user
    @RequestMapping("/edit")
    public String edit(User user){
        userService.edit(user);
        return  "redirect:../page/toUserList";
    }

}
