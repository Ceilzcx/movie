package com.example.movie.ui.dao;


import com.example.movie.ui.bean.Comment;

import java.util.List;

public interface CommentDao {

    List<Comment> getCommentById(int movieId);

}
