package com.example.demo.Dao;

import com.example.demo.pojo.TestArticle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestArticleDao {

    List<TestArticle> getAllArticles();

    void newArticle(TestArticle article);

    TestArticle getArticleById(int arid);

    int updateArticle(TestArticle article);

    int deleteArticle(int arid);

}
