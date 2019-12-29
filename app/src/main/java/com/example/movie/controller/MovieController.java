package com.example.movie.controller;

import com.alibaba.fastjson.JSON;
import com.example.movie.bean.Movie;
import com.example.movie.ui.util.HttpUtil;

import java.util.List;

public class MovieController {

    public Movie getMovieById(int movieId) {
        String path = "http://ylnzk.cn:8010/douban/movie/getMovie?movieId=" + movieId;
        String result = HttpUtil.getHttpInterface(path);
        return JSON.parseObject(result, Movie.class);
    }

    public List<Movie> getMoviesByGenre(String genre) {
        String path = "http://ylnzk.cn:8010/douban/movie/getMovies?genre=" + genre;
        String result = HttpUtil.getHttpInterface(path);
        return JSON.parseArray(result, Movie.class);
    }

    public List<Movie> getCommedMovie(int movieId) {
        String path = "";
        String result = HttpUtil.getHttpInterface(path);
        return JSON.parseArray(result, Movie.class);
    }

    public List<Movie> getAlsolikeMovie(int movieId){
        String path = "";
        String result = HttpUtil.getHttpInterface(path);
        return JSON.parseArray(result, Movie.class);
    }

}
