package com.blog.service.blog;

import com.blog.model.Blog;
import com.blog.model.TypeBlog;
import com.blog.model.Type;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Map<String, Object> queryBlogList(int page, int rows,Blog blog);

    void saveBlog(Blog blog);

    void delBlogById(String ids);

    Blog queryBlogById(Integer id);

    void updateBlog(Blog blog);

    List<Type> getTypeBlogStatusList();

    void updateBlogComment(Blog blog);

    Map<String, Object> queryCommentById(Integer page, Integer rows, Integer id);

    List<TypeBlog> queryTypeCount();

    List<Map<String, Integer>> query2();

    List<Map<String, Integer>> query1();

    List<Map<String, Integer>> query22();

    List<Map<String, Integer>> query3();

    List<Map<String, Integer>> query4();

    List<Map<String, Integer>> query5();

    void updateBlogTopById(Blog blog);

    Map<String, Object> queryBlogExamine(Integer page, Integer rows);

    void updateBlogExamine(Integer id, Integer flag);
}
