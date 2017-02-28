package com.zw.postman.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.zw.postman.R;

import java.util.ArrayList;

/**
 * Created by 张巍 on 2017/2/3.
 */

public class RecyclerAdapter extends RecyclerView.Adapter <RecyclerAdapter.EditTextHolder>{

    public void setData(ArrayList<String> mData) {
        this.mData = mData;
    }

    private EditTextHolder editTextHolder;
    private ArrayList<String> mData;
    private Context mContext;
    private boolean mEditable;
    private ArrayList<ArrayList> mArray;
    public RecyclerAdapter(Context context, ArrayList<String> data){
        this.mContext = context;
        this.mData = data;
        this.mEditable = false;
    }
    public RecyclerAdapter(Context context, ArrayList<ArrayList> data, boolean editable
    ){
        this.mContext = context;
        this.mArray = data;
        this.mEditable = editable;
    }
    @Override
    public EditTextHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            editTextHolder = new EditTextHolder(LayoutInflater.from(mContext).
                inflate(R.layout.main_recycler_view_editable, parent, false));
        editTextHolder.setEditable();
        return editTextHolder;
    }

    @Override
    public void onBindViewHolder(EditTextHolder holder, int position) {
        holder.setEditText(mData.get(position),position);
        holder.setEditable();
    }

    @Override
    public int getItemCount() {
        if(!mData.isEmpty())return mData.size();else return 0;
    }

    public void setEditable(boolean editable){
        this.mEditable = editable;
        if (editTextHolder != null) {
            editTextHolder.setEditable();
        }
        notifyDataSetChanged();
    }

    class EditTextHolder extends RecyclerView.ViewHolder {
        EditText editText;

        EditTextHolder(View itemView) {
            super(itemView);
                editText = (EditText) itemView.findViewById(R.id.recyclerview_item_editable);

        }

        void setEditText(String data, final int position){
                editText.setText(data);
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    mData.set(position,editText.getText().toString());
                }
            });

        }

        void setEditable(){
            this.editText.setFocusable(mEditable);
            this.editText.setFocusableInTouchMode(mEditable);
        }
    }
}
