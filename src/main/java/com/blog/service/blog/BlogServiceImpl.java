package com.blog.service.blog;

import com.blog.mapper.blog.BlogMapper;
import com.blog.model.Blog;
import com.blog.model.Comment;
import com.blog.model.TypeBlog;
import com.blog.model.Type;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl implements BlogService{
    @Resource
    private BlogMapper blogMapper;

    @Override
    public Map<String, Object> queryBlogList(int page, int rows,Blog blog) {
        Map<String, Object> map=new HashMap<>();
        Integer Count=blogMapper.queryBlogCount(blog);
        List<Blog> blogList=blogMapper.queryBlogList((page-1)*rows,rows,blog);
        map.put("total",Count);
        map.put("rows",blogList);
        return map;
    }

    @Override
    public void saveBlog(Blog blog) {
        blogMapper.saveBlog(blog);
    }

    @Override
    public void delBlogById(String ids) {
        String[] split = ids.split(",");
        blogMapper.delBlogById(split);
    }

    @Override
    public Blog queryBlogById(Integer id) {
        return blogMapper.queryBlogById(id);
    }

    @Override
    public void updateBlog(Blog blog) {
        blogMapper.blogMapper(blog);
    }

    @Override
    public List<Type> getTypeBlogStatusList() {
        return blogMapper.getTypeBlogStatusList();
    }

    @Override
    public void updateBlogComment(Blog blog) {
        blogMapper.updateBlogComment(blog);
    }

    /*查询文章评论*/
    @Override
    public Map<String, Object> queryCommentById(Integer page,Integer rows,Integer id) {
        Integer bcCount=blogMapper.queryBcCount(id);
        Integer startnum=(page-1)*rows;
        List<Comment> blogCommentList=blogMapper.queryCommentById(startnum,rows,id);
        Map<String,Object> m=new HashMap<>();
        m.put("total",bcCount);
        m.put("rows",blogCommentList);
        return m;
    }

    @Override
    public List<TypeBlog> queryTypeCount() {
        return blogMapper.queryTypeCount();
    }


    @Override
    public List<Map<String, Integer>> query2() {
        return blogMapper.query2();
    }

    @Override
    public List<Map<String, Integer>> query1() {
        return blogMapper.query1();
    }

    @Override
    public List<Map<String, Integer>> query22() {
        return blogMapper.query22();
    }

    @Override
    public List<Map<String, Integer>> query3() {
        return blogMapper.query3();
    }

    @Override
    public List<Map<String, Integer>> query4() {
        return blogMapper.query4();
    }

    @Override
    public List<Map<String, Integer>> query5() {
        return blogMapper.query5();
    }

    @Override
    public void updateBlogTopById(Blog blog) {
        blogMapper.updateBlogTopById(blog);
    }


    @Override
    public Map<String, Object> queryBlogExamine(Integer page, Integer rows) {
        Map<String, Object> map=new HashMap<>();
        Integer Count=blogMapper.queryExamineCount();
        List<Blog> blogList=blogMapper.queryBlogExamine((page-1)*rows,rows);
        map.put("total",Count);
        map.put("rows",blogList);
        return map;
    }

    @Override
    public void updateBlogExamine(Integer id, Integer flag) {
        blogMapper.updateBlogExamine(id,flag);
    }

}
