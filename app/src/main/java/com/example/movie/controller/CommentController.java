package com.example.movie.controller;

import com.alibaba.fastjson.JSON;
import com.example.movie.bean.Comment;
import com.example.movie.ui.util.HttpUtil;

import java.util.List;

public class CommentController {

    public List<Comment> getCommentById(int movieId) {
        String path="http://ylnzk.cn:8010/douban/comment/getComment?movieId="+movieId;
        String result= HttpUtil.getHttpInterface(path);
        return JSON.parseArray(result, Comment.class);
    }

    public String judgeComment(String content){
        String path = "http://192.168.1.4:5000/comment?comment=好看";
        return HttpUtil.getHttpInterface(path);
    }

}
