package com.example.kesha.weddingcard1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kesha on 05-05-2018.
 */

public class myService extends Service

{

    Context context;
    private Timer timer;
    private TimerTask timerTask;
    SQLiteDatabase myDB;
    Runnable mRunnable;
    Databasehelper db;
    ArrayList<Integer> IDList = new ArrayList();
    ArrayList<String> dateList = new ArrayList();
    ArrayList<String> timeList = new ArrayList();
    SharedPreferences sh;
    private RemoteViews remoteViews;
    final class Mythreadclass implements  Runnable
    {

        int service_id;

        Mythreadclass (int service_id)
        {
            this.service_id=service_id;
        }

        @Override
        public void run()
        {
            timer = new Timer();
            timerTask = new TimerTask() {
                public void run() {
                    //Toast.makeText(context,"Service Started",Toast.LENGTH_LONG).show();
                    Cursor sc = db.getdatetime();
                    while (sc.moveToNext())
                    {
                        IDList.add(sc.getInt(0));
                        dateList.add(sc.getString(1));
                        timeList.add(sc.getString(2));
                    }
                    int size=IDList.size();
                    Date j = Calendar.getInstance().getTime();

                    SimpleDateFormat df0 = new SimpleDateFormat("HH");
                    String Realtimehour = df0.format(j);

                    sh = getSharedPreferences("cnt15", MODE_PRIVATE);
                    String cnt = sh.getString("no", "");

                    int id=1;

                    if(Realtimehour.compareTo(cnt)==0 )
                    {
                            int val = 0;

                            remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
                            timematcher tm = new timematcher();
                             id = tm.matchtime(IDList, dateList, timeList, 0);

                            Cursor sc1 = db.getcnt(id);
                           while( sc1.moveToNext()) {
                               val = sc1.getInt(9);
                           }
                            while (val != 0) {


                                    id = tm.matchtime(IDList, dateList, timeList, id);


                                    if (id == -1) {
                                    break;

                                }
                                sc1 = db.getcnt(id);
                                sc1.moveToNext();
                                val = sc1.getInt(9);

                            }



                            if (id != -1 && val != 1) {

                                db.updatecnt(id);
                                Cursor cs = db.getonedata(id);
                                if (cs.getCount() == 0) {

                                }
                                String eventname = "";
                                String category = "";
                                String longitude = "";
                                String latitude = "";

                                while (cs.moveToNext()) {
                                    category = cs.getString(0);
                                    eventname = cs.getString(1);
                                    longitude = cs.getString(2);
                                    latitude = cs.getString(3);
                                }

                                remoteViews.setTextViewText(R.id.eventname, eventname);
                                remoteViews.setTextViewText(R.id.category, category);
                                remoteViews.setTextViewText(R.id.longitude, longitude);
                                remoteViews.setTextViewText(R.id.latitude, latitude);
                                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                                Intent intent = new Intent(myService.this, MainActivity.class);
                                PendingIntent pIntent = PendingIntent.getActivity(myService.this, 0, intent,
                                        PendingIntent.FLAG_UPDATE_CURRENT);
                                android.support.v7.app.NotificationCompat.Builder builder = (android.support.v7.app.NotificationCompat.Builder) new android.support.v7.app.NotificationCompat.Builder(myService.this)

                                        .setSmallIcon(R.mipmap.ic_launcher_round)
                                        .setContentTitle(eventname)
                                        .setTicker(eventname)
                                        .setSound(uri)
                                        .setCustomBigContentView(remoteViews)
                                        .setContentIntent(pIntent)
                                        .setVibrate(new long[]{Notification.DEFAULT_VIBRATE})
                                        .setPriority(Notification.PRIORITY_MAX)
                                        .setAutoCancel(true);


                                NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                                notificationmanager.notify(0, builder.build());

                            }


                    }
                    else
                    {

                        sh = getSharedPreferences("cnt15", MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = sh.edit();
                        editor1.putString("no",Realtimehour);
                        editor1.commit();
                        db.changecnt();

                    }



                }
            };
            timer.schedule(timerTask, 1000, 5000);

        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Toast.makeText(context,"Service Started",Toast.LENGTH_LONG).show();
        Thread thread= new Thread(new Mythreadclass(startId));
        thread.start();

        /*final Handler mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {

                Toast.makeText(context,"Service Started",Toast.LENGTH_LONG).show();
                Cursor sc=db.getdatetime();
                while(sc.moveToNext())
                {
                    IDList.add(sc.getInt(0));
                    dateList.add(sc.getString(1));
                    timeList.add(sc.getString(2));
                }

                timematcher tm=new timematcher();
                int id=tm.matchtime(IDList,dateList,timeList);
                if(id!=-1)
                {
                    Cursor cs=db.getonedata(id);

                    if(cs.getCount()==0 )
                    {

                    }
                    String eventname="";
                    String category="";

                    while(cs.moveToNext())
                    {
                        category=cs.getString(1);
                        eventname=cs.getString(2);
                    }

                    Backtask3 b=new Backtask3();
                    b.execute(category,eventname);


                }

                    //Toast.makeText(context,String.valueOf(id), Toast.LENGTH_LONG).show();
                mHandler.postDelayed(mRunnable, 10 * 1000);
            }
        };
        mHandler.postDelayed(mRunnable, 10 * 1000);

*/
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {

        //Toast.makeText(this,"Service destroyed",Toast.LENGTH_SHORT).show();
        timer.cancel();
        timer = null;
    }

    @Override
    public void onCreate() {


        context=getApplicationContext();
        db = new Databasehelper(getApplicationContext());



    }

    public class Backtask3 extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... strings) {


            onPostExecute(strings);
            return "1";
        }


        protected void onPostExecute(String... strings) {





        }
    }



}


