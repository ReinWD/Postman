package com.zw.postman.activities;

import android.app.Activity;
import android.content.Intent;
import android.opengl.ETC1;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zw.postman.R;
import com.zw.postman.adapter.ParamsAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class AdvancedSettingActivity extends Activity {
    public static final int PARAMS_EXIST = 1;
    public static final int PARAMS_NOT_EXIST = 0;


    ArrayList mData;
    RecyclerView mMenu;
    ParamsAdapter mRecyclerAdapter;
    Button mFinish;
    HashMap<String, String> mResult = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_setting);
        init();
        //初始化控件---------------------------------------------------------------------------------
        /**
         * RecyclerView
         */
        mMenu = (RecyclerView) findViewById(R.id.advanced_recycler_itemlist);
        mRecyclerAdapter = new ParamsAdapter(this);
        mMenu.setLayoutManager(new LinearLayoutManager(this));
        mMenu.setAdapter(mRecyclerAdapter);
        /**
         * Button
         */
        mFinish = (Button) findViewById(R.id.advanced_params_finish);
        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateResult();
                finish();
            }
        });
    }

    void generateResult() {
        RecyclerView.LayoutManager mManager = mMenu.getLayoutManager();
        int totalViewCount = mManager.getItemCount();
        for (int i = 0; i < totalViewCount; i++) {
            try {
                String mKey =
                        ((EditText) mMenu.getChildAt(i).findViewById(R.id.advanced_more_key)).getText().toString();
                String mValue =
                        ((EditText) mMenu.getChildAt(i).findViewById(R.id.advanced_more_value)).getText().toString();
                if (!mKey.isEmpty()) {
                    mResult.put(mKey, mValue);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mResult == null) {
            setResult(PARAMS_NOT_EXIST);
        } else {
            ArrayList<HashMap.Entry<String, String>> names = new ArrayList<>(mResult.entrySet());
            ArrayList<String> keys= new ArrayList<>();
            Intent intent = new Intent();
            for (HashMap.Entry<String,String> fuck :
                    names) {
                keys.add(fuck.getKey());
                intent.putExtra(fuck.getKey(),fuck.getValue());
            }
            intent.putExtra("names",keys);
            setResult(PARAMS_EXIST, intent);
        }
    }

    void init() {
        mData = new ArrayList();
    }
}
