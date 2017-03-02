package com.zw.postman.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
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

import com.zw.postman.R;
import com.zw.postman.adapter.MyAdapter;
import com.zw.postman.adapter.RecyclerAdapter;
import com.zw.postman.http.Browser;
import com.zw.postman.http.HttpRequest;

import static android.view.KeyEvent.KEYCODE_ENTER;

public class MainActivity extends AppCompatActivity implements Runnable {
    /**
     * MainActivity
     * 本app的主活动
     */
    Context mMain = this;
    URL mRequestURL;
    Spinner mModeSelect;
    TextView mModeDisplay;
    TextView mProtocolDisplay;
    EditText mURL;
    RecyclerView mContains;
    RecyclerAdapter mRecyclerAdapter;
    Button mSend;
    Button mPreview;
    Button mMoreSetting;
    ToggleButton mEditable;
    ArrayList<String> mModes = new ArrayList<>();
    ArrayList<String> mData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        Thread mMainActivityThread = new Thread(this);
        mMainActivityThread.start();
    }
    /**
    * 为必要的下拉框插入数据
    */
    private void initData() {
        mModes.add("GET");
        mModes.add("POST");
        mModes.add("HEAD");
        mModes.add("PUT");
        mModes.add("DELETE");
        mModes.add("COPY");
        mModes.add("PATCH");
    }
    /**
     * 为了不在主线使用网络请求而独立出来的Thread
     */
    public class HttpThread extends Thread {
        @Override
        public void run() {
            try {
                mRequestURL = new URL(mURL.getText().toString());
            } catch (Exception e1) {
                try {
                    mRequestURL = new URL(mProtocolDisplay.getText().toString().toLowerCase() + mURL.getText());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            try {
                HttpRequest httpRequest = new HttpRequest(mRequestURL, mModeSelect.getSelectedItem().toString(), mProtocolDisplay.getText().toString().toLowerCase());
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
                    mRecyclerAdapter.notifyDataSetChanged();
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
        mMoreSetting = (Button) findViewById(R.id.params_button_more);
        mEditable = (ToggleButton) findViewById(R.id.main_toggle_button_editable);
        mEditable.setChecked(false);
        /**
         * RecyclerView
         */
        mContains = (RecyclerView) findViewById(R.id.main_recyclerView_contents_main);
        mRecyclerAdapter = new RecyclerAdapter(this, mData);
        mContains.setAdapter(mRecyclerAdapter);
        mContains.setLayoutManager(new LinearLayoutManager(this));
        /**
         * Adapter
         */
        MyAdapter adapter1 = new MyAdapter(mMain, mModes);
        mModeSelect.setAdapter(adapter1);

        /**
         * 设置侦听器
         */

        mURL.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            }
        });
        //---------------------\---------------------------------------------------------------------
        mURL.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KEYCODE_ENTER) {
                    mSend.performClick();
                }
                return false;
            }
        });
        //------------------------------------------------------------------------------------------
        mModeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String target = mModeSelect.getItemAtPosition(position).toString();
                mModeDisplay.setText(target);
            }
            //选中的条目会实时显示在spinner旁边的TextView里
            //选中ADVANCED即可开启高级模式
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
            //不选就没事
        });
        //------------------------------------------------------------------------------------------
        mModeDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(mURL.getWindowToken(), 0);
                mModeSelect.performClick();
            }
        });
        mProtocolDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mProtocolDisplay.getText().equals("https://")) {
                    mProtocolDisplay.setText("http://");
                } else {
                    mProtocolDisplay.setText("https://");
                }
            }
        });
        //把TextView与spinner联系在一起
        //------------------------------------------------------------------------------------------
        mSend.setOnClickListener(new View.OnClickListener() {

            Thread httpRequestThread = new HttpThread();

            @Override
            public void onClick(View v) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(mURL.getWindowToken(), 0);
                mData = new ArrayList<>();
                mData.add("Processing");
                mRecyclerAdapter.setData(mData);
                mRecyclerAdapter.notifyDataSetChanged();
                if (!httpRequestThread.isAlive()) {
                    try {
                        httpRequestThread.start();
                    } catch (Exception e) {
                        mData = new ArrayList<String>();
                        mData.add("Request Interrupted");
                        mRecyclerAdapter.notifyDataSetChanged();
                        e.printStackTrace();
                    }
                } else {
                    httpRequestThread.interrupt();
                }
            }
        });
        //------------------------------------------------------------------------------------------
        mEditable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mRecyclerAdapter.setEditable(isChecked);
            }
        });
        //------------------------------------------------------------------------------------------
        mPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isURLNull()){
                String target;
//                File html=null;
//                String wow = mData.toString();
//                boolean finish;
//                finish = false;
//                for (int i = 0;!finish;i++) {
//                    html = new File(getFilesDir().getPath()+"/"+"webPageCache"+i+".html");
//                    if (!html.exists()&&!wow.isEmpty()){
//                            try {
//                                if(finish = html.createNewFile()) {
//                                    FileWriter HarukiMurakami = new FileWriter(html);
//                                    HarukiMurakami.write(wow);
//                                    HarukiMurakami.close();
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
                target = mProtocolDisplay.getText().toString() + mURL.getText().toString();
                Intent toWebBrowser = new Intent(MainActivity.this, Browser.class);
                toWebBrowser.putExtra("url", target);
                startActivity(toWebBrowser);}
                else {
                    Toast.makeText(getApplicationContext(),"Please check your URL",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //------------------------------------------------------------------------------------------
        mMoreSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AdvancedSettingActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean isURLNull(){
        return mURL.getText().toString().isEmpty();
    }
    private void makeToast(String message){
    }
}
