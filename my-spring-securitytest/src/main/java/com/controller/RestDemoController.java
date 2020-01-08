package com.controller;

import com.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestDemoController {

    @RequestMapping("/api/query")
    public ResponseVO admin() {
        ResponseVO vo = new ResponseVO();
        vo.setCode("200");
        vo.setMsg("访问了 /api/query");
        vo.setData("返回的信息...");
        return vo;
    }
}
