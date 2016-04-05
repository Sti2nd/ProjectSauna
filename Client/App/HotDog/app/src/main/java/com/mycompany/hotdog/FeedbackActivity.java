package com.mycompany.hotdog;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.kosalgeek.asynctask.AsyncResponse;

/**
 * Created by corneliusgriegdahling on 04/04/16.
 */


public class FeedbackActivity extends ActionBarActivity implements View.OnClickListener, AsyncResponse {

    private TextView textView;

    //Kalles når aktivitet starter
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
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
