package io.githu.yazied.submission.favorit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.githu.yazied.submission.favorit.MoviesResult;
import io.githu.yazied.submission.favorit.R;
import io.githu.yazied.submission.favorit.util.Static;


public class MoviesMVVMAdapter extends RecyclerView.Adapter<MoviesMVVMAdapter.NewsViewHolder> {

    Context context;
    List<MoviesResult> list;

    public interface OnItemClickListener {
        void onItemClick(MoviesResult model);
    }

    private final OnItemClickListener listener;

    public MoviesMVVMAdapter(Context context, List<MoviesResult> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MoviesMVVMAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movies, parent, false);
        return new  NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesMVVMAdapter.NewsViewHolder holder, int position) {
        final MoviesResult model = list.get(position);

        holder.tvTitle.setText(model.getTitle());
        holder.tvDesc.setText(model.getOverview());
        holder.tvRelease.setText(model.getReleaseDate());

        Glide.with(holder.itemView.getContext())
                .load(Static.getUrlPoster(Static.POSTER_w185, model.getPosterPath()))
                .centerCrop()
//                .placeholder(R.drawable.logo)
                .into(holder.ivImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvDesc;
        TextView tvRelease;
        ImageView ivImage;


        public NewsViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            tvRelease = itemView.findViewById(R.id.tv_release);
            ivImage = itemView.findViewById(R.id.img);
        }
    }
}
