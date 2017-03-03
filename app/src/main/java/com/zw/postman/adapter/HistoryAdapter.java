package com.zw.postman.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zw.postman.R;
import com.zw.postman.activities.HistoryActivity;
import com.zw.postman.activities.MainActivity;
import com.zw.postman.components.History;

import java.util.ArrayList;

/**
 * Created by ZW on 2017/3/3.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {
    HistoryAdapter mThis = this;
    HistoryActivity mContext;
    ArrayList<History> mHistory = MainActivity.mHistory;

    public HistoryAdapter(HistoryActivity context, ArrayList<History> userHistory) {
        this.mHistory = userHistory;
        this.mContext = context;
    }

    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HistoryHolder holder = new HistoryHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.recycler_history, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final HistoryHolder holder, final int position) {
         holder.init(position);
    }

    @Override
    public int getItemCount() {
        return mHistory.size();
    }

    class HistoryHolder extends RecyclerView.ViewHolder {
        TextView lable;
        TextView protocol;
        TextView url;
        Button delete;
        Button yes;

        HistoryHolder(View view) {
            super(view);
            this.lable = (TextView) view.findViewById(R.id.recycler_index);
            this.protocol = (TextView) view.findViewById(R.id.history_protocol);
            this.url = (TextView) view.findViewById(R.id.history_url);
            this.delete = (Button) view.findViewById(R.id.history_delete);
            this.yes = (Button) view.findViewById(R.id.history_yes);
        }
        public void init(final int position){
            lable.setText(String.valueOf(position+1));
            protocol.setText(mHistory.get(position).getProtocol());
            url.setText(mHistory.get(position).getURL());
            delete.setOnClickListener
                    (new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(mHistory.size()>1){
                            mHistory.remove(position);}
                            mThis.notifyItemRemoved(position);
                        }
                    });
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent returns = new Intent();
                    returns.putExtra("protocol",protocol.getText().toString()).
                            putExtra("url",url.getText().toString());
                    mContext.destroy(returns);
                }
            });
        }
    }
}
