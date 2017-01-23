package com.zw.postman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.zw.postman.Adapter.MyAdapter;
import com.zw.postman.HTTP.HttpRequest;

public class MainActivity extends AppCompatActivity {
    URL mRequestURL;
    Spinner mModeSelect;
    TextView mModeDisplay;
    TextView mContains;
    EditText mURL;
    Button mSend;
    ArrayList<String> modes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        mContains = (TextView) findViewById(R.id.main_textview_main);
        mModeSelect = (Spinner) findViewById(R.id.main_mode_select);
        mModeDisplay = (TextView) findViewById(R.id.main_text_mode);
        mURL = (EditText) findViewById(R.id.main_et_url_input);
        mSend = (Button) findViewById(R.id.main_button_send);
        MyAdapter adapter = new MyAdapter(this, modes);
        mModeSelect.setAdapter(adapter);

        //设置侦听器
        mModeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mModeDisplay.setText(mModeSelect.getItemAtPosition(position).toString());
            }
            //选中的条目会实时显示在spinner旁边的TextView里

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
            //不选就没事
        });
        //把TextView与spinner联系在一起
        mModeDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mModeSelect.performClick();
            }
        });
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //读取模式与URL
                try {
                    mRequestURL = new URL(mURL.getText().toString());
                    HttpRequest httpRequest = new HttpRequest(mRequestURL);
                    switch (httpRequest.getProtocol()) {
                        case 1:
                            System.out.println("开始进行第一次HTTP连接尝试");
                            mContains.setText(httpRequest.Request());
                            break;
                        case 2:
                            System.out.println("开始进行第一次HTTPS连接尝试");

                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

    }

    private void initData() {
        modes.add("GET");
        modes.add("POST");
        modes.add("HEAD");
        modes.add("PUT");
        modes.add("DELETE");
        modes.add("COPY");
        modes.add("PATCH");
    }

    public void importData(){}



}
