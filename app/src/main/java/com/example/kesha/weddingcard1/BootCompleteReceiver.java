package com.example.kesha.weddingcard1;



        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;

        import com.example.kesha.weddingcard1.myService;

        import static android.content.Context.MODE_PRIVATE;

public class BootCompleteReceiver extends BroadcastReceiver {
    SharedPreferences sh;
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent pushIntent = new Intent(context, myService.class);
            context.startService(pushIntent);
            /*sh=context.getSharedPreferences("scancnt17",MODE_PRIVATE);
            SharedPreferences.Editor editor1=sh.edit();
            editor1.putString("cnt7","0");
            editor1.commit();*/
        }
    }

}
