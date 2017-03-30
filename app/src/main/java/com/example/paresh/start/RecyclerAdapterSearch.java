package com.example.paresh.start;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by root on 23/3/17.
 */

public class RecyclerAdapterSearch extends RecyclerView.Adapter<RecyclerAdapterSearch.ViewHolder> {

    private Activity activity;
    private ArrayList<UserDetails> userDetails;


    public RecyclerAdapterSearch(Activity activity,ArrayList<UserDetails> userDetails){
        this.activity =activity;
        this.userDetails = userDetails;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.list_item,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(userDetails.get(position).getName());
        holder.phone_number.setText(userDetails.get(position).getPhone_number());
        holder.email.setText(userDetails.get(position).getEmail());
        holder.room_no.setText(userDetails.get(position).getRoom_no());
    }

    public void setFilter(ArrayList<UserDetails> details){
        userDetails = new ArrayList<>();
        userDetails.addAll(details);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (userDetails!=null ? userDetails.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,phone_number,email,room_no;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            phone_number = (TextView)itemView.findViewById(R.id.phone_number);
            email = (TextView)itemView.findViewById(R.id.email);
            room_no = (TextView)itemView.findViewById(R.id.room_no);
        }
    }
}
