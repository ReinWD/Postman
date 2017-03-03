package com.zw.postman.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zw.postman.R;

/**
 * Adapter for RecyclerView used in "More params" Activity
 * Created by ZW on 2017/3/1.
 */

public class ParamsAdapter extends RecyclerView.Adapter<ParamsAdapter.ViewHolder> {
    private ParamsAdapter mAdapter = this;
    private Context mContext;
    private int mViewCount = 1;

    public ParamsAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView;
        contentView = LayoutInflater.from(mContext).inflate(R.layout.recycler_more_setting, parent, false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        setListeners(holder, position);
    }

    @Override
    public int getItemCount() {
        return mViewCount;
    }


    private void insertItem() {
        mViewCount++;
        mAdapter.notifyItemInserted(mViewCount - 1);
    }

    private void setListeners(final ViewHolder holder, final int position) {

        InsertItem insertItem = new InsertItem(holder);
        OnEnterDownListener enterDownListener = new OnEnterDownListener();

        holder.mKey.setOnTouchListener(insertItem);
        holder.mValue.setOnTouchListener(insertItem);
        holder.mKey.setOnEditorActionListener(enterDownListener);
        holder.mValue.setOnEditorActionListener(enterDownListener);
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewCount > 1) {
                    mViewCount--;
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View contentView;
        EditText mKey;
        EditText mValue;
        Button mDelete;

        ViewHolder(View view) {
            super(view);
            init(view);
        }

        private void init(View view) {
            this.contentView = view;
            this.mKey = (EditText) contentView.findViewById(R.id.advanced_more_key);
            this.mValue = (EditText) contentView.findViewById(R.id.advanced_more_value);
            this.mDelete = (Button) contentView.findViewById(R.id.advanced_delete_button);
        }
    }

    private class InsertItem implements View.OnTouchListener {
        ViewHolder holder;

        InsertItem(ViewHolder vh) {
            this.holder = vh;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();

            switch (action) {
                case MotionEvent.ACTION_UP:
                    InputMethodManager inputMethod = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethod.showSoftInput(v, 0);
                    v.requestFocus();
                    if (v.focusSearch(View.FOCUS_DOWN) == null) {
                        insertItem();
                    }

            }
            return false;
        }
    }

    private class OnEnterDownListener implements TextView.OnEditorActionListener, View.OnKeyListener {
        View foundedView, result;

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            foundedView = v.focusSearch(View.FOCUS_RIGHT);
            if (foundedView == null) {
                foundedView = v;
                while (!((foundedView = foundedView.focusSearch(View.FOCUS_LEFT)) == null)) {
                    result = foundedView;
                }
                result = result.focusSearch(View.FOCUS_DOWN);
            } else result = foundedView;
            result.requestFocus();
            result.dispatchTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP, 0, 0, 0));
            return false;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            return false;
        }
    }

}