package com.blog.controller.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("log")
public class LogController {

    @Autowired
    private MongoTemplate mongoTemplate;


    @RequestMapping("queryLog")
    @ResponseBody
    public String queryLog(){


        return "";
    }
}
