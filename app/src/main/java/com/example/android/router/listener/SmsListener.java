package com.example.android.router.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.router.RouteViewModel;
import com.example.android.router.component.MyApplication;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Map;


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
                        Toast.makeText(context, "No active route defined for " + msg_from, Toast.LENGTH_LONG).show();
                    }

                    StringBuilder msgBody = new StringBuilder();
                    for (SmsMessage smsMessage : messages) {
                        msgBody.append(smsMessage.getMessageBody());
                    }
                    RequestQueue volleyQueue = Volley.newRequestQueue(context);

                    JSONObject jsonPayload = new JSONObject();
                    jsonPayload.put("text", "*" + msg_from + "*" + "\n" + msgBody);

                    JsonRequest<String> jsonObjectRequest = new JsonRequest<String>(Request.Method.POST, url, jsonPayload.toString(), response -> {
                        Toast.makeText(context, "Message status " + response, Toast.LENGTH_LONG).show();
                    }, error -> {
                        Toast.makeText(context, "Cannot send the message", Toast.LENGTH_LONG).show();
                        Log.e("SMSListener", "error: " + error);
                    }) {
                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            try {
                                return Response.success(new String(response.data, StandardCharsets.UTF_8), HttpHeaderParser.parseCacheHeaders(response));
                            } catch (Exception e) {
                                return Response.error(new ParseError(e));
                            }
                        }
                    };
                    volleyQueue.add(jsonObjectRequest);

                } catch (Exception e) {
                    Log.d("Exception caught", e.getMessage());
                }
            }
        }
    }

}
