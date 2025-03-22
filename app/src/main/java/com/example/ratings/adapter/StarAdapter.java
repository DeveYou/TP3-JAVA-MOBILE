package com.example.ratings.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ratings.R;
import com.example.ratings.beans.Star;
import com.example.ratings.service.StarService;


import java.util.ArrayList;
import java.util.List;

public class StarAdapter extends  RecyclerView.Adapter<StarAdapter.StarViewHolder> implements Filterable {
    private List<Star> stars;
    private List<Star> starsFilter;
    private NewFilter filter;
    private Context context;

    public StarAdapter(List<Star> stars, Context context) {
        this.stars = stars;
        this.context = context;
        starsFilter = new ArrayList<>();
        starsFilter.addAll(stars);
        filter = new NewFilter(this);
    }


    @NonNull
    @Override
    public StarAdapter.StarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        final StarViewHolder holder = new StarViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popup = LayoutInflater.from(context).inflate(R.layout.activity_edit_item, null, false);
                final ImageView img = popup.findViewById(R.id.img);
                final RatingBar bar = popup.findViewById(R.id.ratingBar);
                final  TextView id = popup.findViewById(R.id.id);

                Bitmap bitmap = ((BitmapDrawable) ((ImageView)v.findViewById(R.id.img)).getDrawable()).getBitmap();
                img.setImageBitmap(bitmap);
                bar.setRating(((RatingBar)v.findViewById(R.id.stars)).getRating());
                id.setText(((TextView)v.findViewById(R.id.id)).getText().toString());
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setView(popup)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                float s = bar.getRating();
                                int ids = Integer.parseInt(id.getText().toString());
                                Star star = StarService.getInstance().findById(ids);
                                star.setStar(s);
                                StarService.getInstance().update(star);
                                notifyItemChanged(holder.getAdapterPosition());
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StarAdapter.StarViewHolder holder, int position) {
        Glide.with(context)
                .asBitmap()
                .load(starsFilter.get(position).getImg())
                .apply(new RequestOptions().override(100, 100))
                .into(holder.img);

        Log.d("StarAdapter", "id : " + holder.id + ", Actor's name : " + holder.name);

        holder.name.setText(starsFilter.get(position).getName().toUpperCase());
        holder.stars.setRating(starsFilter.get(position).getStar());
        holder.id.setText(starsFilter.get(position).getId()+"");

        Log.d("StarAdapter", "id : " + holder.id + ", Actor's name : " + holder.name);
    }

    @Override
    public int getItemCount() {
        return starsFilter.size();
    }

    @Override
    public NewFilter getFilter() {
        return filter;
    }


    public class StarViewHolder extends RecyclerView.ViewHolder{
        TextView id;
        TextView name;
        ImageView img;
        RatingBar stars;
        RelativeLayout parent;

        public StarViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = itemView.findViewById(R.id.id);
            this.name = itemView.findViewById(R.id.name);
            this.img = itemView.findViewById(R.id.img);
            this.stars = itemView.findViewById(R.id.stars);
            this.parent = itemView.findViewById(R.id.parent);
        }
    }

    public class NewFilter extends Filter {
        public RecyclerView.Adapter mAdapter;

        public NewFilter(RecyclerView.Adapter mAdapter) {
            super();
            this.mAdapter = mAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            starsFilter.clear();
            final FilterResults results = new FilterResults();
            if(constraint.length() == 0){
                starsFilter.addAll(stars);
            }else{
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for(Star s : stars){
                    if(s.getName().toLowerCase().startsWith(filterPattern)){
                        starsFilter.add(s);
                    }
                }
            }
            results.values = starsFilter;
            results.count = starsFilter.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                starsFilter = (List<Star>) results.values;
                this.mAdapter.notifyDataSetChanged();
        }
    }
}
