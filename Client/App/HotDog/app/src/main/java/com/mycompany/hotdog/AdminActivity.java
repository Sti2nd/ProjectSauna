package com.mycompany.hotdog;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.kosalgeek.asynctask.AsyncResponse;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by corneliusgriegdahling on 04/04/16.
 */


public class AdminActivity extends ActionBarActivity implements View.OnClickListener, AsyncResponse {

    private TextView textView;

    //Kalles når aktivitet starter
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void setAdminInfo(){

    }

    @Override
    public void processFinish(String s) {
        setAdminInfo();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
