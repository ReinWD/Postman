package com.zw.postman;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.zw.postman.adapter.MyAdapter;
import com.zw.postman.adapter.RecyclerAdapter;
import com.zw.postman.http.HttpRequest;

import static android.view.KeyEvent.KEYCODE_ENTER;

public class MainActivity extends Activity implements Runnable {
    Boolean mReadable;
    Context mMain = this;
    URL mRequestURL;
    Spinner mModeSelect;
    Spinner mProtocolSelect;
    TextView mModeDisplay;
    TextView mProtocolDisplay;
    EditText mURL;
    RecyclerView mContains;
    RecyclerAdapter mRecyclerAdapter;
    Button mSend;
    Button mPreview;
    ToggleButton mEditable;
    ArrayList<String> mModes = new ArrayList<>();
    ArrayList<String> mProtocol = new ArrayList<>();
    ArrayList<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        Thread mMainActivityThread = new Thread(this);
        mMainActivityThread.start();
    }


    private void initData() {
        mModes.add("GET");
        mModes.add("POST");
        mModes.add("HEAD");
        mModes.add("PUT");
        mModes.add("DELETE");
        mModes.add("COPY");
        mModes.add("PATCH");
        mProtocol.add("http://");
        mProtocol.add("https://");
        mData.add("cao");
    }

    public class HttpThread extends Thread {

        @Override
        public void run() {
            try {
                mRequestURL = new URL(mURL.getText().toString());
            } catch (Exception e1) {
                try {
                    mRequestURL = new URL(mProtocolSelect.getSelectedItem().toString().toLowerCase() + mURL.getText());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            try {
                HttpRequest httpRequest = new HttpRequest(mRequestURL, mModeSelect.getSelectedItem().toString(), mProtocolSelect.getSelectedItem().toString());
                switch (httpRequest.getProtocol()) {
                    case 1:
                        System.out.println("开始进行第一次HTTP连接尝试");
                        mData = httpRequest.Request();
                        break;
                    case 2:
                        System.out.println("开始进行第一次HTTPS连接尝试");
                        mData = httpRequest.Request();
                }
            } catch (IOException e) {
                e.printStackTrace();
                        mData = new ArrayList<>();
                mData.add("A problem occurred\\nCheck your URL\\nor your network connection");
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mRecyclerAdapter.setData(mData);
                    mContains.setAdapter(mRecyclerAdapter);
                }
            });
        }

    }

    @Override
    public void run() {
        /**
         * 实例化布局
         * TextView
         */

        mModeDisplay = (TextView) findViewById(R.id.main_text_mode);
        mProtocolDisplay = (TextView) findViewById(R.id.main_text_protocol);
        /**
         * Spinner
         */
        mModeSelect = (Spinner) findViewById(R.id.main_mode_select);
        mProtocolSelect = (Spinner) findViewById(R.id.main_spinner_protocol);
        /**
         * EditText
         */
        mURL = (EditText) findViewById(R.id.main_et_url_input);
        /**
         * Button
         * ToggleButton
         */
        mSend = (Button) findViewById(R.id.main_button_send);
        mPreview = (Button) findViewById(R.id.main_button_preview);
        mEditable = (ToggleButton) findViewById(R.id.main_toggle_button_editable);
        mEditable.setChecked(false);
        /**
         * RecyclerView
         */
        mContains = (RecyclerView) findViewById(R.id.main_recyclerView_contents_main);
        mRecyclerAdapter = new RecyclerAdapter(this,mData);
        mContains.setAdapter(mRecyclerAdapter);
        mContains.setLayoutManager(new LinearLayoutManager(this));
        /**
         * Adapter
         */
        MyAdapter adapter1 = new MyAdapter(mMain, mModes);
        MyAdapter adapter2 = new MyAdapter(mMain, mProtocol);
        mModeSelect.setAdapter(adapter1);
        mProtocolSelect.setAdapter(adapter2);

        /**
         * 设置侦听器
         */
        mURL.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KEYCODE_ENTER) {
                    mSend.performClick();
                    InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(mURL.getWindowToken(), 0);
                }
                return false;
            }
        });
        //------------------------------------------------------------------------------------------
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
        //------------------------------------------------------------------------------------------
        mProtocolSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mProtocolDisplay.setText(mProtocol.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //------------------------------------------------------------------------------------------
        mModeDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mModeSelect.performClick();
            }
        });
        mProtocolDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProtocolSelect.performClick();
            }
        });
        //把TextView与spinner联系在一起
        //------------------------------------------------------------------------------------------
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData = new ArrayList<String>();
                mData.add("Processing");
                mRecyclerAdapter.setData(mData);
                mContains.setAdapter(mRecyclerAdapter);
                //读取模式与URL
                new HttpThread().start();
            }
        });
        //------------------------------------------------------------------------------------------
        mEditable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mContains.setFocusable(true);
                    mContains.setFocusableInTouchMode(true);
                } else {
                    mContains.setFocusable(false);
                    mContains.setFocusableInTouchMode(false);
                }
            }
        });
        //------------------------------------------------------------------------------------------
        mPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = new Toast(getApplicationContext());
                toast.setView(v);
                toast.makeText(getApplicationContext(), "sorry...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean getReadable(){
        mReadable = (mEditable.isChecked());
        return mReadable;
    }

}
