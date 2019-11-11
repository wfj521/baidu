package com.blog.controller.blog;

import com.alibaba.fastjson.JSON;
import com.blog.model.Blog;
import com.blog.model.TypeBlog;
import com.blog.model.Type;
import com.blog.service.blog.BlogService;
import com.blog.utils.AliyunOSSUtil;
import com.blog.utils.RedisCommonConf;
import com.blog.utils.RedisUtil;
import net.minidev.json.JSONArray;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RedisTemplate redisTemplate;


    @RequestMapping("toBlogExamine")
    public String toBlogExamine(){

        return "blog/blogExamineList";
    }

    /*
     *   跳转到展示文章页面
     * */
    @RequestMapping("toBlog")
    public String toBlog(Model model) {
        List<Type> typeList = new ArrayList<Type>();
        //1、定义一个key
        String key = RedisCommonConf.QUERY_BLOG_TYPE_LIST;
        //2、判断redis缓存中是否存在key
        if (redisUtil.hasKey(key)) {
            //a、存在：走缓存、获取、return
            typeList = (List<Type>) redisUtil.get(key);
        } else {
            //b、不存在：走数据、获取、存入缓存、return
            typeList = blogService.getTypeBlogStatusList();
            //存入缓存
            redisUtil.set(key, typeList);
        }
        model.addAttribute("typeList", typeList);
        return "blog/blogList";
    }

    /*
     *   返回到展示文章页面，返回map
     * */
    @RequestMapping("queryBlogList")
    @ResponseBody
    public Map<String, Object> queryBlogList(int page, int rows, Blog blog) {
        Map<String, Object> map = new HashMap<>();
        String key = RedisCommonConf.QUERY_BLOG_LIST + "_" + page + "_" + rows;
        if (blog != null) {
            if (!StringUtils.isEmpty(blog.getTitle())) {
                key += "_" + blog.getTitle();
            }
            if (blog.getStartcreateTime() != null && !blog.getStartcreateTime().equals("")) {
                key += "_" + blog.getStartcreateTime();
            }
            if (blog.getEndcreateTime() != null && !blog.getEndcreateTime().equals("")) {
                key += "_" + blog.getEndcreateTime();
            }
            if (blog.getStartupdateTime() != null && !blog.getStartupdateTime().equals("")) {
                key += "_" + blog.getStartupdateTime();
            }
            if (blog.getEndupdateTime() != null && !blog.getEndupdateTime().equals("")) {
                key += "_" + blog.getEndupdateTime();
            }
            if (blog.getTypeId() != null && !"".equals(blog.getTypeId()) && blog.getTypeId() != -1) {
                key += "_" + blog.getTypeId();
            }
        }

     /*  map= (Map<String, Object>) redisUtil.get(key);
        if (!redisUtil.hasKey(key)){*/
        map = blogService.queryBlogList(page, rows, blog);
       /*     redisUtil.set(key,map);
        }*/
        return map;
    }

    @RequestMapping("updateBlogComment")
    @ResponseBody
    public void updateBlogComment(Blog blog) {
        blog.setUpdateTime(new Date());
        blogService.updateBlogComment(blog);

    }

    /*
     *   跳转到新增文章页面
     * */
    @RequestMapping("toAddBlog")
    public String toAddBlog(Model model) {
        List<Type> typeList = new ArrayList<Type>();
        //1、定义一个key
        String key = RedisCommonConf.QUERY_BLOG_TYPE_LIST;
        //2、判断redis缓存中是否存在key
        if (redisUtil.hasKey(key)) {
            //a、存在：走缓存、获取、return
            typeList = (List<Type>) redisUtil.get(RedisCommonConf.QUERY_BLOG_TYPE_LIST);
        } else {
            //b、不存在：走数据、获取、存入缓存、return
            typeList = blogService.getTypeBlogStatusList();
            //存入缓存
            redisUtil.set(key, typeList);
        }
        model.addAttribute("typeList", typeList);

        return "blog/addBlog";

    }

    /*
     *   没ID是新增，有ID 是修改
     * */
    @RequestMapping("saveBlog")
    @ResponseBody
    public String saveBlog(Blog blog) {
        String key = RedisCommonConf.QUERY_BLOG_LIST;
        if (blog.getId() != null) {
            blog.setUpdateTime(new Date());
            blogService.updateBlog(blog);
            Set keys = redisTemplate.keys(RedisCommonConf.QUERY_BLOG_LIST + "*");
            redisTemplate.delete(keys);
            return "修改成功";
        }
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setKeywords("随便填的思数据");
        blog.setTop(1);
        blogService.saveBlog(blog);
        Set keys = redisTemplate.keys(RedisCommonConf.QUERY_BLOG_LIST + "*");
        redisTemplate.delete(keys);
        return "添加成功";
    }

    /*
     *   批量删除
     * */


    @RequestMapping("delBlogById")
    @ResponseBody
    public String delBlogById(String ids) {
        blogService.delBlogById(ids);
        Set keys = redisTemplate.keys(RedisCommonConf.QUERY_BLOG_LIST + "*");
        redisTemplate.delete(keys);
        return "删除成功";
    }

    /*
     *   回显单个对象
     * */
    @RequestMapping("queryBlogById")
    public String queryBlogById(Integer id, Model model) {
        Blog blog = blogService.queryBlogById(id);
        System.out.println(blog);
        model.addAttribute("blog", blog);
        List<Type> typeList = new ArrayList<Type>();
        //1、定义一个key
        String key = RedisCommonConf.QUERY_BLOG_TYPE_LIST;
        //2、判断redis缓存中是否存在key
        if (redisUtil.hasKey(key)) {
            //a、存在：走缓存、获取、return
            typeList = (List<Type>) redisUtil.get(RedisCommonConf.QUERY_BLOG_TYPE_LIST);
        } else {
            //b、不存在：走数据、获取、存入缓存、return
            typeList = blogService.getTypeBlogStatusList();
            //存入缓存
            redisUtil.set(key, typeList);
        }
        model.addAttribute("typeList", typeList);
        return "blog/updateBlog";
    }

    @RequestMapping("updateBlogTopById")
    public String updateBlogTopById(Blog blog) {
        blog.setTop(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis());
        blog.setUpdateTime(new Date());
        blogService.updateBlogTopById(blog);
        Set keys = redisTemplate.keys(RedisCommonConf.QUERY_BLOG_LIST + "*");
        redisTemplate.delete(keys);
        return "blog/blogList";
    }

    /*查询文章评论*/
    @RequestMapping("toCommentById")
    public ModelAndView toCommentById(Integer id) {
        return new ModelAndView("blog/commentById", "id", id);
    }


    @RequestMapping("queryCommentById")
    @ResponseBody
    public Map<String, Object> queryCommentById(Integer page, Integer rows, Integer id) {
        Map<String, Object> m = blogService.queryCommentById(page, rows, id);
        return m;
    }

    /*上传图片到oss*/
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("postFile")
    public String uploadBlog(MultipartFile file, Model model) {
        String uploadUrl = "";
        logger.info("============>文件上传");
        try {
            if (null != file) {
                String filename = file.getOriginalFilename();
                if (!"".equals(filename.trim())) {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        model.addAttribute("blogImage", "https://blogimg-group2.oss-cn-chengdu.aliyuncs.com/" + uploadUrl);
        return "blog/addBlog";
    }


    @RequestMapping("getBaseDataData")
    public List<TypeBlog> getData(HttpServletResponse response) {
        List<TypeBlog> list = blogService.queryTypeCount();
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.write(JSONArray.toJSONString(list).toString());// 饼图
        out.flush();

        return null;
    }

    @RequestMapping("toaaa")
    public String toaaa() {
        return "blog/ccc";
    }

    @RequestMapping("query")
    @ResponseBody
    public Map<String, Object> query() {
        /* List<String> list = echartsService.query();*/
        List<Map<String, Integer>> ui = blogService.query1();
        List<Map<String, Integer>> java = blogService.query22();
        List<Map<String, Integer>> ce = blogService.query3();
        List<Map<String, Integer>> sp = blogService.query4();
        List<Map<String, Integer>> re = blogService.query5();

        List<Map<String, Integer>> jilist2 = blogService.query2();
        List<Integer> classes = new ArrayList<>();
        List<Integer> uiui = new ArrayList<>();
        List<Integer> javajava = new ArrayList<>();
        List<Integer> cece = new ArrayList<>();
        List<Integer> spsp = new ArrayList<>();
        List<Integer> rere = new ArrayList<>();

        for (int i = 0; i < ui.size(); i++) {
            uiui.add(ui.get(i).get("数量"));
        }
        for (int i = 0; i < java.size(); i++) {
            javajava.add(java.get(i).get("数量"));
        }
        for (int i = 0; i < ce.size(); i++) {
            cece.add(ce.get(i).get("数量"));
        }
        for (int i = 0; i < sp.size(); i++) {
            spsp.add(sp.get(i).get("数量"));
        }
        for (int i = 0; i < re.size(); i++) {
            rere.add(re.get(i).get("数量"));
        }
        for (int i = 0; i < jilist2.size(); i++) {
            classes.add(jilist2.get(i).get("日期"));
        }


        Map<String, Object> map = new HashMap<>();
        map.put("classse", classes);
        map.put("uiui", uiui);
        map.put("cece", cece);
        map.put("spsp", spsp);
        map.put("rere", rere);
        map.put("javajava", javajava);
        return map;
    }

    @RequestMapping(value = "uploadImg", method = RequestMethod.POST)
    @ResponseBody
    public String uploadBlog(MultipartFile file) {
        String uploadUrl = "";

        logger.info("============>文件上传");
        try {

            if (null != file) {
                String filename = file.getOriginalFilename();
                if (!"".equals(filename.trim())) {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        uploadUrl = "https://blogimg-group2.oss-cn-chengdu.aliyuncs.com/" + uploadUrl;
        return JSON.toJSONString(uploadUrl);
    }


    /*
     *   查询待审核文章
     * */
    @RequestMapping("blogExamine")
    @ResponseBody
    public Map<String, Object> blogExamine(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        //String key = RedisCommonConf.QUERY_BLOG_EXAMINE + "_" + page + "_" + rows;
        map = blogService.queryBlogExamine(page, rows);
        return map;
    }

    @RequestMapping("updateBlogExamine")
    @ResponseBody
    public String updateBlogExamine(Integer id,Integer flag){
        blogService.updateBlogExamine(id,flag);
        return "审核完成";
    }


}