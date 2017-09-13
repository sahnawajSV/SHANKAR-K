package com.android.movieapp.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.movieapp.R;
import com.android.movieapp.common.BaseActivity;
import com.android.movieapp.modelobject.MovieDetailBaseResponse;
import com.android.movieapp.modelobject.MovieListBaseResponse;
import com.android.movieapp.network.APIClient;
import com.android.movieapp.network.APIInterface;
import com.android.movieapp.network.Constant;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sahnawajbiswas on 9/12/17.
 */

public class MovieDetailActivity extends BaseActivity {

    private static final String TAG = MovieDetailActivity.class.getSimpleName();
    private ImageView imageView;
    private TextView title;
    private TextView details;
    private TextView voter;
    private TextView date;
    private TextView rating;
    private ImageView bannerImageView;

    @Override
    public void showProgressDialog() {
// Set up progress before call
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("loading....");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();

    }

    @Override
    public void dismissProgressDialog() {
        if(mProgressDialog!=null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_view);

        showProgressDialog();
        init();
        int movie_id = getIntent().getExtras().getInt("movie_id");
        APIInterface apiService =
                APIClient.getClient().create(APIInterface.class);

        Call<MovieDetailBaseResponse> call = apiService.getMovieDetails(movie_id,Constant.API_KEY);
        call.enqueue(new Callback<MovieDetailBaseResponse>() {
            @Override
            public void onResponse(Call<MovieDetailBaseResponse> call, Response<MovieDetailBaseResponse> response) {
                int statusCode = response.code();
                dismissProgressDialog();
                if (statusCode == 200 && response.body() != null ) {

                    loadUI(response.body());
                }

            }

            @Override
            public void onFailure(Call<MovieDetailBaseResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                dismissProgressDialog();
            }
        });

    }

    private void init() {

        imageView=(ImageView)findViewById(R.id.detail_imageView);
        bannerImageView=(ImageView)findViewById(R.id.detail_imageView_fullview);
        title=(TextView)findViewById(R.id.title_txt);
        details=(TextView)findViewById(R.id.des_txt);
        voter=(TextView)findViewById(R.id.voter);
        date=(TextView)findViewById(R.id.date);
        rating=(TextView)findViewById(R.id.rating);

    }

    private void loadUI(MovieDetailBaseResponse response) {

        Glide.with(this)
                .load(Constant.IMAGE_BASE_URL+response.getPosterPath())
                .override(300,300)
                .into(imageView);

        Glide.with(this)
                .load(Constant.IMAGE_BASE_URL+response.getBackdropPath())
                .override(600,600)
                .into(bannerImageView);

        title.setText("Title :"+response.getTitle());
        details.setText(response.getOverview());
        voter.setText("Voter : "+response.getVoteCount());
        date.setText("Release Date : "+response.getReleaseDate());
        rating.setText("Rating : "+String.valueOf(response.getVoteAverage())+" / 10 ");

    }

    @Override
    public void setUpToolBar() {
    }
}
