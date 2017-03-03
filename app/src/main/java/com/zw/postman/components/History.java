package com.zw.postman.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZW on 2017/3/3.
 */

public class History {
    private String mProtocol,mURL;
    ArrayList<HashMap.Entry<String ,String >> mParam;

    public History(String protocol,String URL){
        this.mProtocol = protocol;
        this.mURL = URL;
    }
    public History(String protocol,String URL,ArrayList<HashMap.Entry<String ,String>> params){
        this.mProtocol = protocol;
        this.mURL = URL;
        this.mParam = params;
    }
    public String getProtocol(){
        return this.mProtocol;
    }
    public String getURL(){
        return this.mURL;
    }
    public boolean hasParams(){
        return mParam != null;
    }


}
