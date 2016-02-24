package com.mycompany.hotdog;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText username;
    private EditText email;
    private EditText password;
    private EditText retypePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText)findViewById(R.id.inputUsername);
        email = (EditText)findViewById(R.id.inputEmail);
        password = (EditText)findViewById(R.id.inputPassword);
        retypePassword = (EditText)findViewById(R.id.inputRetypePassword);

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

    @Override
    public void onClick(View v) {
        validateUsername(username.getText().toString());
        validateEmail(email.getText().toString());
        validatePassword(password.getText().toString());
        validateRetypePassword(password.getText().toString(), retypePassword.getText().toString());


        Toast.makeText(getApplicationContext(), "Your toast message",
                Toast.LENGTH_LONG).show();

    }

    private boolean validateUsername(String username){
        return username.length() > 4;
    }

    private boolean validateEmail(String email){
        return true;
    }

    private boolean validatePassword(String password){
        return password.length() > 7;
    }

    private boolean validateRetypePassword(String password, String retypePassword){
        return password.equals(retypePassword);
    }

}
