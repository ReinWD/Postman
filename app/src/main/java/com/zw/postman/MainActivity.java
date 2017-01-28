package com.zw.postman;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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

import com.zw.postman.Adapter.MyAdapter;
import com.zw.postman.HTTP.HttpRequest;

import static android.view.KeyEvent.KEYCODE_ENTER;

public class MainActivity extends Activity implements Runnable {
    Context mMain = this;
    URL mRequestURL;
    Spinner mModeSelect;
    Spinner mProtocolSelect;
    TextView mModeDisplay;
    TextView mProtocolDisplay;
    EditText mContains;
    EditText mURL;
    ToggleButton mEditable;
    Button mSend;
    Button mPreview;
    ArrayList<String> mModes = new ArrayList<>();
    ArrayList<String> mProtocol = new ArrayList<>();
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
                        final String result = httpRequest.Request();
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                mContains.setText(result);
                            }
                        });
                        break;
                    case 2:
                        System.out.println("开始进行第一次HTTPS连接尝试");
                        final String resultS = httpRequest.Request();
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                mContains.setText(resultS);
                            }
                        });
                }
            } catch (IOException e) {
                e.printStackTrace();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mContains.setText("A problem occurred\nCheck your URL\nor your network connection");
                    }
                });
            }
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
                        if(keyCode == KEYCODE_ENTER){
                            mSend.performClick();
                            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            manager.hideSoftInputFromWindow(mURL.getWindowToken(),0);
                        }
                        return false;}
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
                    public void onClick(View v) {mModeSelect.performClick();}
                });
                mProtocolDisplay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {mProtocolSelect.performClick();}
                });
                //把TextView与spinner联系在一起
                //------------------------------------------------------------------------------------------
                mSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContains.setText("Progressing...");
                        //读取模式与URL
                        new HttpThread().start();}
                });
                //------------------------------------------------------------------------------------------
                mEditable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            mContains.setFocusable(true);
                            mContains.setFocusableInTouchMode(true);
                        }else {
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
                        toast.setText("sorry...");
                        toast.show();
                    }
                });
                //------------------------------------------------------------------------------------------

                mContains = (EditText) findViewById(R.id.main_edittext_contents_main);
            mContains.setTextIsSelectable(true);
            mContains.setFocusableInTouchMode(false);
            mContains.setFocusable(false);

    }

}
