package com.example.demo.Service.impl;


import com.example.demo.Config.MD5;
import com.example.demo.Config.RedisUtil;
import com.example.demo.Service.TokenService;
import nl.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    //   pc-32位加密的用户名-用户代码-年月日时分 秒-6位随机数
    //前缀 PC-NAME-USERID-CREATIONDATE-RONDEM(6位)
    // 生成token
    public String generateToken(String userAgent, Map<String,Object> yhXxb) throws Exception {
        StringBuilder  str=new StringBuilder();
        //获取用户代理
        UserAgent agent= UserAgent.parseUserAgentString(userAgent);
        //判断是否是手机端还是电脑端
        if(agent.getOperatingSystem().isMobileDevice()){
            str.append("MOBILE-YGJD-");
        }else{
            str.append("PC-YGJD-");
        }
        str.append(MD5.getMd5(yhXxb.get("yhDm").toString(),32)+"-");
        str.append(yhXxb.get("yhDm").toString() +"-");
        str.append(new SimpleDateFormat("yyyyMMddHHmmsss").format(new Date())+"-");
        str.append(MD5.getMd5(userAgent,6));

        return str.toString();
    }
    @Override
// 保存token信息
    public void save(String token,Map<String,Object> yhXxb) throws Exception {
        Map<String,String> map=new HashMap<String,String>();
        map.put("yh_dm",yhXxb.get("yhDm").toString());
        map.put("js_dm",yhXxb.get("jsDm").toString());
        map.put("zzdw_dm",yhXxb.get("zzdwDm").toString());
        map.put("zzdw_jb",yhXxb.get("zzdwJb").toString());
        if(token.startsWith("PC-")){
            redisUtil.setMap(token, map);
        }else{
            redisUtil.setMap(token,map);
        }

    }

    @Override
    public void delete(String token) throws Exception {
        if(redisUtil.exists(token)){
            redisUtil.dell(token);
        }
    }

    @Override
    public String validate(String userAgent, String token) throws Exception {
        String agentMd5 = token.split("-")[5];
        if (!redisUtil.exists(token)) {
            return "";
        } else {
            if(MD5.getMd5(userAgent, 6).equals(agentMd5)) {
                redisUtil.expire(token);
                return redisUtil.getMap(token).get("yh_dm");
            }
            return "";
        }

    }

    @Override
    public Map<String, String> getYhxx(String token) throws Exception {
        return redisUtil.getMap(token);
    }
}
