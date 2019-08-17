package id.yaziedda.digitalent;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapater extends RecyclerView.Adapter<RecyclerViewAdapater.ViewHolder> {

    List<Food> list = new ArrayList<>();
    boolean horizontal = false;

    public interface OnItemClickListener {
        void onItemClick(Food model);
    }

    private final OnItemClickListener listener;

    public RecyclerViewAdapater(List<Food> list, OnItemClickListener listener, boolean horizontal) {
        this.list = list;
        this.listener = listener;
        this.horizontal = horizontal;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (horizontal){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_horizontal, parent, false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Food model = list.get(position);

        holder.tvTitle.setText(model.getName());
        holder.tvPrice.setText(model.getPrice());

        Glide.with(holder.itemView.getContext())
                .load(model.getImage())
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvPrice;
        ImageView ivImage;


        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            ivImage = itemView.findViewById(R.id.img);
        }
    }
}

