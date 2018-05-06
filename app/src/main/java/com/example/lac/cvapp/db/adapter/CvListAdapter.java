package com.example.lac.cvapp.db.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lac.cvapp.R;
import com.example.lac.cvapp.db.entity.CvEntity;

import java.util.List;

public class CvListAdapter extends RecyclerView.Adapter<CvListAdapter.BeanHolder> {

    private List<CvEntity> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnCvListItemClick onCvListItemClick;

    public CvListAdapter(List<CvEntity> list, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.onCvListItemClick = (OnCvListItemClick) context;
    }

    @Override
    public BeanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item,parent,false);
        return new BeanHolder(view);
    }

    @Override
    public void onBindViewHolder(BeanHolder holder, int position) {
        Log.e("bind", "onBindViewHolder: "+ list.get(position));
        holder.textViewItem.setText(list.get(position).getLastName() + " " + list.get(position).getFirstName());
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
            onCvListItemClick.onCvClick(getAdapterPosition());
        }
    }

    public interface OnCvListItemClick{
        void onCvClick(int pos);
    }

    public List<CvEntity> getList() {
        return list;
    }

    public void setList(List<CvEntity> list) {
        this.list = list;
    }
}
