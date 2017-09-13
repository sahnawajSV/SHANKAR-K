package com.android.movieapp.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.movieapp.modelobject.Result;
import com.android.movieapp.network.Constant;
import com.bumptech.glide.Glide;

import java.util.List;
import com.android.movieapp.R;



public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private final Context mContext;
    private List<Result> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageview;
        private final TextView details;
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title_txt);
            details = (TextView) view.findViewById(R.id.details_txt);
            imageview = (ImageView) view.findViewById(R.id.imageView);
        }
    }


    public MoviesAdapter(Context context, List<Result> moviesList) {
        this.mContext=context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Result movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.details.setText(movie.getOverview());
        Glide.with(mContext)
                .load(Constant.IMAGE_BASE_URL+movie.getPosterPath())
                .override(200,200)
                .into(holder.imageview);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
