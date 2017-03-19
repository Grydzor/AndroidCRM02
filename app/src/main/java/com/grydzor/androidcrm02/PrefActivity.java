package com.grydzor.androidcrm02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.grydzor.androidcrm02.settings.Settings;

public class PrefActivity extends AppCompatActivity {
    private EditText url;
    private EditText port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);

        url = (EditText) findViewById(R.id.editText3);
        port = (EditText) findViewById(R.id.editText4);
    }

    public void applyButton(View view){
        Settings.url = url.getText().toString();
        Settings.port = port.getText().toString();

        Settings.ctreateUrl(Settings.url, Settings.port);

        Intent intent = new Intent(PrefActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
