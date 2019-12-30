package com.example.movie.ui.movie;

import android.content.Intent;
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
import com.example.movie.ui.moviedetail.MovieDetail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieFragment extends Fragment{
    private List<String> genres = Arrays.asList("科幻", "剧情", "爱情", "战争", "动画", "喜剧");
    private List<Movie> movies;
    private MovieController controller;
    private MovieContentAdapter contentAdapter;
    private MyHandler handler;

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

        handler = new MyHandler();

        movies = new ArrayList<>();
        RecyclerView movie_content_view = root.findViewById(R.id.recycle_movie_content);
        contentAdapter = new MovieContentAdapter(movies);
        contentAdapter.OnRecycleItemClickListener(position -> {
            Intent intent = new Intent(getContext(), MovieDetail.class);
            intent.putExtra("movieId", movies.get(position).getMovieId());
            startActivity(intent);
        });

        movie_content_view.setAdapter(contentAdapter);
        getMovies("科幻");

        return root;
    }

    private void getMovies(String genre){
        movies.clear();
        new Thread(() ->{
            controller = new MovieController();
            List<Movie> result = controller.getMoviesByGenre(genre);
            movies.addAll(result);
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }).start();
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1){
                contentAdapter.notifyDataSetChanged();
            }
        }
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

