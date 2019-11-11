package com.blog.mapper.comment;


import com.blog.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    Integer queryAllCount(Comment comment);

    List<Comment> queryComment(@Param("startNum") Integer startNum,@Param("rows") Integer rows,@Param("c") Comment comment);

    void addcomment(Comment comment);

    void deleteById(Integer id);

    void delComment(String[] arr);

    void updateSupport(Integer id);

    void updateOppose(Integer id);
}
