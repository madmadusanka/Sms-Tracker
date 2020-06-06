package ephoenix.example.gps;

import android.location.Location;
import androidx.appcompat.app.AppCompatActivity;
import android.telephony.SmsManager;

public class send extends AppCompatActivity {
    public void sends(){

        GpsTracker gt = new GpsTracker(getApplicationContext());
        Location l = gt.getLocation();
        SmsManager sms=SmsManager.getDefault();
        if( l == null){
            sms.sendTextMessage("+94717888566",null,"work bt nt snd",null,null);
        }else {
            double lat = l.getLatitude();
            double lon = l.getLongitude();
            String msg=lat+"  "+lon;

            sms.sendTextMessage("+94717888566",null,msg,null,null);
        }

    }

}
