package com.zw.postman.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zw.postman.R;
import com.zw.postman.adapter.HistoryAdapter;
import com.zw.postman.adapter.RecyclerAdapter;
import com.zw.postman.components.History;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    public static final int ITEM_SELECTED=3;
    RecyclerView mList;
    HistoryAdapter mAdapter;
    ArrayList<History> mHistoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        init();
        /**
         * RecyclerView
         */
        mList = (RecyclerView) findViewById(R.id.history_recycler);
        mAdapter = new HistoryAdapter(this,mHistoryList);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setAdapter(mAdapter);
    }
    public void destroy(Intent intent){
        setResult(ITEM_SELECTED,intent);
        finish();
    }
    void init(){
        mHistoryList = MainActivity.mHistory;
    }
}
