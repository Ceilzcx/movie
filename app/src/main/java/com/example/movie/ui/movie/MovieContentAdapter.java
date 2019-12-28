package com.example.movie.ui.movie;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie.R;
import com.example.movie.bean.Movie;
import com.example.movie.ui.MyImageView;

import java.util.List;

public class MovieContentAdapter extends RecyclerView.Adapter<MovieContentAdapter.ContentHolder>{
    private List<Movie> movies;
    private MovieGenreAdapter.OnRecycleItemClickListener listener = null;

    public MovieContentAdapter(List<Movie> movies){
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
        Log.e("电影标题", movie.getTitle());
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