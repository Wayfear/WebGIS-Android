package com.example.kanxuan.baidumap;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.kanxuan.baidumap.Domain.BaseDomain;
import com.example.kanxuan.baidumap.Domain.MapDomain;
import com.example.kanxuan.baidumap.Domain.SerilzeData;
import com.example.kanxuan.baidumap.Domain.WebMapContent;
import com.example.kanxuan.baidumap.HttpLoader.MapLoader;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import co.dift.ui.SwipeToAction;
import retrofit2.Converter;
import rx.functions.Action1;

public class SelectMapActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MapAdapter adapter;
    SwipeToAction swipeToAction;
    List<MapDomain> maps = new ArrayList<>();
    final private String TAG = "Selected Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_select_map);
        Intent lastIntent = getIntent();
        SerilzeData<MapDomain> da = (SerilzeData<MapDomain>)lastIntent.getSerializableExtra("maps");
        maps = da.getData();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new MapAdapter(this.maps);

        recyclerView.setAdapter(adapter);

        swipeToAction = new SwipeToAction(recyclerView, new SwipeToAction.SwipeListener<MapDomain>() {
            @Override
            public boolean swipeLeft(final MapDomain itemData) {
                final int pos = removeMap(itemData);
                displaySnackbar(itemData.getName() + " removed", "Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addMap(pos, itemData);
                    }
                });
                return true;
            }

            @Override
            public boolean swipeRight(MapDomain itemData) {
                displaySnackbar(itemData.getName() + " loved", null, null);
                return true;
            }

            @Override
            public void onClick(MapDomain itemData) {
                displaySnackbar(itemData.getName() + " clicked", null, null);

                MapLoader mapLoader = new MapLoader();
                mapLoader.getLayersByMapId(74).subscribe(new Action1<List<BaseDomain>>() {
                    @Override
                    public void call(List<BaseDomain> baseDomains) {
                        Intent intent = new Intent(SelectMapActivity.this, MainActivity.class);
                        Bundle data = new Bundle();
                        data.putSerializable("maps", new SerilzeData<BaseDomain>(baseDomains));
                        intent.putExtras(data);
                        startActivity(intent);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG,"error message:"+throwable.getMessage());
                    }
                });
            }

            @Override
            public void onLongClick(MapDomain itemData) {
                displaySnackbar(itemData.getName() + " long clicked", null, null);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMap(maps.size(), new MapDomain());
            }
        });
    }

    private void displaySnackbar(String text, String actionName, View.OnClickListener action) {
        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                .setAction(actionName, action);

        View v = snack.getView();
        v.setBackgroundColor(getResources().getColor(R.color.secondary));
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_action)).setTextColor(Color.BLACK);

        snack.show();
    }

    private int removeMap(MapDomain map) {
        int pos = maps.indexOf(map);
        maps.remove(map);
        adapter.notifyItemRemoved(pos);
        return pos;
    }

    private void addMap(int pos, MapDomain map) {
        maps.add(pos, map);
        adapter.notifyItemInserted(pos);
    }

}
