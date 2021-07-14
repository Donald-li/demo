package com.example.demo.controller;

import com.example.demo.Dao.TestUserDao;
import com.example.demo.pojo.TestUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class TestUserController {
//
//    @Autowired
//    private TestDao testDao;

    @Autowired
    private TestUserDao testUserDao;

    @GetMapping("/getUser")
    public TestUser getUser(@Param("userid") int userid){
        return testUserDao.getUserById(userid);
    }

    @GetMapping("/all")
    public List<TestUser> getall(){

        List<TestUser> users = testUserDao.getAllUsers();

        return users;

    }

    @GetMapping("/getArticles")
    public TestUser getA(@Param("userid") int userid){

        TestUser user = testUserDao.getAllUserArticlesByid(userid);
        return user;

    }

    @PostMapping("/new")
    public Map<String,Object> newUser(TestUser user){
        Map<String,Object> reMap = new HashMap<String,Object>();
        try{
            testUserDao.insertUser(user);
            reMap.put("message","注册成功！");
            return reMap;
        }catch (Exception e){
            reMap.put("message","注册失败！");
            return reMap;
        }
//        System.out.println(user.toString());

    }


    @PatchMapping("/updateUser")
    public Map<String,Object> updateuser(TestUser user){
        Map<String,Object> reMap = new HashMap<>();
        if(testUserDao.updateUser(user) != 0){
            reMap.put("message","修改成功！");
            reMap.put("result",testUserDao.getUserById(user.getUserid()));
            return reMap;
        }else{
            reMap.put("message","修改失败！");
            return reMap;
        }

    }

    @DeleteMapping("/deleteUser")
    public Map<String,Object> deleteUser(@Param("userid") int userid){
        Map<String,Object> reMap = new HashMap<String,Object>();
        if(testUserDao.deleteUser(userid) != 0){
            reMap.put("message","删除成功！");
            return reMap;
        }else{
            reMap.put("message","删除失败！");
            return reMap;
        }
    }

}
