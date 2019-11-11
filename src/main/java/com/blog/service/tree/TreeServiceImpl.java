package com.blog.service.tree;

import com.blog.mapper.tree.TreeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TreeServiceImpl implements TreeService {
    @Resource
    private TreeMapper treeMapper;

    @Override
    public List<Integer> queryRoTeById(Integer id) {
        return treeMapper.queryRoTeById(id);
    }

    @Override
    public List<Map<String, Object>> queryTree2(List<Integer> utidList) {
        List<Map<String, Object>> treeList = queryPerByPid(1, utidList);
        return treeList;
    }

    @Override
    public void deleteQx(Integer id) {
        treeMapper.deleteQx(id);
    }

    @Override
    public void addQx(Integer id, String qid) {
        treeMapper.addQx(id,Integer.parseInt(qid));
    }

    private List<Map<String, Object>> queryPerByPid(Integer id, List<Integer> utidList){
        List<Map<String, Object>> perList = treeMapper.queryPer(id);
        for (Map<String, Object> map : perList) {
            for (Integer ut : utidList) {
                if(Integer.valueOf(map.get("value").toString()) == ut) {
                    Map<String, Boolean> checkMap = new HashMap<>();
                    checkMap.put("checked", true);
                    map.put("state", checkMap);
                }
            }

            List<Map<String, Object>> nodeList = queryPerByPid(Integer.parseInt(map.get("value").toString()), utidList);
            if(nodeList != null && nodeList.size() > 0) {
                map.put("nodes", nodeList);
            }

        }
        return perList;
    }
}
