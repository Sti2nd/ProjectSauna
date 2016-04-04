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


    // Deklarerer felter hvor bruker skriver inn info.
    private EditText username, email, password, retypePassword, raspnum;

    //Database
    private static final String url = "jdbc:mysql://mysql.stud.ntnu.no/roberei_hotdog_db";
    private static final String user = "roberei_hotdog";
    private static final String pass = "pekka";



    // Denne metoden kjøres når aktiviteten startes.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Linke felter til xml-fil.
        username = (EditText)findViewById(R.id.inputUsername);
        email = (EditText)findViewById(R.id.inputEmail);
        password = (EditText)findViewById(R.id.inputPassword);
        retypePassword = (EditText)findViewById(R.id.inputRetypePassword);
        raspnum = (EditText)findViewById(R.id.inputRaspnum);

        // Setter opp OK-knapp, og setter opp clicklistener.
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


    // Denne metoden blir kalt når OK blir trykket, eller når teksten til Login blir trykket.
    @Override
    public void onClick(View v) {
        //Sjekker om knappen ble trykket på.
        if (v.getId() == R.id.okButton) {

            //Validerer feltene vha. private metoder.
            if (validateUsername(username.getText().toString())
                    && validateEmail(email.getText().toString())
                    && validatePassword(password.getText().toString())
                    && validateRetypePassword(password.getText().toString(), retypePassword.getText().toString())
                    && validateRaspnum(raspnum.getText().toString())) {

                //postData sendes til PHP-serveren for å legge inn verdier.
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
        //Sjekker om login-teksten ble trykket på. Starter i såfall Login-aktiviteten
        else if (v.getId() == R.id.loginlink){
            startLoginActivity();
        }
    }

    //Starter Login-aktivitet
    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //Denne metoden kalles av AsyncTask når PHP-server gir svar.
    @Override
    public void processFinish(String result) {
        //Sjekker om registrering var suksessfull. Sender isåfall bruker videre til MainActivity.
        if (result.equals("Success")){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("raspnum", raspnum.getText().toString());
            startActivity(intent);
        } else if (result.equals("error")){
            Toast.makeText(this, "Raspberry Pi number isn't registered in our database", Toast.LENGTH_SHORT);
        }
    }

    //Validerer brukernavn
    private boolean validateUsername(String username){
        if (username.length() < 5){
            //Gi beskjed til brukeren dersom brukernavn er ugyldig
            Toast.makeText(this, "Username must be at least 5 characters.", Toast.LENGTH_SHORT).show();
        }
        return username.length() >= 5;
    }

    //Validerer email
    private boolean validateEmail(String email){
        //Lager regex-pattern, slik at kun gyldig email blir godkjent.
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        if (! m.matches()){
            //Gi beskjed til brukeren dersom mail er ugyldig
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
        }
        return m.matches();
    }

    private boolean validatePassword(String password){
        if (password.length() < 6){
            //Gi beskjed til brukeren dersom passord er ugyldig
            Toast.makeText(this, "Password must be at least 6 characters.", Toast.LENGTH_SHORT).show();
        }
        return password.length() >= 6;
    }

    private boolean validateRetypePassword(String password, String retypePassword){
        if (!password.equals(retypePassword)){
            //Gi beskjed til brukeren dersom retype-passord er ulikt passord.
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
        }
        return password.equals(retypePassword);
    }

    private boolean validateRaspnum(String s) {
        int numb = 0;

        //Sjekker om det er oppgitt heltall.
        try {
            numb = Integer.parseInt(s);
        } catch (Exception e) {
            //Gi beskjed til brukeren dersom oppgitt verdi ikke er heltall
            Toast.makeText(this, "Invalid Raspberry-number", Toast.LENGTH_SHORT).show();
        }
        if (numb < 0) {
            //Gi beskjed til brukeren dersom tall er ugyldig
            Toast.makeText(this, "Invalid Raspberry-number", Toast.LENGTH_SHORT).show();
        }
        return numb >= 0;
    }

}
