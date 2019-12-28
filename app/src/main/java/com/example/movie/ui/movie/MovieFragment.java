package com.example.movie.ui.movie;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie.R;
import com.example.movie.ui.bean.Movie;

import java.util.Arrays;
import java.util.List;

public class MovieFragment extends Fragment{
    private List<String> genres = Arrays.asList("科幻", "剧情", "爱情", "战争", "动画", "喜剧");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie, container, false);
        RecyclerView movie_classify_view = root.findViewById(R.id.recycle_movie_classify);
        RecyclerView movie_content_view = root.findViewById(R.id.recycle_movie_content);

        MovieGenreAdapter genreAdapter = new MovieGenreAdapter(genres);
        LinearLayoutManager manager1 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, true);
        movie_classify_view.setLayoutManager(manager1);
        movie_classify_view.setAdapter(genreAdapter);

        return root;
    }
}

class MovieGenreAdapter extends RecyclerView.Adapter<MovieGenreAdapter.GenreHolder>{
    private List<String> classifies;

    public MovieGenreAdapter(List<String> classifies){
        this.classifies = classifies;
    }

    @NonNull
    @Override
    public MovieGenreAdapter.GenreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_genre, null);
        return new GenreHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreHolder holder, int position) {
        holder.textView.setText(classifies.get(position));
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

    MovieContentAdapter(List<Movie> movies){
        this.movies = movies;
    }

    @NonNull
    @Override
    public ContentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_movie_content, null);
        return new ContentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.image.setImageBitmap(BitmapFactory.decodeFile(movie.getImage()));
        holder.text_title.setText(movie.getTitle());
        holder.text_rate.setText(""+movie.getRate());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class ContentHolder extends RecyclerView.ViewHolder{
        private ImageView image;
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