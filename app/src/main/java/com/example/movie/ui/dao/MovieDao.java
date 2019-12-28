package com.example.movie.ui.dao;

import com.example.movie.ui.bean.Movie;

import java.util.List;

public interface MovieDao {

    Movie getMovieById(int movieId);

    List<Movie> getMoviesByGenre(String genre);

}
