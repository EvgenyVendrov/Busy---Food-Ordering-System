package com.example.busy.Admin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        CharSequence iData = intent.getCharSequenceExtra("msg");
        Toast.makeText(context, "Message: " + iData, Toast.LENGTH_LONG).show();
    }
}

