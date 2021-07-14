package com.example.demo.controller;

import com.example.demo.Dao.TestArticleDao;
import com.example.demo.pojo.TestArticle;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/articles")
public class TestArticleController {

    @Autowired
    private TestArticleDao testArticleDao;

    @GetMapping("/all")
    public List<TestArticle> getAllArticles(){
        return testArticleDao.getAllArticles();
    }

    @PostMapping("/new")
    public Map<String,Object> newArticle(TestArticle article){
        Map<String,Object> reMap = new HashMap<>();
        try {
            testArticleDao.newArticle(article);
            reMap.put("message","创建成功！");
            return reMap;
        }catch (Exception e){
            e.printStackTrace();
            reMap.put("message","创建失败！");
            return reMap;
        }
    }

    @GetMapping("/getArticle")
    public TestArticle getArticleById(@Param("arid") int arid){
        return  testArticleDao.getArticleById(arid);
    }

    @PatchMapping("/update")
    public Map<String,Object> udpateArticle(TestArticle article){
        Map<String,Object> reMap = new HashMap<>();
        if(testArticleDao.updateArticle(article) != 0){
            reMap.put("message","修改成功！");
            reMap.put("result",testArticleDao.getArticleById(article.getArid()));
        }else {
            reMap.put("message","修改失败！");
        }

        return reMap;
    }

    @DeleteMapping("/delete")
    public Map<String,Object> deleteArticle(@Param("arid")int arid){
        Map<String,Object> reMap = new HashMap<>();
        if(testArticleDao.deleteArticle(arid) != 0){
            reMap.put("message","删除成功！");
        }else {
            reMap.put("message","删除失败！");
        }

        return reMap;
    }

}
