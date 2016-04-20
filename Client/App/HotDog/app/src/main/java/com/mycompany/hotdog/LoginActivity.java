package com.mycompany.hotdog;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener, AsyncResponse {

    //Setter felt
    private EditText username, password;


    //Kalles når aktivitet starter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Linker felt til xml
        username = (EditText)findViewById(R.id.inputLoginUsername);
        password = (EditText)findViewById(R.id.inputLoginPassword);

        // Setting ok-button.
        Button okLoginButton = (Button)findViewById(R.id.okLoginButton);
        okLoginButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }
    

    //Kalles når PHP-server gir svar
    @Override
    public void processFinish(String result) {
        //Gi beskjed til bruker om at brukernavn/passord er feil.
        if (result.equals("NoResult")){
            Toast.makeText(this, "Wrong username/password", Toast.LENGTH_SHORT).show();
        }
        //Dersom login var suksessfullt, gå til main-activity.
        else {
            if (isNetworkAvailable()) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("raspnum", result);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "No internet connection. Please connect and try again!", Toast.LENGTH_LONG).show();
            }
        }
    }

    //Sjekker om nettverk er tilgjengelig
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    //Kalles når en knapp trykkes
    @Override
    public void onClick(View v) {
        // Sjekker at OK-knapp ble trykket
        if (v.getId() == R.id.okLoginButton){

            //Validerer brukernavn og passord
            if (validateUsername(username.getText().toString())
                    && validatePassword(password.getText().toString())){

                //Sender data til PHP-server.
                HashMap postData = new HashMap();
                postData.put("mobile", "android");
                postData.put("username", username.getText().toString());
                postData.put("password", password.getText().toString());

                PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
                task.execute("http://folk.ntnu.no/sigurbe/login.php");

            }
        }

    }

    public void onClickCreateAccount(View v) {
       Intent intent = new Intent(this, RegisterActivity.class);
       startActivity(intent);
   }

    private boolean validateUsername(String s) {
        return true;
    }

    private boolean validatePassword(String s){
        return true;
    }
}
