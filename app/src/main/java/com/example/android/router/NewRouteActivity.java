package com.example.android.router;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

/**
 * Activity for entering a word.
 */

public class NewRouteActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_PHONE_NUMBER = NewRouteActivity.class.getCanonicalName() + "EXTRA_REPLY_PHONE_NUMBER";
    public static final String EXTRA_REPLY_URL = NewRouteActivity.class.getCanonicalName() + "EXTRA_REPLY_URL";

    private EditText mEditUrlView;
    private EditText mEditPhoneNumberView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_route);
        mEditUrlView = findViewById(R.id.edit_url);
        mEditPhoneNumberView = findViewById(R.id.edit_text_phone);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditPhoneNumberView.getText()) || TextUtils.isEmpty(mEditUrlView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String phoneNumber = mEditPhoneNumberView.getText().toString();
                String url = mEditUrlView.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY_PHONE_NUMBER, phoneNumber);
                replyIntent.putExtra(EXTRA_REPLY_URL, url);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}

