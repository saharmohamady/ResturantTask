package com.quand.resturanttask.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.quand.resturanttask.R;
import com.quand.resturanttask.model.Table;

import java.util.ArrayList;

/**
 * Created by salmohamady on 2/23/2016.
 */
public class TablesAdapter extends RecyclerView.Adapter<TablesAdapter.ViewHolder> {

    private ArrayList<Table> tablesData = new ArrayList<Table>();

    private int selectedIndex = -1;

    public TablesAdapter(ArrayList<Table> data) {
        this.tablesData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_item_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Boolean reserved = tablesData.get(position).isReserved();
        if (reserved) {
            holder.reserved.setVisibility(View.VISIBLE);
        } else
            holder.reserved.setVisibility(View.GONE);
        //highlight reserved table
        if (position == selectedIndex) {
            holder.tablebg.setBackgroundResource(R.drawable.reserved_bg_round);
        } else {
            holder.tablebg.setBackgroundResource(R.drawable.unreserved_bg_round);

        }
    }

    @Override
    public int getItemCount() {
        return tablesData.size();
    }

    public Table getItemAtPostion(int pos) {
        if (tablesData != null)
            return tablesData.get(pos);
        return null;
    }

    public void setReserved(int position) {
        //clear previous selection if exist
        if (selectedIndex != -1) {
            tablesData.get(selectedIndex).setReserved(false);
        }
        //select table
        tablesData.get(position).setReserved(true);
        selectedIndex = position;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView reserved;
        private final ImageView tablebg;
        // each data item is just a imageview in this case
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            reserved = (ImageView) v.findViewById(R.id.reserved);
            tablebg = (ImageView) v.findViewById(R.id.table);

        }
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

}
