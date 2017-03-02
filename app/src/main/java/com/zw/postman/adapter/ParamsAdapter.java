package com.zw.postman.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.zw.postman.R;

/**
 * Created by ZW on 2017/3/1.
 */

public class ParamsAdapter extends RecyclerView.Adapter <ParamsAdapter.ViewHolder> {
    Context mContext;

    public ParamsAdapter(Context context){
        this.mContext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View contentView = LayoutInflater.from(mContext).inflate(R.layout.recycler_more_setting,parent,false);
        ViewHolder holder = new ViewHolder(contentView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        View contentView;
        EditText mKey;
        EditText mValue;
        public ViewHolder(View view){
            super(view);
            this.contentView = view;
            this.mKey = (EditText) contentView.findViewById(R.id.advanced_more_key);
            this.mValue = (EditText) contentView.findViewById(R.id.advanced_more_value);
        }
    }
}
