package com.mycompany.hotdog;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(String result) {
        if (result.equals("Success")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else if (result.equals("NoResult")){
            Toast.makeText(this, "Wrong username/password", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.okLoginButton){
            if (validateUsername(username.getText().toString())
                    && validatePassword(password.getText().toString())){
                HashMap postData = new HashMap();
                postData.put("mobile", "android");
                postData.put("username", username.getText().toString());
                postData.put("password", password.getText().toString());

                PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
                task.execute("http://folk.ntnu.no/sigurbe/login.php");

            }
        }

    }

    private boolean validateUsername(String s) {
        return true;
    }

    private boolean validatePassword(String s){
        return true;
    }
}
