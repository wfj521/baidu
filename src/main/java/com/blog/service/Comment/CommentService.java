package com.blog.service.Comment;

import com.blog.model.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {

    Map<String, Object> queryComment(Integer page, Integer rows, Comment comment);

    void addcomment(Comment comment);

    void deleteById(Integer id);

    void delComment(String ids);

    void updateSupport(Integer id);

    void updateOppose(Integer id);
}
