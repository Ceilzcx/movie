package com.example.movie.ui.moviedetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.movie.R;
import com.example.movie.bean.Comment;
import com.example.movie.bean.Movie;
import com.example.movie.controller.CommentController;
import com.example.movie.controller.MovieController;
import com.example.movie.ui.MyImageView;
import com.example.movie.ui.movie.MovieContentAdapter;

import java.util.ArrayList;
import java.util.List;

public class MovieDetail extends AppCompatActivity {
    Movie movie;
    MyImageView movieDetailImage;
    TextView movieDetailTitle, movieDetailGenre, movieDetailId, movieDetailRate;
    List<Comment> comments;
    RecyclerView recyclerView, commendRecyclerView, alsolikeRecyclerView;
    Button button;
    EditText editText;

    List<Movie> commendMovies;
    List<Movie> alsoLikeMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetail);
        Intent intent = getIntent();
        final int movieId = intent.getIntExtra("movieId", 0);

        movieDetailImage = findViewById(R.id.movie_detail_image);
        movieDetailTitle = findViewById(R.id.movie_detail_title);
        movieDetailGenre = findViewById(R.id.movie_detail_sorts);
        movieDetailId = findViewById(R.id.movie_detail_id);
        movieDetailRate = findViewById(R.id.shop_score);
        recyclerView = findViewById(R.id.movie_detail_recycleview);
        editText = findViewById(R.id.movie_detail_editrate);
        commendRecyclerView = findViewById(R.id.commendRecycelview);
        alsolikeRecyclerView = findViewById(R.id.alsolookRecycelview);
        button = findViewById(R.id.movie_detail_confirm);
        button.setOnClickListener(v -> {

        });
        Thread thread = new Thread(() -> movie = new MovieController().getMovieById(movieId));
        Thread thread2 = new Thread(() -> comments = new CommentController().getCommentById(movieId));
        thread.start();
        thread2.start();
        try {
            thread.join();
            thread2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        movieDetailTitle.setText(movie.getTitle());
        movieDetailId.setText("movieId:0" + movie.getMovieId());
        movieDetailGenre.setText(movie.getGenres());
        movieDetailRate.setText(movie.getRate() + "");
        movieDetailImage.setImageURL(movie.getImage());
        CommentAdapter commentAdapter = new CommentAdapter(this, comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(commentAdapter);

        //改成调用接口
        MovieController controller = new MovieController();
        Thread thread3 = new Thread(() -> commendMovies = controller.getCommedMovie(movieId));
        Thread thread4 = new Thread(() -> alsoLikeMovies = controller.getAlsolikeMovie(movieId));
        thread3.start();
        thread4.start();
        try {
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MovieContentAdapter adapter1 = new MovieContentAdapter(commendMovies);
        LinearLayoutManager manager1 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        commendRecyclerView.setLayoutManager(manager1);
        commendRecyclerView.setAdapter(adapter1);

        MovieContentAdapter adapter2 = new MovieContentAdapter(alsoLikeMovies);
        LinearLayoutManager manager2 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        alsolikeRecyclerView.setLayoutManager(manager2);
        alsolikeRecyclerView.setAdapter(adapter2);

    }
}
