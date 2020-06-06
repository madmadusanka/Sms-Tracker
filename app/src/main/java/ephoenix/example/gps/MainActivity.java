package ephoenix.example.gps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.gps.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import android.Manifest;
import android.location.Location;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnLoc;
    EditText txt;
    private InterstitialAd mInterstitialAd;
    private InterstitialAd mInterstitialAd2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        if(sharedPreferences.getBoolean("time",true)){
            SharedPreferences.Editor state = sharedPreferences.edit();
            state.putBoolean("time",false);
            state.commit();
            Intent intent = new Intent(this, settngs.class);
            startActivity(intent);
            finish();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
               != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)  {
            TextView pm=(TextView) findViewById(R.id.textView4);
            pm.setText("*Location Permison not granted");
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            TextView pm=(TextView) findViewById(R.id.textView5);
            pm.setText("SMS permision not Granted");
        }

        TextView locview = (TextView) findViewById(R.id.textView6);
        try {
            if (gpstatus()) {
                GpsTracker gt = new GpsTracker(getApplicationContext());
                Location l = gt.getLocation();
                double lat = l.getLatitude();
                double lon = l.getLongitude();
                locview.setText(lat+" "+lon);

            } else {
                locview.setText("Enabled Gps to work the application");
            }
        }catch (Exception e){
            locview.setText("Enabled Gps to Active the application");
        }


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd2 = new InterstitialAd(this);
        mInterstitialAd2.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd2.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Intent intent = new Intent(MainActivity.this, settngs.class);
                startActivity(intent);
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mInterstitialAd2.show();
            }
        }, 5000);

    }
    @Override
    public void onBackPressed(){

        System.exit(0);

    }
    public void settings(View view){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Intent intent = new Intent(this, settngs.class);
            startActivity(intent);
        }

    }
    public void click(View view){
         GpsTracker gt = new GpsTracker(getApplicationContext());
         Location l = gt.getLocation();
         if( l == null){
             Toast.makeText(getApplicationContext(),"GPS unable to get Value",Toast.LENGTH_SHORT).show();
         }else {
             double lat = l.getLatitude();
             double lon = l.getLongitude();
             txt.setText(lat+"  "+lon);
             String msg=lat+"  "+lon;
             Toast.makeText(getApplicationContext(),"GPS Lat = "+lat+"\n lon = "+lon,Toast.LENGTH_SHORT).show();
             SmsManager sms=SmsManager.getDefault();
             sms.sendTextMessage("+94710000000",null,msg,null,null);
         }


     }
    public Boolean gpstatus(){
        Context context=getApplicationContext();
        LocationManager locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        Boolean gpstatus=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return gpstatus;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }



}
