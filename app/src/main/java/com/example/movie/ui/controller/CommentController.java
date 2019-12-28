package com.example.movie.ui.controller;

import com.alibaba.fastjson.JSON;
import com.example.movie.ui.bean.Comment;
import com.example.movie.ui.dao.CommentDao;
import com.example.movie.ui.util.HttpUtil;


import java.util.List;

public class CommentController implements CommentDao {
    @Override
    public List<Comment> getCommentById(int movieId) {
        String path="http://ylnzk.cn:8080/douban/comment/getComments?movieId="+movieId;
        String result= HttpUtil.getHttpInterface(path);
        return JSON.parseArray(result, Comment.class);
    }
}
