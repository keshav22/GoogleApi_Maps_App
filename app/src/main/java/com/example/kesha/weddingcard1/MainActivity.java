package com.example.kesha.weddingcard1;


import android.*;
import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;


import com.google.android.gms.vision.barcode.Barcode;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public class MainActivity extends AppCompatActivity  {
    NotificationCompat.Builder mBuilder;
    int view=0;
    Button b;
    Context context = this;
    Databasehelper db;
    String cnt="";
    SharedPreferences sh;
    public static final int REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = (Button) findViewById(R.id.notify);
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setAutoCancel(true);
        db=new Databasehelper(this);
        //sh = getSharedPreferences("scancnt17", MODE_PRIVATE);
        //cnt = sh.getString("cnt7", "");


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            return;
        }
      Intent in = new Intent(MainActivity.this, myService.class);
        startService(in);
    }

    public void firstdataadd()
    {

            Intent in = new Intent(MainActivity.this, myService.class);
            startService(in);
    }



    public void qrscanner(View v)
    {
        Intent intent = new Intent(MainActivity.this, ScanActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
        view=1;
        /*scannerView=new ZXingScannerView(this);
        setContentView(scannerView);
        scannerView.setResultHandler(this);
        scannerView.startCamera();
        */
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode;
                barcode = data.getParcelableExtra("barcode");
                handleResult(barcode.displayValue.toString());
            }
        }
    }
    public void notification(View view)
    {



        /*mBuilder.setSmallIcon(R.drawable.ic_launcher_background);
        mBuilder.setTicker("This is ticket");
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setContentTitle("Keshav");
        mBuilder.setContentText("I am the body");
        Intent in=new Intent(this,MainActivity.class);
        PendingIntent pi= PendingIntent.getActivity(this, 0, in,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(456123, mBuilder.build());

*/

       /* Cursor cs=db.getAlldata();

        if(cs.getCount()==0 )
        {
            Toast.makeText(getApplicationContext(),"No",Toast.LENGTH_LONG).show();
        }*/
       /* else
        {
            int n=cs.getCount();
            String p=String.valueOf(n);
            Toast.makeText(getApplicationContext(),p,Toast.LENGTH_LONG).show();
        }*/

       // Intent n=new Intent(this,maps1.class);
       // startActivity(n);

        /*StringBuffer buffer =new StringBuffer();
        while(cs.moveToNext())
        {
            buffer.append("Id :"+cs.getString(0)+ "\n");
            buffer.append("Category :"+cs.getString(1)+ "\n");
            buffer.append("Event_name :"+cs.getString(2)+ "\n");
            buffer.append("Longitude :"+cs.getString(3)+ "\n");
            buffer.append("Latitude :"+cs.getString(4)+ "\n");
            buffer.append("Start_date :"+cs.getString(5)+ "\n");
            buffer.append("End_date :"+cs.getString(6)+ "\n");
            buffer.append("Start_time :"+cs.getString(7)+ "\n");
            buffer.append("end_time :"+cs.getString(8)+ "\n");
            buffer.append("cnt :"+cs.getString(9)+"\n");
        }

        Toast.makeText(getApplicationContext(),buffer.toString(),Toast.LENGTH_LONG).show();


        Boolean n=db.updatecnt(1);
        if(n==false)
        {
            Toast.makeText(getApplicationContext(),"Not updated",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_LONG).show();
        int val=0;
        Cursor sc1 = db.getcnt(1);
        while(sc1.moveToNext()) {
             val= sc1.getInt(9);
        }
        Toast.makeText(getApplicationContext(),String.valueOf(val),Toast.LENGTH_LONG).show();
        Boolean n1=db.changecnt();
        if(n1==false)
        {
            Toast.makeText(getApplicationContext(),"Not updated",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_LONG).show();
*/

    }



    public void handleResult(String result)
    {

        sh = getSharedPreferences("scancnt37", MODE_PRIVATE);
        cnt = sh.getString("cnt7", "");

        if(cnt.compareTo("1")!=0 )
        {
            firstdataadd();
            sh=getSharedPreferences("scancnt37",MODE_PRIVATE);
            SharedPreferences.Editor editor1=sh.edit();
            editor1.putString("cnt7","1");
            editor1.commit();
        }


        String[] separated = result.toString().split("\n");
        int n=separated.length;

        if(n>=8)
        {
            int i=0;
            int cnt1=9;
            int cnt=0;
            while(cnt1>=9)
            {

                int i1=0;
                String [] events=new String[9];

                while(i1!=9)
                {
                    if(separated[i].compareTo("")==0)
                        break;
                    events[i1] = separated[i];
                    i=i+1;
                    i1=i1+1;
                }
                cnt++;
                if(i1==9)
                {
                    i=i+1;
                    Boolean b = db.insertdata(events);

                    if (b == true)
                    {

                        Toast.makeText(getApplicationContext(), "Data inserted", Toast.LENGTH_SHORT).show();

                    }

                }

                else

                {

                    i=cnt*8+1;
                    //Toast.makeText(getApplicationContext(), "Data error", Toast.LENGTH_SHORT).show();
                }

                cnt1=n-i;

            }

            setContentView(R.layout.activity_main);

        }


        else
        {

            setContentView(R.layout.activity_main);

            Toast.makeText(getApplicationContext(), "QR Code not Valid", Toast.LENGTH_SHORT).show();
    }
       /* if(n>=8) {
            Boolean b = db.insertdata(separated);

            if (b == true) {
                Toast.makeText(getApplicationContext(), "Data inserted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Data not inserted", Toast.LENGTH_SHORT).show();
            }
            setContentView(R.layout.activity_main);
        }
        else {
            scannerView.resumeCameraPreview(this);
            Toast.makeText(getApplicationContext(), "QR Code not Valid", Toast.LENGTH_SHORT).show();
        }*/

        /*
        Log.w("Hanfle Result",result.getText());
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(result.getText());
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
        scannerView.resumeCameraPreview(this);
        */

    }

    @Override
    public void onBackPressed()
    {

        if(view==1)
        {
            view=0;
            setContentView(R.layout.activity_main);
        }
        else
            super.onBackPressed();
    }
}
