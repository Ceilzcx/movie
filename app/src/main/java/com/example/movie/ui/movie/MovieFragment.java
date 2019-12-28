package com.example.movie.ui.movie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.movie.ui.MyImageView;
import com.example.movie.ui.moviedetail.MovieDetail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieFragment extends Fragment{
    private List<String> genres = Arrays.asList("科幻", "剧情", "爱情", "战争", "动画", "喜剧");
    private List<Movie> movies;
    private MovieController controller;
    private MovieContentAdapter contentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie, container, false);

        RecyclerView movie_classify_view = root.findViewById(R.id.recycle_movie_classify);
        MovieGenreAdapter genreAdapter = new MovieGenreAdapter(genres);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, true);
        genreAdapter.OnRecycleItemClickListener(position -> getMovies(genres.get(position)));
        movie_classify_view.setLayoutManager(manager);
        movie_classify_view.setAdapter(genreAdapter);

        movies = new ArrayList<>();
        getMovies("科幻");
        RecyclerView movie_content_view = root.findViewById(R.id.recycle_movie_content);
        contentAdapter = new MovieContentAdapter(movies);
        contentAdapter.OnRecycleItemClickListener(position -> {
            Intent intent = new Intent(getContext(), MovieDetail.class);
            intent.putExtra("movieId", movies.get(position).getMovieId());
            startActivity(intent);
        });
        movie_content_view.setAdapter(contentAdapter);

        return root;
    }

    private void getMovies(String genre){
        movies.clear();
        new Thread(() ->{
            controller = new MovieController();
            List<Movie> result = controller.getMoviesByGenre(genre);
            Log.e("电影", result.size()+"");
            movies.addAll(result);
            //contentAda pter.notifyDataSetChanged();
        }).start();
    }

}

class MovieGenreAdapter extends RecyclerView.Adapter<MovieGenreAdapter.GenreHolder>{
    private List<String> classifies;
    private OnRecycleItemClickListener listener = null;

    MovieGenreAdapter(List<String> classifies){
        this.classifies = classifies;
    }

    public void OnRecycleItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecycleItemClickListener {
        void OnRecycleItemClickListener(int position);
    }

    @NonNull
    @Override
    public MovieGenreAdapter.GenreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_genre, parent, false);
        return new GenreHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreHolder holder, int position) {
        holder.textView.setText(classifies.get(position));
        holder.textView.setOnClickListener(v -> {
            listener.OnRecycleItemClickListener(position);
        });
    }

    @Override
    public int getItemCount() {
        return classifies.size();
    }

    static class GenreHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public GenreHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_genre);
        }
    }

}

class MovieContentAdapter extends RecyclerView.Adapter<MovieContentAdapter.ContentHolder>{
    private List<Movie> movies;
    private MovieGenreAdapter.OnRecycleItemClickListener listener = null;

    MovieContentAdapter(List<Movie> movies){
        this.movies = movies;
    }

    public void OnRecycleItemClickListener(MovieGenreAdapter.OnRecycleItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecycleItemClickListener {
        void OnRecycleItemClickListener(int position);
    }

    @NonNull
    @Override
    public ContentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_movie_content, parent, false);
        return new ContentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.image.setImageURL(movie.getImage());
        holder.text_title.setText(movie.getTitle());
        holder.text_rate.setText(""+movie.getRate());
        holder.image.setOnClickListener(v -> listener.OnRecycleItemClickListener(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class ContentHolder extends RecyclerView.ViewHolder{
        private MyImageView image;
        private TextView text_title;
        private TextView text_rate;


        public ContentHolder(@NonNull View view) {
            super(view);
            image = view.findViewById(R.id.movie_image);
            text_title = view.findViewById(R.id.movie_title);
            text_rate = view.findViewById(R.id.movie_rate);
        }
    }

}