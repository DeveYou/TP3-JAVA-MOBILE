package com.example.ratings.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratings.beans.Star;
import com.example.ratings.utils.NewFilter;

import java.util.List;

public class StarAdapter extends  RecyclerView.Adapter<StarAdapter.StarViewHolder> implements Filterable {
    private List<Star> stars;
    private List<Star> starsFilter;
    private NewFilter filter;
    private Context context;

    public StarAdapter(List<Star> stars, Context context) {
        this.stars = stars;
        this.context = context;
    }


    @NonNull
    @Override
    public StarAdapter.StarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull StarAdapter.StarViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public NewFilter getFilter() {
        return null;
    }


    public class StarViewHolder extends RecyclerView.ViewHolder{
        TextView id;
        TextView name;
        ImageView img;
        RatingBar stars;
        RelativeLayout parent;

        public StarViewHolder(@NonNull View itemView, TextView id, TextView name, ImageView img, RatingBar stars, RelativeLayout parent) {
            super(itemView);
            this.id = id;
            this.name = name;
            this.img = img;
            this.stars = stars;
            this.parent = parent;
        }
    }
}
