package com.example.luis.tweetapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.Duration;

import twitter4j.Status;

/**
 * Created by Luis on 2018/02/20.
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    private final Context context;
    private List<Status> statuses = new ArrayList<>();

    public TimelineAdapter(Context context) {
        this.context = context;
    }

    void setStatuses(List<Status> statuses){
        this.statuses = statuses;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        TextView screenName;
        TextView text;
        ImageView icon;
        TextView isKey;
        TextView time;

        public ViewHolder(View itemView){
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            isKey = itemView.findViewById(R.id.isKey);
            icon = itemView.findViewById(R.id.icon);
            screenName = itemView.findViewById(R.id.screenName);
            text = itemView.findViewById(R.id.text);
            time = itemView.findViewById(R.id.time);
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
        if (status.getUser().isProtected()) {
            holder.isKey.setText("🔒");
        }
        Picasso.with(context).load(status.getUser().getProfileImageURL()).into(holder.icon);
        holder.screenName.setText(" @" + status.getUser().getScreenName());
        holder.text.setText(status.getText());
        Date now = new Date();
        holder.time.setText(status.getCreatedAt().toString());
        //holder.time.setText(String.valueOf(now.getTime() - status.getCreatedAt().getTime() / 1000) + "秒前のツイート");
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }


}
