package com.example.demo.Dao;

import com.example.demo.pojo.TestUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestUserDao {

    TestUser getUserById(int userid);

//    @Select("select * from test_users")
    List<TestUser> getAllUsers();

    TestUser getAllUserArticlesByid(int userid);

    void insertUser(TestUser user);

    int updateUser(TestUser user);

    int deleteUser(int userid);

}
