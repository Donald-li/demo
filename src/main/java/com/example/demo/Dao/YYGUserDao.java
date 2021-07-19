package com.example.demo.Dao;

import com.example.demo.pojo.YYGUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YYGUserDao {

    //根据用户代码获得用户
    YYGUser getUserByYHDM(String YH_DM);

}
