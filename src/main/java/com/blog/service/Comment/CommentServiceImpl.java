package com.blog.service.Comment;


import com.blog.mapper.comment.CommentMapper;
import com.blog.model.Comment;
import com.blog.utils.RedisCommonConf;
import com.blog.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private RedisUtil redisUtil;


    @Override
    public Map<String, Object> queryComment(Integer page, Integer rows, Comment comment) {

        List<Comment> listComment=new ArrayList<Comment>();
        String key= RedisCommonConf.QUERY_COMMENT_LIST;

        if(comment.getStartDate()!=null) {
            key+="_"+comment.getStartDate();
        }
        if(comment.getEndDate()!=null) {
            key+="_"+comment.getEndDate();
        }
        Integer commentAllCount=commentMapper.queryAllCount(comment);
        HashMap m=new HashMap();
        Integer startNum=(page-1)*rows;

        if (redisUtil.hasKey(key)) {
            //a、存在：走缓存、获取、return
            listComment = (List<Comment>) redisUtil.get(RedisCommonConf.QUERY_COMMENT_LIST );
        } else {
            //b、不存在：走数据、获取、存入缓存、return
            listComment=commentMapper.queryComment(startNum,rows,comment);
            //存入缓存
            redisUtil.set(key, listComment);
        }
        m.put("total",commentAllCount);
        m.put("rows",listComment);
        return m ;
    }


    @Override
    public void addcomment(Comment comment) {
        commentMapper.addcomment(comment);
    }

    @Override
    public void deleteById(Integer id) {
        commentMapper.deleteById(id);
    }

    @Override
    public void delComment(String ids) {
        String[] arr=ids.split(",");
        commentMapper.delComment(arr);
    }

    @Override
    public void updateSupport(Integer id) {
        commentMapper.updateSupport(id);
    }

    @Override
    public void updateOppose(Integer id) {
        commentMapper.updateOppose(id);
    }
}
