package com.example.movie.ui.prediction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie.R;
import com.example.movie.bean.Movie;
import com.example.movie.controller.MovieController;
import com.example.movie.ui.movie.MovieContentAdapter;
import com.example.movie.ui.moviedetail.MovieDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PredictionFragment extends Fragment {
    private List<Movie> myMovies;
    private List<Movie> recommendMovies;
    private MovieContentAdapter recommedAdapter;
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_prediction, container, false);

        MovieController controller = new MovieController();
        SharedPreferences pref = Objects.requireNonNull(getContext()).getSharedPreferences("user", Context.MODE_PRIVATE);

        TextView textNone = root.findViewById(R.id.text_none);

        myMovies = new ArrayList<>();
        recommendMovies = new ArrayList<>();

        RecyclerView myMovieView = root.findViewById(R.id.recycle_my_movie);
        Thread thread = new Thread(() -> {
            List<Movie> movies = controller.getMovieByUser(pref.getInt("userId", 0));
            if (movies != null)
                myMovies.addAll(movies);
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MovieContentAdapter myAdapter = new MovieContentAdapter(myMovies);
        LinearLayoutManager manager2 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        myMovieView.setLayoutManager(manager2);
        myMovieView.setAdapter(myAdapter);
        if (myMovies.size() > 0) {
            textNone.setVisibility(View.GONE);
            myMovieView.setVisibility(View.VISIBLE);
        }
        else {
            textNone.setVisibility(View.VISIBLE);
            myMovieView.setVisibility(View.GONE);
        }

        RecyclerView recommendView = root.findViewById(R.id.recycle_recommend_movie);
        recommendMovies = new ArrayList<>();
        Thread thread1 = new Thread(() -> {
            recommendMovies.addAll(controller.getUserMovie(pref.getInt("userId", 0)));
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        });
        thread1.start();
        recommedAdapter = new MovieContentAdapter(recommendMovies);
        LinearLayoutManager manager1 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        recommendView.setLayoutManager(manager1);
        recommendView.setAdapter(recommedAdapter);

        handler = new MyHandler();

        recommedAdapter.OnRecycleItemClickListener(position -> {
            Intent intent = new Intent(getContext(), MovieDetail.class);
            intent.putExtra("movieId", recommendMovies.get(position).getMovieId());
            startActivity(intent);
        });

        myAdapter.OnRecycleItemClickListener(position -> {
            Intent intent = new Intent(getContext(), MovieDetail.class);
            intent.putExtra("movieId", myMovies.get(position).getMovieId());
            startActivity(intent);
        });

        return root;
    }

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1){
                recommedAdapter.notifyDataSetChanged();
            }
        }
    }

}