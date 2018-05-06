package com.example.lac.cvapp.db.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lac.cvapp.R;
import com.example.lac.cvapp.db.entity.StudyEntity;

import java.util.List;

public class StudyListAdapter extends RecyclerView.Adapter<StudyListAdapter.BeanHolder> {

    private List<StudyEntity> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnStudyListItemClick onStudyListItemClick;

    public StudyListAdapter(List<StudyEntity> list, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.onStudyListItemClick = (OnStudyListItemClick) context;
    }

    @Override
    public BeanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item,parent,false);
        return new BeanHolder(view);
    }

    @Override
    public void onBindViewHolder(BeanHolder holder, int position) {
        Log.e("bind", "onBindViewHolder: "+ list.get(position));
        holder.textViewItem.setText(list.get(position).getSchool());
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
            onStudyListItemClick.onStudyClick(getAdapterPosition());
        }
    }

    public interface OnStudyListItemClick{
        void onStudyClick(int pos);
    }
}
