package com.example.demo.Service;

import com.example.demo.pojo.YYGUser;

public interface YYGUserService {

    //通过用户代码获取用户
    YYGUser getUserByYHDM(String YH_DM);

}
