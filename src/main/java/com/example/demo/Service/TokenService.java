package com.example.demo.Service;


import java.util.Map;

public interface TokenService {
    //生成token
    String generateToken(String userAgent, Map<String,Object> yhXxb) throws Exception;
    //生成save
    void save(String token, Map<String,Object> yhXxb) throws Exception;
    //删除Token
    void delete(String token) throws Exception;
    //验证是否登录
    String validate(String userAgent, String token) throws Exception;
    //获取用户信息
    Map<String,String> getYhxx(String token)throws Exception;
}
