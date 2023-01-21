package com.example.android.router.listener;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.router.MainActivity;
import com.example.android.router.RouteViewModel;
import com.example.android.router.component.MyApplication;

import org.json.JSONObject;

import java.util.Map;

import javax.inject.Inject;

import dagger.android.DaggerApplication;
import dagger.android.DaggerBroadcastReceiver;


public class SmsListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            RouteViewModel mRouteViewModel = ((MyApplication) context.getApplicationContext()).getRouteViewModel();

            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            String msg_from;
            if (bundle != null) {
                try {
                    SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                    msg_from = messages[0].getOriginatingAddress();

                    Map<String, String> routes = mRouteViewModel.getRoutesBySMS();
                    String url = routes.get(msg_from);
                    if (url == null) {
                        Toast.makeText(context, "No route defined for " + msg_from, Toast.LENGTH_LONG).show();
                    }

                    StringBuilder msgBody = new StringBuilder();
                    for (SmsMessage smsMessage : messages) {
                        msgBody.append(smsMessage.getMessageBody());
                    }
                    RequestQueue volleyQueue = Volley.newRequestQueue(context);

                    JSONObject jsonPayload = new JSONObject();
                    jsonPayload.put("text", "*" + msg_from + "*" + "\n" + msgBody);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonPayload, response -> {
                        String status = response.toString();
                        Toast.makeText(context, "Message status " + status, Toast.LENGTH_LONG).show();
                    }, error -> {
                        Toast.makeText(context, "Cannot send the message", Toast.LENGTH_LONG).show();
                        Log.e("SMSListener", "error: " + error);
                    });
                    volleyQueue.add(jsonObjectRequest);

                } catch (Exception e) {
                    Log.d("Exception caught", e.getMessage());
                }
            }
        }
    }

}
