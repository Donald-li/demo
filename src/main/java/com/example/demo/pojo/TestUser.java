package com.example.demo.pojo;

import java.util.List;

public class TestUser {
    private int userid;
    private String username;
    private String password;
    private TestUserAdaver adaver;
    private List<TestArticle> articles;

    @Override
    public String toString() {
        return "TestUser{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public TestUser() {
    }

    public List<TestArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<TestArticle> articles) {
        this.articles = articles;
    }

    public TestUserAdaver getAdaver() {
        return adaver;
    }

    public void setAdaver(TestUserAdaver adaver) {
        this.adaver = adaver;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
