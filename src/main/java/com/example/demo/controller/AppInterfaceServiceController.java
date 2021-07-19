package com.example.demo.controller;

import com.example.demo.Service.impl.AppInterfaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppInterfaceServiceController {

    @Autowired
    private AppInterfaceServiceImpl appInterfaceService;

    @PostMapping("/appinterface")
    @ResponseBody
    public String appinterface(
            @RequestParam("moduleId") String moduleId,// 模块标识
            @RequestParam("querystr") String querystr){


        return appInterfaceService.queryAppInterfaceList(querystr,moduleId);
    }
}
