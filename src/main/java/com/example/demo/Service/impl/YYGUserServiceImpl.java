package com.example.demo.Service.impl;

import com.example.demo.Dao.YYGUserDao;
import com.example.demo.Service.YYGUserService;
import com.example.demo.pojo.YYGUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YYGUserServiceImpl implements YYGUserService {
    @Autowired
    private YYGUserDao yygUserDao;

    @Override
    public YYGUser getUserByYHDM(String YH_DM) {
        try {
            return yygUserDao.getUserByYHDM(YH_DM);

        }catch (Exception e){
            throw e;
        }
    }
}
