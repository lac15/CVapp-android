package com.example.lac.cvapp.db.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lac.cvapp.R;
import com.example.lac.cvapp.db.entity.DrivingLicenseEntity;

import java.util.List;

public class DrivingLicenseListAdapter extends RecyclerView.Adapter<DrivingLicenseListAdapter.BeanHolder> {

    private List<DrivingLicenseEntity> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnDrivingLicenseListItemClick onDrivingLicenseListItemClick;

    public DrivingLicenseListAdapter(List<DrivingLicenseEntity> list, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.onDrivingLicenseListItemClick = (OnDrivingLicenseListItemClick) context;
    }

    @Override
    public BeanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item,parent,false);
        return new BeanHolder(view);
    }

    @Override
    public void onBindViewHolder(BeanHolder holder, int position) {
        Log.e("bind", "onBindViewHolder: "+ list.get(position));
        holder.textViewItem.setText(list.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewItem;

        public BeanHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewItem = itemView.findViewById(R.id.item);
        }

        @Override
        public void onClick(View view) {
            onDrivingLicenseListItemClick.onDrivingLicenseClick(getAdapterPosition());
        }
    }

    public interface OnDrivingLicenseListItemClick{
        void onDrivingLicenseClick(int pos);
    }
}
