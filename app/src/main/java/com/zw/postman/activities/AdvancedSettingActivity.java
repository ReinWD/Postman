package com.zw.postman.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.zw.postman.R;
import com.zw.postman.adapter.ParamsAdapter;
import com.zw.postman.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdvancedSettingActivity extends Activity {
    ArrayList mData;
    RecyclerView mMenu;
    ParamsAdapter mRecyclerAdapter;
    Button mFinish;
    HashMap mResult;


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
        mMenu.setLayoutManager( new LinearLayoutManager(this));
        mMenu.setAdapter(mRecyclerAdapter);
        /**
         * Button
         */
        mFinish = (Button) findViewById(R.id.advanced_params_finish);
        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mResult = mRecyclerAdapter.getData();
        for (String entry:
             new ArrayList<String>(mResult.entrySet())) {

        }
    }

    void init(){
        mData = new ArrayList();
    }
}
