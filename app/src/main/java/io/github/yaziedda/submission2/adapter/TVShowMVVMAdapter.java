package io.github.yaziedda.submission2.adapter;

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

import io.github.yaziedda.submission2.R;
import io.github.yaziedda.submission2.model.MoviesResult;
import io.github.yaziedda.submission2.model.TVShowResult;
import io.github.yaziedda.submission2.util.Static;

public class TVShowMVVMAdapter extends RecyclerView.Adapter<TVShowMVVMAdapter.NewsViewHolder> {

    Context context;
    List<TVShowResult> list;

    public interface OnItemClickListener {
        void onItemClick(TVShowResult model);
    }

    private final OnItemClickListener listener;

    public TVShowMVVMAdapter(Context context, List<TVShowResult> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TVShowMVVMAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tv_show, parent, false);
        return new  NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowMVVMAdapter.NewsViewHolder holder, int position) {
        final TVShowResult model = list.get(position);

        holder.tvTitle.setText(model.getOriginalName());
        holder.tvDesc.setText(model.getOverview());
        holder.tvRelease.setText(model.getFirstAirDate());

        Glide.with(holder.itemView.getContext())
                .load(Static.getUrlPoster(Static.POSTER_w185, model.getPosterPath()))
                .centerCrop()
                .placeholder(R.drawable.logo)
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
