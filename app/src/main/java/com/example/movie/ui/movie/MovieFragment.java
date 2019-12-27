package com.example.movie.ui.movie;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie.R;

import java.util.Arrays;
import java.util.List;

public class MovieFragment extends Fragment{
    private List<String> classifies = Arrays.asList("科幻", "剧情", "爱情", "战争", "动画", "喜剧");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie, container, false);
        RecyclerView movie_classify_view = root.findViewById(R.id.recycle_movie_classify);
        RecyclerView movie_content_view = root.findViewById(R.id.recycle_movie_content);

        MovieClassifyAdapter classifyAdapter = new MovieClassifyAdapter(classifies);
        LinearLayoutManager manager1 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, true);
        movie_classify_view.setLayoutManager(manager1);
        movie_classify_view.setAdapter(classifyAdapter);

        return root;
    }
}

class MovieClassifyAdapter extends RecyclerView.Adapter<MovieClassifyAdapter.ClassifyHolder>{
    private List<String> classifies;

    public MovieClassifyAdapter(List<String> classifies){
        this.classifies = classifies;
    }

    @NonNull
    @Override
    public MovieClassifyAdapter.ClassifyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_classify, null);
        return new ClassifyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassifyHolder holder, int position) {
        holder.textView.setText(classifies.get(position));
    }

    @Override
    public int getItemCount() {
        return classifies.size();
    }

    static class ClassifyHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public ClassifyHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_classify);
        }
    }

}

class MovieContentAdapter extends RecyclerView.Adapter<>{
    private List<>

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ContentHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView text_name;
        TextView text_rate;


        public ContentHolder(@NonNull View view) {
            super(view);
            image = view.findViewById(R.id.movie_image);
            text_name = view.findViewById(R.id.movie_name);
            text_rate = view.findViewById(R.id.movie_rate);
        }
    }

}