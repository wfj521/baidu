package com.blog.service.tree;

import java.util.List;
import java.util.Map;

public interface TreeService {
    List<Integer> queryRoTeById(Integer id);

    List<Map<String, Object>> queryTree2(List<Integer> utidList);

    void deleteQx(Integer id);

    void addQx(Integer id, String qid);
}
