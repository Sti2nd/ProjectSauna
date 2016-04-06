package com.mycompany.hotdog;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;


public class MainActivity extends ActionBarActivity implements AsyncResponse {

    private String raspnum;
    private TextView tempView;
    private ImageView dogState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hent raspnum fra bundle
        Bundle bundle = getIntent().getExtras();
        raspnum = bundle.getString("raspnum");

        tempView = (TextView)findViewById(R.id.show_temp);
        setTemp();
        dogState = (ImageView)findViewById(R.id.ic_dog_state);

        //Start sjekk av temp i bakgrunn
        startBackgroundService();
    }

    private void startBackgroundService() {
        try {
            AlarmManager alarms = (AlarmManager) this
                    .getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(getApplicationContext(), TempReceiver.class);
            intent.putExtra(TempReceiver.ACTION_TEMP, TempReceiver.ACTION_TEMP);
            intent.putExtra("raspnum", raspnum);

            final PendingIntent pIntent = PendingIntent.getBroadcast(this, 1234567, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarms.setRepeating(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(), 60000, pIntent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTemp() {
        //Send raspnum til PHP-server
        HashMap postData = new HashMap();
        postData.put("mobile", "android");
        postData.put("raspnum", raspnum);

        PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
        task.execute("http://folk.ntnu.no/sigurbe/getTemperature.php");
    }

    @Override
    public void processFinish(String s) {
        //endre tekst til temperatur.
        tempView.setText(s);
        double temperature = Double.parseDouble(s);
        if (temperature < 25){
            dogState.setImageResource(R.drawable.dog_en);
        } else if (temperature < 35){
            dogState.setImageResource(R.drawable.dog_to);
        } else if (temperature < 45){
            dogState.setImageResource(R.drawable.dog_tre);
        } else{
            dogState.setImageResource(R.drawable.dog_fire);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onClickFeedback(View view) {
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }


    public void onClickLogout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClickRefresh(View view){
        setTemp();
    }

}
