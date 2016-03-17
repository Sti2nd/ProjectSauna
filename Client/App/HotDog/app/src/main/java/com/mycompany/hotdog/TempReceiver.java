package com.mycompany.hotdog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 Denne klassen fungerer som et ledd mellom MainActivity og Servicen (som kalles i bakgrunnen hvert n'te millisekund.
 */
public class TempReceiver extends BroadcastReceiver {

    private String raspnum;
    public static String ACTION_TEMP = "com.mycompany.hotdog";


    //Denne metoden kalles når receiveren opprettes.
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Alarm Receiver", "Entered");

        //Henter ut raspberrynummer sendt fra main activity
        Bundle bundle = intent.getExtras();
        String action = bundle.getString(ACTION_TEMP);
        raspnum = bundle.getString("raspnum");

        if (action.equals(ACTION_TEMP)) {
            Log.i("Alarm Receiver", "If loop");

            //Send med raspberrynummer, og start service-klassen.
            Intent inService = new Intent(context,TempService.class);
            inService.putExtra("raspnum", raspnum);
            context.startService(inService);
        }
        else
        {
            Log.i("Alarm Receiver", "Else loop");
        }
    }
}
