package ephoenix.example.gps;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gps.R;

import com.google.android.gms.ads.InterstitialAd;



public class settngs extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settngs);
           savedSettings();

    }

    private void savedSettings(){
        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        TextView pnv = (TextView) findViewById(R.id.textView2);
        TextView pwv = (TextView) findViewById(R.id.textView3);
        pnv.setText(sharedPreferences.getString("pn", "Null"));
        pwv.setText(sharedPreferences.getString("pw", "Null"));
    }

    public void savesettings(View view){
        EditText pn=(EditText) findViewById(R.id.editText);
        EditText pw=(EditText) findViewById(R.id.editText2);
        String phonenumber = pn.getText().toString();
        String password = pw.getText().toString();
        if(password.equals("")|| phonenumber.equals("")){
            Toast toast = Toast.makeText(this,"Phonenumber or Key cant't be empty ",Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
            SharedPreferences.Editor state = sharedPreferences.edit();
            state.putString("pn", phonenumber);
            state.putString("pw", password);
            state.commit();
            pn.setText("");
            pw.setText("");
            savedSettings();
        }
    }
    public void home(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void permision(View view){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(this).setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(settngs.this,
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},1);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        }
        else{
            Toast toast=Toast.makeText(this,"Permision already granted",Toast.LENGTH_SHORT);
            toast.show();

        }
    }
    public void permisions(View view){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(this).setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(settngs.this,
                                    new String[]{Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS,Manifest.permission.SEND_SMS},1);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        }
        else{
            Toast toast=Toast.makeText(this,"Permision already granted",Toast.LENGTH_SHORT);
            toast.show();

        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){

        }
        return super.onKeyDown(keyCode, event);
    }


}
