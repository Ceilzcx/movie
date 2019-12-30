package com.example.movie.ui.moviedetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

    private MovieContentAdapter adapter1;
    private MovieContentAdapter adapter2;
    private Handler handler;

    private String result;

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
            new Thread(() ->{
                CommentController commentController = new CommentController();
                result = commentController.judgeComment(editText.getText().toString());
                if (result.equals("1"))
                    result = "好评";
                else
                    result = "差评";
                Message message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }).start();
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

        commendMovies = new ArrayList<>();
        alsoLikeMovies = new ArrayList<>();

        //改成调用接口
        MovieController controller = new MovieController();
        new Thread(() -> {
            List<Movie> movies = controller.getCommendMovie(movieId);
            if (movies != null) {
                commendMovies.addAll(movies);
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }else {
                Message message = new Message();
                message.what = 4;
                handler.sendMessage(message);
            }
        }).start();
        new Thread(() -> {
            List<Movie> movies = controller.getCommendMovie(movieId);
            if (movies != null) {
                alsoLikeMovies.addAll(controller.getAlsolikeMovie(movieId));
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            }else {
                Message message = new Message();
                message.what = 4;
                handler.sendMessage(message);
            }
        }).start();

        handler = new MyHandler();


        adapter1 = new MovieContentAdapter(commendMovies);
        LinearLayoutManager manager1 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        commendRecyclerView.setLayoutManager(manager1);
        commendRecyclerView.setAdapter(adapter1);

        adapter2 = new MovieContentAdapter(alsoLikeMovies);
        LinearLayoutManager manager2 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        alsolikeRecyclerView.setLayoutManager(manager2);
        alsolikeRecyclerView.setAdapter(adapter2);

    }

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1){
                adapter1.notifyDataSetChanged();
            }else if (msg.what == 2){
                adapter2.notifyDataSetChanged();
            }else if (msg.what == 3){
                new AlertDialog.Builder(MovieDetail.this).setTitle("提示")
                        .setMessage("判断结果为"+result)
                        .setPositiveButton("确定", null).show();
            }else if (msg.what == 4){
                new AlertDialog.Builder(MovieDetail.this).setTitle("提示")
                        .setMessage("服务器连接错误,请检查您的网络")
                        .setPositiveButton("确定", null).show();
            }
        }
    }

}
