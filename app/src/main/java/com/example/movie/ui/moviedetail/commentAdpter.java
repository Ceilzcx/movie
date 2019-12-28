package com.example.movie.ui.moviedetail;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie.R;
import com.example.movie.bean.Comment;

import java.util.List;

class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Comment> comments;
    private Context context;

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView content;
        TextView score;
        TextView data;
        public CommentViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            content = view.findViewById(R.id.content);
            score = view.findViewById(R.id.shop_score);
            data = view.findViewById(R.id.date);
        }
    }

    public CommentAdapter(Context context,List<Comment> comments) {
        this.context = context;
        this.comments=comments;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_comment, parent, false);
        CommentViewHolder holder = new CommentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Comment comment=comments.get(position);
        CommentViewHolder mholder = (CommentViewHolder) holder;
        mholder.name.setText(comment.getUsername());
        mholder.data.setText(comment.get_date());
        mholder.score.setText(comment.getRating());
        mholder.content.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

}
