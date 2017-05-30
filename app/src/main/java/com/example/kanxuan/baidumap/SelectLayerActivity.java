package com.example.kanxuan.baidumap;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kanxuan.baidumap.Domain.HistoryData;
import com.example.kanxuan.baidumap.Domain.SerilzeData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SelectLayerActivity extends ExpandableListActivity {

    private List<HistoryData> historyData;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent lastIntent = getIntent();
        SerilzeData<HistoryData> da = (SerilzeData<HistoryData>)lastIntent.getSerializableExtra("history");
        historyData = da.getData();
        ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
            @Override
            public int getGroupCount() {
                return historyData.size();
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return historyData.get(groupPosition).getData().size();
            }

            @Override
            public Object getGroup(int groupPosition) {
                return historyData.get(groupPosition);
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return historyData.get(groupPosition).getData().get(childPosition);
            }

            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                LinearLayout ll = new LinearLayout(SelectLayerActivity.this);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ImageView logo = new ImageView(SelectLayerActivity.this);
                ll.addView(logo);
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d1=new Date(Long.parseLong(historyData.get(groupPosition).getDate()));
                String t1=format.format(d1);
                TextView textView = getTextView();
                textView.setText(t1);
                ll.addView(textView);
                return ll;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

                TextView tv = getTextView();
                tv.setText(historyData.get(groupPosition).getData().get(childPosition).getType().toString());
                return tv;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }
            private TextView getTextView() {
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
                TextView textView = new TextView(SelectLayerActivity.this);
                textView.setLayoutParams(lp);
                textView.setGravity(Gravity.CENTER_VERTICAL| Gravity.LEFT);
                textView.setPadding(36,0,0,0);
                textView.setTextSize(20);
                return textView;
            }

        };
        setListAdapter(adapter);
        getExpandableListView().setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = getIntent();
                intent.putExtra("gIndex", groupPosition);
                intent.putExtra("cIndex", childPosition);
                SelectLayerActivity.this.setResult(0,intent);
                SelectLayerActivity.this.finish();
                return false;
            }
        });
    }

}
