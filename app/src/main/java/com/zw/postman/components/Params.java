package com.zw.postman.components;

import android.content.Intent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ZW on 2017/2/28.
 */

public class Params {
    private HashMap<String, String> mParams;

    public Params(String key, String value) {init(key,value);}

    public Params(Intent map) {
        init(map);
    }

    private void init(String key, String value) {
        mParams = new HashMap<>();
        mParams.put(key, value);
    }

    private void init(Intent map) {
        ArrayList<String> keys = map.getStringArrayListExtra("names");
        mParams = new HashMap<>();
        String value;
        for (String key :
                keys) {
            value = map.getStringExtra(key);
            mParams.put(key, value);
        }
    }

    public HashMap<String, String> getData() {
        return mParams;
    }

    public boolean hasParams() {
        return !(mParams == null);
    }
}
