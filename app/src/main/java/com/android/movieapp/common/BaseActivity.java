package com.android.movieapp.common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.movieapp.R;
import com.android.movieapp.view.MainActivity;

/**
 * Created by sahnawajbiswas on 9/12/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public ProgressDialog mProgressDialog;
    public Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public abstract void setUpToolBar();

    public abstract void showProgressDialog();

    public abstract void dismissProgressDialog();


}
