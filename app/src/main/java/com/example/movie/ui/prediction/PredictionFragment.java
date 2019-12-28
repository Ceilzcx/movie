package com.example.movie.ui.prediction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie.R;
import com.example.movie.bean.Movie;
import com.example.movie.ui.movie.MovieContentAdapter;

import java.util.ArrayList;
import java.util.List;

public class PredictionFragment extends Fragment {
    private List<Movie> myMovies;
    private List<Movie> recommendMovies;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_prediction, container, false);

        RecyclerView myMovieView = root.findViewById(R.id.recycle_my_movie);
        myMovies = new ArrayList<>();
        MovieContentAdapter myAdapter = new MovieContentAdapter(myMovies);
        myMovieView.setAdapter(myAdapter);

        RecyclerView recommendView = root.findViewById(R.id.recycle_recommend_movie);
        recommendMovies = new ArrayList<>();
        MovieContentAdapter recommedAdapter = new MovieContentAdapter(recommendMovies);
        recommendView.setAdapter(recommedAdapter);

        return root;
    }

}