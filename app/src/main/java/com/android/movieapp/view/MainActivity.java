package com.android.movieapp.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.movieapp.R;
import com.android.movieapp.common.BaseActivity;
import com.android.movieapp.common.DividerItemDecoration;
import com.android.movieapp.common.RecyclerTouchListener;
import com.android.movieapp.modelobject.MovieListBaseResponse;
import com.android.movieapp.modelobject.Result;
import com.android.movieapp.network.APIClient;
import com.android.movieapp.network.APIInterface;
import com.android.movieapp.network.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<Result> movieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolBar();
        showProgressDialog();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        APIInterface apiService =
                APIClient.getClient().create(APIInterface.class);

        Call<MovieListBaseResponse> call = apiService.getMovieList(Constant.API_KEY);
        call.enqueue(new Callback<MovieListBaseResponse>() {
            @Override
            public void onResponse(Call<MovieListBaseResponse> call, Response<MovieListBaseResponse> response) {
                int statusCode = response.code();

                dismissProgressDialog();
                if (statusCode == 200 && response.body() != null && !response.body().getResults().isEmpty()) {

                    movieList = response.body().getResults();
                    mAdapter = new MoviesAdapter(MainActivity.this, response.body().getResults());
                    recyclerView.setAdapter(mAdapter);
                }

            }

            @Override
            public void onFailure(Call<MovieListBaseResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                dismissProgressDialog();
            }
        });


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Result movie = movieList.get(position);

                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                intent.putExtra("movie_id", movie.getId());
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    @Override
    public void setUpToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

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
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

    }


}
