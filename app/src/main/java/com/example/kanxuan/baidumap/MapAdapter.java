package com.example.kanxuan.baidumap;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kanxuan.baidumap.Domain.MapDomain;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import co.dift.ui.SwipeToAction;

/**
 * Created by kanxuan on 2017/5/16.
 */

public class MapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<MapDomain> items;

    public class MapViewHolder extends SwipeToAction.ViewHolder<MapDomain> {
        public TextView titleView;
        public TextView describeView;
        public SimpleDraweeView imageView;

        public MapViewHolder(View v) {
            super(v);

            titleView = (TextView)v.findViewById(R.id.title);
            describeView = (TextView)v.findViewById(R.id.desc);
            imageView = (SimpleDraweeView)v.findViewById(R.id.image);
        }

    }

    public MapAdapter(List<MapDomain> items){
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_item_view, parent, false);

        return new MapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MapDomain item = items.get(position);
        MapViewHolder vh = (MapViewHolder) holder;
        vh.titleView.setText(item.getName());
        vh.describeView.setText(item.getDescription());
        vh.data = item;
    }

}
