package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Service.TokenService;
import com.example.demo.Service.YYGUserService;
import com.example.demo.pojo.YYGUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/YYG")
public class YYGController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private YYGUserService yygUserService;

    @PostMapping("/login")
    Map<String,Object> login(
            @Param("username") String username,
            @Param("password") String password,
            HttpServletRequest request,
            HttpServletResponse response){

        String userAgent=request.getHeader("user-agent");
        Map<String, Object> remap=new HashMap<String, Object>();
        Map<String, Object> datamap=new HashMap<String, Object>();
        System.out.println("进入try");
        try{
            YYGUser user =  yygUserService.getUserByYHDM(username);
            if(user==null){
                remap.put("msg", "登录失败：用户名不存在!");
                remap.put("code", "0");
                remap.put("data", datamap);
                return remap;
            }
            if(!password.equals(user.getYH_MM())){
                remap.put("msg", "登录失败：密码不正确!");
                remap.put("code", "0");
                remap.put("data", datamap);
                return remap;
            }
            else{
                //编辑返回值map
                datamap.put("yhdm",username );
                datamap.put("yhmc",user.getYH_MC());
                //编辑用于生成Token的map
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("yhMc",user.getYH_MC());
                map.put("yhDm",user.getYH_DM());
                map.put("jsDm",user.getJS_DM());
                map.put("zzdwDm",user.getZZDW_DM());
                map.put("zzdwLeaf",user.getZZDw_LEAF());
                System.out.println(user.toString());
                String token=tokenService.generateToken(userAgent,map);
//                tokenService.save(token,map);
                datamap.put("token",token);

                //保存Token
                tokenService.save(token,map);

                //在返回头部返回生成的Token
                response.setHeader("Access-Control-Expose-Headers", "token");
                response.setHeader("token", token);



                remap.put("msg", "登录成功!");
                remap.put("code", "1");
                remap.put("data", datamap);

                System.out.println("\n缓存："+tokenService.getYhxx(token));
                return remap;
            }

        }catch(Exception e) {
            remap.put("msg", "登录失败，用户名或密码错误!");
            remap.put("code", "0");
            remap.put("data", e);
            e.printStackTrace();
            return remap;
        }

    };

}
