package com.mycompany.hotdog;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

/**
 * Created by corneliusgriegdahling on 04/04/16.
 */


public class FeedbackActivity extends ActionBarActivity implements View.OnClickListener, AsyncResponse {

    private EditText feedback;
    private TextView textViewResponse;

    //Kalles når aktivitet starter
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Linke felter til XML
        feedback = (EditText)findViewById(R.id.inputFeedback);
        textViewResponse = (TextView) findViewById(R.id.textViewResponse);
        textViewResponse.setText("");
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.feedbackButton) {
            //postData sendes til PHP-serveren for å legge inn verdier.
            HashMap postData = new HashMap();
            postData.put("mobile", "android");
            postData.put("feedback", feedback.getText().toString());

            PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
            task.execute("http://folk.ntnu.no/cornelgd/feedback.php");
        }

        else if (v.getId() == R.id.logoutButton) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }


    //Denne metoden kalles av AsyncTask når PHP-server gir svar.
    @Override
    public void processFinish(String result) {
        //Sjekker om registrering var suksessfull. Sender isåfall bruker videre til MainActivity.
        if (result.equals("Success")){
            textViewResponse.setText("It was a glaring success");
            Toast.makeText(this, "Feedback was succesfully recorded", Toast.LENGTH_SHORT);
        } else if (result.equals("error")){
            textViewResponse.setText("It did not work");
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT);
        }
    }
}
