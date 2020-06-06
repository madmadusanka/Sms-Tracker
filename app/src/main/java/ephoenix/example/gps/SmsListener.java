package ephoenix.example.gps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import static android.content.Context.MODE_PRIVATE;

public class SmsListener extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO Auto-generated method stub
        SmsManager sms=SmsManager.getDefault();
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();//---get the SMS message passed in---

            SmsMessage[] msgs = null;
            SharedPreferences sharedPreferences = context.getSharedPreferences("settings", MODE_PRIVATE);
            String pw= sharedPreferences.getString("pw", "Null");
            String pn= sharedPreferences.getString("pn", "Null");
            String msg_from;
            if (bundle != null){
                //---retrieve the SMS message received---
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();
                        String msgNumbr = msgs[i].getDisplayOriginatingAddress();

                        if (msgNumbr.equals(pn)){
                            if (msgBody.equals(pw)) {

                                GpsTracker gt = new GpsTracker(context);
                                Location l = gt.getLocation();
                                double lat = l.getLatitude();
                                double lon = l.getLongitude();
                                String msg1 = lat + "  " + lon;
                                sms.sendTextMessage(pn, null, msg1, null, null);
                                MainActivity mn = new MainActivity();

                            }
                    }
                }


                }catch(Exception e){
//                            Log.d("Exception caught",e.getMessage());

                    sms.sendTextMessage("+94717888566",null,"not work",null,null);

                }
            }
        }
    }
}