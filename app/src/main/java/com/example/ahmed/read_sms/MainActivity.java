package com.example.ahmed.read_sms;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txt_sms;
    Uri uri;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_sms = (TextView) findViewById(R.id.text_sms);
    }

    public void readSMS(View view) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_SMS}, 10);
            } else {
                read();
            }
        } else {
            read();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 10:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    read();
                break;
        }
    }

    private void read() {
        String oneSMS;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
            cursor.moveToPosition(0);
            while (!cursor.isAfterLast()) {
                oneSMS = "From: " + cursor.getString(cursor.getColumnIndex("address")) + " , \n" + cursor.getString(cursor.getColumnIndex("body")) + "\n---------\n";
                cursor.moveToNext();
                stringBuilder.append(oneSMS);
            }
            txt_sms.setText(stringBuilder);
        } catch (Exception e) {
        }
    }

}
