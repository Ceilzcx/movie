package com.example.movie.controller;

import com.alibaba.fastjson.JSON;
import com.example.movie.bean.User;
import com.example.movie.ui.util.HttpUtil;

public class UserController {

    public User getUser(int userId){
        String path = "http://ylnzk.cn:8010/douban/user/getUser?userId=" + userId;
        String result = HttpUtil.getHttpInterface(path);
        return JSON.parseObject(result, User.class);
    }

}
