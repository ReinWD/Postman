package com.zw.postman.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.zw.postman.R;
import com.zw.postman.adapter.ParamsAdapter;
import com.zw.postman.adapter.RecyclerAdapter;

import java.util.ArrayList;

public class AdvancedSettingActivity extends Activity {
    ArrayList mData;
    RecyclerView mMenu;
    ParamsAdapter mRecyclerAdapter;

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

    }
    void init(){
        mData = new ArrayList();
    }
}
