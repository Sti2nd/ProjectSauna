package com.mycompany.hotdog;

import android.content.Intent;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends ActionBarActivity implements View.OnClickListener, AsyncResponse {

    private EditText username, email, password, retypePassword, raspnum;
    private static final String url = "jdbc:mysql://mysql.stud.ntnu.no/roberei_hotdog_db";
    private static final String user = "roberei_hotdog";
    private static final String pass = "pekka";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText)findViewById(R.id.inputUsername);
        email = (EditText)findViewById(R.id.inputEmail);
        password = (EditText)findViewById(R.id.inputPassword);
        retypePassword = (EditText)findViewById(R.id.inputRetypePassword);
        raspnum = (EditText)findViewById(R.id.inputRaspnum);

        // Setting OK-button.
        Button okButton = (Button)findViewById(R.id.okButton);
        okButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    public void onLoginClick(){

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.okButton) {
            if (validateUsername(username.getText().toString())
                    && validateEmail(email.getText().toString())
                    && validatePassword(password.getText().toString())
                    && validateRetypePassword(password.getText().toString(), retypePassword.getText().toString())
                    && validateRaspnum(raspnum.getText().toString())) {
                HashMap postData = new HashMap();
                postData.put("mobile", "android");
                postData.put("username", username.getText().toString());
                postData.put("email", email.getText().toString());
                postData.put("password", password.getText().toString());
                postData.put("raspnum", raspnum.getText().toString());

                PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
                task.execute("http://folk.ntnu.no/sigurbe/register.php");

            }
        }
        else if (v.getId() == R.id.loginlink){
            startLoginActivity();
        }
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    @Override
    public void processFinish(String result) {
        if (result.equals("Success")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }


    private boolean validateUsername(String username){
        if (username.length() < 5){
            Toast.makeText(this, "Username must be at least 5 characters.", Toast.LENGTH_SHORT).show();
        }
        return username.length() >= 5;
    }

    private boolean validateEmail(String email){
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        if (! m.matches()){
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
        }
        return m.matches();
    }

    private boolean validatePassword(String password){
        if (password.length() < 6){
            Toast.makeText(this, "Password must be at least 6 characters.", Toast.LENGTH_SHORT).show();
        }
        return password.length() >= 6;
    }

    private boolean validateRetypePassword(String password, String retypePassword){
        if (!password.equals(retypePassword)){
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
        }
        return password.equals(retypePassword);
    }

    private boolean validateRaspnum(String s) {
        int numb = 0;
        try {
            numb = Integer.parseInt(s);
        } catch (Exception e) {
            Toast.makeText(this, "Invalid Raspberry-number", Toast.LENGTH_SHORT).show();
        }
        if (numb < 0) {
            Toast.makeText(this, "Invalid Raspberry-number", Toast.LENGTH_SHORT).show();
        }
        return numb >= 0;
    }

}
