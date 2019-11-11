package com.blog.controller.comment;


import com.blog.model.Comment;
import com.blog.service.Comment.CommentService;
import com.blog.utils.RedisCommonConf;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private RedisTemplate redisTemplate;


    @RequestMapping("toShowComment")
    public  String toShowComment(){
        return "comment/showComment.html";
    }


    @RequestMapping("showAddComment")
    public  String showAddComment(){
        return "comment/addComment.html";
    }

    @RequestMapping("queryComment")
    @ResponseBody
    public Map<String,Object> queryComment(Integer page, Integer rows, Comment comment){
        Map<String,Object> commentList= commentService.queryComment(page,rows,comment);
        return commentList;
    }

    @RequestMapping("addComment")
    @ResponseBody
    public void addcomment(Comment comment){
        comment.setStatus(2);
        comment.setCreateTime(new Date());
        comment.setOppose(0);
        comment.setSupport(0);
        commentService.addcomment(comment);
        Set keys = redisTemplate.keys(RedisCommonConf.QUERY_COMMENT_LIST + "*");
        redisTemplate.delete(keys);
    }


    @RequestMapping("deleteById")
    @ResponseBody
    public void deleteById(Integer id){
        commentService.deleteById(id);
        Set keys = redisTemplate.keys(RedisCommonConf.QUERY_COMMENT_LIST + "*");
        redisTemplate.delete(keys);
    }

    @RequestMapping("delComment")
    @ResponseBody
    public void delComment(String ids){
        commentService.delComment(ids);
        Set keys = redisTemplate.keys(RedisCommonConf.QUERY_COMMENT_LIST + "*");
        redisTemplate.delete(keys);
    }


    @RequestMapping("updateSupport")
    @ResponseBody
    public void updateSupport(Integer id){
        commentService.updateSupport(id);
        Set keys = redisTemplate.keys(RedisCommonConf.QUERY_COMMENT_LIST + "*");
        redisTemplate.delete(keys);
    }


    @RequestMapping("updateOppose")
    @ResponseBody
    public void updateOppose(Integer id){
        commentService.updateOppose(id);
        Set keys = redisTemplate.keys(RedisCommonConf.QUERY_COMMENT_LIST + "*");
        redisTemplate.delete(keys);
    }
}
