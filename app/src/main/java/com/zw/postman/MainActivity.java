package com.zw.postman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.net.URL;

import HTTP.Identifier;

public class MainActivity extends AppCompatActivity {
    URL mRequestURL;
    Spinner mModeSelect;
    EditText mURL;
    Button mSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mModeSelect = (Spinner) findViewById(R.id.main_mode_select);
        mURL = (EditText) findViewById(R.id.main_et_url_input);
        mSend = (Button) findViewById(R.id.main_button_send);


        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mRequestURL = new URL(mURL.getText().toString());
                    Identifier identifier = new Identifier(mRequestURL);
                    mModeSelect.getTag();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
