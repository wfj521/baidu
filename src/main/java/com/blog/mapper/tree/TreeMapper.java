package com.blog.mapper.tree;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TreeMapper {

    List<Integer> queryRoTeById(Integer id);

    List<Map<String, Object>> queryPer(Integer id);

    void deleteQx(Integer id);

    void addQx(@Param("id") Integer id,@Param("qid") int parseInt);
}
