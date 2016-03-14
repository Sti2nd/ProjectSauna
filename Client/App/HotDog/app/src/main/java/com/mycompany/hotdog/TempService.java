package com.mycompany.hotdog;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sigurd on 09.03.2016.
 */
public class TempService extends IntentService{

    private String raspnum;
    private Double temp;

    public TempService() {
        super("TempService");
    }


    //Denne blir kalt når servicen opprettes. Når servicen har utført oppgavene sine blir den ødelagt.
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        raspnum = bundle.getString("raspnum");
        setTemp();
    }

    private void setTemp() {
        //Setter opp komminkasjonen med PHP-serveren
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://folk.ntnu.no/sigurbe/getTemperature.php");

        try{
            //Legger til variablene som skal til PHP-serveren
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("raspnum", raspnum));
            nameValuePairs.add(new BasicNameValuePair("mobile", "android"));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            //Lager en response-handler,  kjører PHP-skript
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String response = httpClient.execute(httpPost, responseHandler);

            //Endrer echo fra PHP-server til double
            temp = Double.parseDouble(response);

            //Hvis temperatur er over 40 grader, lag notifikasjon.
            if (temp > 40) {
                createNotification();
            }

        } catch (ClientProtocolException e){
            System.out.println(e.getStackTrace());
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        } catch (NumberFormatException e){
            System.out.println(e.getStackTrace());
        }
    }

    private void createNotification() {
        //Bygger en notification. Stort sett kopiert fra http://developer.android.com/training/notify-user/build-notification.html#action
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_template_icon_bg)
                .setContentTitle("Temperature high!")
                .setContentText("Temperature is " + temp.toString());
        Intent resultIntent = new Intent(this, TempService.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        int mNotificationId = 001;
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }
}
