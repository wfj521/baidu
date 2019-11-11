package com.blog.controller.tree;

import com.blog.service.tree.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("tree")
public class TreeController {

    @Autowired
    private TreeService treeService;

    @RequestMapping("/queryTree2")
    @ResponseBody
    public List<Map<String, Object>> queryTree2(Integer id){
        List<Integer> utidList = treeService.queryRoTeById(id);

        List<Map<String, Object>> maps = treeService.queryTree2(utidList);
        return maps;
    }

    @RequestMapping("addQx")
    @ResponseBody
    public void addQx(String ids ,Integer id){
        treeService.deleteQx(id);
        String[] split = ids.split(",");
        for (String qid:split ) {
            treeService.addQx(id,qid);
        }
    }
}
