package com.zw.postman.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.zw.postman.R;

import java.util.List;

import static com.zw.postman.R.layout.main_spinner_dropdown;

/**
 * Created by 张巍 on 2017/1/21.
 */

public class MyAdapter implements SpinnerAdapter{

    private List mData;
    private Context mContext;

    public MyAdapter(Context context, List data){
        this.mData=data;
        this.mContext = context;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(main_spinner_dropdown,null);
        TextView textView = (TextView) convertView.findViewById(R.id.main_dropdown_text_view);
        textView.setText(getItem(position).toString());
        return convertView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(main_spinner_dropdown,null);
        TextView textView = (TextView) convertView.findViewById(R.id.main_dropdown_text_view);
        textView.setText(getItem(position).toString());
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return mData.isEmpty();
    }

}
