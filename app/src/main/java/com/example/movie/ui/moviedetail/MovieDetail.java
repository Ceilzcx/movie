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

import java.util.List;

public class MovieDetail extends AppCompatActivity {
    Movie movie;
    MyImageView movieDetailImage;
    TextView movieDetailTitle,movieDetailGenre,movieDetailId,movieDetailRate;
    List<Comment> comments;
    RecyclerView recyclerView,commendRecyclerView,alsolikeRecyclerView;
    Button button;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetail);
        Intent intent=getIntent();
        final int movieId=intent.getIntExtra("movieId",0);

        movieDetailImage=findViewById(R.id.movie_detail_image);
        movieDetailTitle=findViewById(R.id.movie_detail_title);
        movieDetailGenre=findViewById(R.id.movie_detail_sorts);
        movieDetailId=findViewById(R.id.movie_detail_id);
        movieDetailRate=findViewById(R.id.shop_score);
        recyclerView=findViewById(R.id.movie_detail_recycleview);
        editText=findViewById(R.id.movie_detail_editrate);
        commendRecyclerView=findViewById(R.id.commendRecycelview);
        alsolikeRecyclerView=findViewById(R.id.alsolookRecycelview);
        button=findViewById(R.id.movie_detail_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Thread thread=new Thread(() -> movie=new MovieController().getMovieById(movieId));
        Thread thread2=new Thread(() -> comments=new CommentController().getCommentById(movieId));
        thread.start();
        thread2.start();
        try{
            thread.join();
            thread2.join();
        }catch (Exception e){
            e.printStackTrace();
        }
        movieDetailTitle.setText(movie.getTitle());
        movieDetailId.setText("movieId:0"+movie.getMovieId());
        movieDetailGenre.setText(movie.getGenres());
        movieDetailRate.setText(movie.getRate()+"");
        movieDetailImage.setImageURL(movie.getImage());
        CommentAdapter commentAdapter=new CommentAdapter(this,comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(commentAdapter);

    }
}
