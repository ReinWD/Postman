package com.zw.postman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.zw.postman.adapter.RecyclerAdapter;

import java.util.ArrayList;

public class AdvancedSettingActivity extends AppCompatActivity {
    ArrayList mData;
    RecyclerView mMenu;
    RecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_setting);
        init();
        //初始化控件---------------------------------------------------------------------------------
        /**
         * RecyclerView
         */
        mMenu = (RecyclerView) findViewById(R.id.advanced_recyclerview);
        mRecyclerAdapter = new RecyclerAdapter(this,)
    }
    void init(){
        mData = new ArrayList();

    }
}
