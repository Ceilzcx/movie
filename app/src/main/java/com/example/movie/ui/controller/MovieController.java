package com.example.movie.ui.controller;

import com.alibaba.fastjson.JSON;
import com.example.movie.ui.bean.Movie;
import com.example.movie.ui.dao.MovieDao;
import com.example.movie.ui.util.HttpUtil;

import java.util.List;

public class MovieController implements MovieDao {

    @Override
    public Movie getMovieById(int movieId) {
        String path="http://ylnzk.cn:8080/douban/movie/getMovie?movieId="+movieId;
        String result= HttpUtil.getHttpInterface(path);
        return JSON.parseObject(result, Movie.class);
    }

    @Override
    public List<Movie> getMoviesByGenre(String genre) {
        String path="http://ylnzk.cn:8080/douban/movie/getMovies?genre="+genre;
        String result= HttpUtil.getHttpInterface(path);
        return JSON.parseArray(result,Movie.class);
    }
}
