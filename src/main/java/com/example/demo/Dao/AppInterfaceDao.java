package com.example.demo.Dao;

import com.example.demo.pojo.AppConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AppInterfaceDao {

    List<AppConfig> getAppConfigsByModuleID(String id);

}
