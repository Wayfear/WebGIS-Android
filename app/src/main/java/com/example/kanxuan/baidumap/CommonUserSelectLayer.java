package com.example.kanxuan.baidumap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.kanxuan.baidumap.Domain.BaseDomain;
import com.example.kanxuan.baidumap.Domain.MongoLayer;
import com.example.kanxuan.baidumap.Domain.PointDomain;
import com.example.kanxuan.baidumap.Domain.SerilzeData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommonUserSelectLayer extends ListActivity {

    List<MongoLayer> allData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_user_select_layer);
        Intent lastIntent = getIntent();
        SerilzeData<MongoLayer> da = (SerilzeData<MongoLayer>)lastIntent.getSerializableExtra("allData");
        allData = da.getData();
        List<String> titles = new ArrayList<>();
        for (int i=0;i<allData.size();i++) {
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1=new Date(allData.get(i).getCreateTime());
            String t1=format.format(d1);
            titles.add(t1);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this, R.layout.content_items,R.id.text, titles );
        setListAdapter(adapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = getIntent();
        intent.putExtra("index", position);
        CommonUserSelectLayer.this.setResult(0,intent);
        CommonUserSelectLayer.this.finish();
    }
}
