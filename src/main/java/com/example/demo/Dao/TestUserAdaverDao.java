package com.example.demo.Dao;

import com.example.demo.pojo.TestUserAdaver;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestUserAdaverDao {

    List<TestUserAdaver> getAllAdaver();

}
