package com.example.luis.tweetapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;

/**
 * Created by Luis on 2018/02/20.
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    private List<Status> statuses = new ArrayList<>();

    void setStatuses(List<Status> statuses){
        this.statuses = statuses;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        TextView text;

        public ViewHolder(View itemView){
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            text = itemView.findViewById(R.id.text);
        }
    }

    @Override
    public TimelineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_status,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TimelineAdapter.ViewHolder holder, int position) {
        Status status = statuses.get(position);
        holder.userName.setText(status.getUser().getName());
        holder.text.setText(status.getText());
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }


}
