package com.blog.mapper.blog;


import com.blog.model.Blog;
import com.blog.model.Comment;
import com.blog.model.TypeBlog;
import com.blog.model.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BlogMapper {
    Integer queryBlogCount(@Param("b") Blog blog);

    List<Blog> queryBlogList(@Param("page") int page, @Param("rows") int rows,@Param("b") Blog blog);

    void saveBlog(Blog blog);

    void delBlogById(String[] ids);

    Blog queryBlogById(Integer id);

    void blogMapper(Blog blog);

    List<Type> getTypeBlogStatusList();

    void updateBlogComment(Blog blog);

    Integer queryBcCount(Integer id);

    List<Comment> queryCommentById(@Param("startnum") Integer startnum, @Param("rows")Integer rows, @Param("id")Integer id);

    List<TypeBlog> queryTypeCount();

    List<Map<String, Integer>> query2();

    List<Map<String, Integer>> query1();

    List<Map<String, Integer>> query22();

    List<Map<String, Integer>> query3();

    List<Map<String, Integer>> query4();

    List<Map<String, Integer>> query5();

    void updateBlogTopById(Blog blog);


    Integer queryExamineCount();

    List<Blog> queryBlogExamine(@Param("page")int i, @Param("rows")int rows);

    void updateBlogExamine(@Param("id")Integer id, @Param("flag")Integer flag);
}
