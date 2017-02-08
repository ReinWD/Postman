package com.zw.postman.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.zw.postman.MainActivity;
import com.zw.postman.R;

import java.util.ArrayList;

/**
 * Created by 张巍 on 2017/2/3.
 */

public class RecyclerAdapter extends RecyclerView.Adapter <RecyclerAdapter.EditTextHolder>{

    public void setData(ArrayList<String> mData) {
        this.mData = mData;
    }

    private ArrayList<String> mData;
    private MainActivity mContext;
    public RecyclerAdapter(MainActivity context, ArrayList<String> data){
        this.mContext = context;
        this.mData = data;
    }
    @Override
    public EditTextHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EditTextHolder(LayoutInflater.from(mContext).
                inflate(R.layout.main_recycler_view,parent,false));
    }

    @Override
    public void onBindViewHolder(EditTextHolder holder, int position) {
        holder.setEditText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        if(!mData.isEmpty())return mData.size();else return 0;
    }

    class EditTextHolder extends RecyclerView.ViewHolder {
        EditText editText;

        EditTextHolder(View itemView) {
            super(itemView);
            editText = (EditText) itemView.findViewById(R.id.recyclerview_item);
        }

        void setEditText(String data){
            editText.setText(data);
        }
    }
}
