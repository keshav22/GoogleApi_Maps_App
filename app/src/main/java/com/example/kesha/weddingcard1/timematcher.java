package com.example.kesha.weddingcard1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by kesha on 05-05-2018.
 */

public class timematcher  {





SharedPreferences sh;

    public int matchtime(ArrayList<Integer> IDList,ArrayList<String> dateList,ArrayList<String> timeList,int i)
    {


        Date j = Calendar.getInstance().getTime();
        SimpleDateFormat df0 = new SimpleDateFormat("yyyy");
        String Realtimeyear = df0.format(j);

        df0 = new SimpleDateFormat("MM");
        String Realtimemonth = df0.format(j);

        df0 = new SimpleDateFormat("dd");
        String Realtimedate = df0.format(j);

        df0 = new SimpleDateFormat("HH");
        String Realtimehour = df0.format(j);

        df0 = new SimpleDateFormat("mm");
        String Realtimeminute = df0.format(j);
        int siz = IDList.size();
        int qz=0;



            for (qz = i+1; qz < siz; qz++)
            {
                String[] date1 = dateList.get(qz).split("-");
                String[] time = timeList.get(qz).split(":");

                int realtimehr = Integer.parseInt(Realtimehour.toString());
                int eventhr = Integer.parseInt(time[0].toString());
                int realtimeyear = Integer.parseInt(Realtimeyear.toString());
                int eventyear = Integer.parseInt(date1[2].toString());
                int realtimemonth = Integer.parseInt(Realtimemonth.toString());
                int eventmonth = Integer.parseInt(date1[1].toString());
                int realtimedate = Integer.parseInt(Realtimedate.toString());
                int eventdate = Integer.parseInt(date1[0].toString());


                int previousday = eventdate;
                int previousyear = eventyear;
                int previousmonth = eventmonth;

                if (eventdate == 1) {
                    if (eventmonth == 1) {

                        previousyear = previousyear - 1;

                        previousday = 31;

                        previousmonth = 12;

                    } else if (eventmonth == 3 || eventmonth == 5 || eventmonth == 7 || eventmonth == 8 || eventmonth == 10 || eventmonth == 12) {

                        previousmonth = previousmonth - 1;

                        previousday = 30;

                    } else {

                        previousmonth = previousmonth - 1;

                        previousday = 31;

                    }

                } else {
                    previousday = eventdate - 1;
                }

                if (realtimeyear == previousyear || realtimeyear == eventyear) {
                    if (realtimemonth == previousmonth || realtimemonth == eventmonth) {
                        if (realtimedate == previousday)
                        {
                            if (realtimehr == 18 || realtimehr == 21) {


                                    return IDList.get(qz);
                            }
                        }
                        else if (realtimedate == eventdate)
                        {
                            if (eventhr - 6 > 8) {
                                if (realtimehr == (eventhr - 6)) {


                                        return IDList.get(qz);
                                }
                            }
                            if (realtimehr == eventhr) {

                                    return IDList.get(qz);
                            }

                        }
                    }
                }


            }


        return -1;
    }


}
