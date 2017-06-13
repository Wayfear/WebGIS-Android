package com.example.kanxuan.baidumap;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.kanxuan.baidumap.Domain.BaseDomain;
import com.example.kanxuan.baidumap.Domain.CommonCoverDomain;
import com.example.kanxuan.baidumap.Domain.CommonPipeDomain;
import com.example.kanxuan.baidumap.Domain.HistoryData;
import com.example.kanxuan.baidumap.Domain.Information;
import com.example.kanxuan.baidumap.Domain.LayerLineDate;
import com.example.kanxuan.baidumap.Domain.LayerPointDate;
import com.example.kanxuan.baidumap.Domain.LineDomain;
import com.example.kanxuan.baidumap.Domain.MapData;
import com.example.kanxuan.baidumap.Domain.PointDomain;
import com.example.kanxuan.baidumap.Domain.SerilzeData;
import com.example.kanxuan.baidumap.Domain.UpdateWebLineLayer;
import com.example.kanxuan.baidumap.Domain.UpdateWebPointLayer;
import com.example.kanxuan.baidumap.Enums.StatusEnum;
import com.example.kanxuan.baidumap.Enums.TypeEnum;
import com.example.kanxuan.baidumap.Http.BaseResponse;
import com.example.kanxuan.baidumap.HttpLoader.MapLoader;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

public class CommonUserActivity extends AppCompatActivity {

    private BoomMenuButton bmb;
    MapView mMapView = null;
    BaiduMap mBaiduMap = null;
//    LocationHelper locationHelper;

    private MapData mapData;
    private List<HistoryData> historyData = new ArrayList<>();


    private TypeEnum saveItems = null;

    private List<PointDomain> points = new ArrayList<>();

    LocationManager mLocationManager;
    private LocationClient mLocationClient = null;

    static private String TAG = "CommonUserActivity";
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.GCJ02);
        setContentView(R.layout.activity_common_user);
        bmb = (BoomMenuButton)findViewById(R.id.bmb);
        assert bmb != null;

        InitBmb();

        Intent lastIntent = getIntent();
        SerilzeData<BaseDomain> da = (SerilzeData<BaseDomain>)lastIntent.getSerializableExtra("maps");
        List<BaseDomain> maps = da.getData();
        layerId = maps.get(0).getId();
        saveItems = maps.get(0).getType();

        historyData.add(new HistoryData(maps));

        if(maps.size()!=0) {
            MapLoader mapLoader = new MapLoader();
            if(maps.get(0).getType()== TypeEnum.YJG) {
                mapLoader.getCoverLayerByLayerID(maps.get(0).getId()).subscribe(new Action1<LayerPointDate>() {
                    @Override
                    public void call(LayerPointDate layerDate) {
                        Log.e(TAG, "success");
                        mapData = new MapData(layerDate);
                        DrawCover();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG,"error message:"+ throwable.getMessage());
                    }
                });
            }
            else {
                mapLoader.getLineLayerByLayerID(maps.get(0).getId()).subscribe(new Action1<LayerLineDate>() {
                    @Override
                    public void call(LayerLineDate layerDate) {
                        Log.e(TAG, "success");
                        mapData = new MapData(layerDate);
                        DrawLine();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG,"error message:"+throwable.getMessage());
                    }
                });
            }

        }

        mMapView = (MapView) findViewById(R.id.bmapView);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getApplicationContext());

        BDLocationListener listener = new CommonUserActivity.MyLocationListener();
        mLocationClient.registerLocationListener(listener);
        getHistoryData();

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Information info = (Information) marker.getExtraInfo().get("info");
                TextView tv = new TextView(getApplicationContext());
                tv.setBackgroundResource(R.mipmap.ground_overlay);
                tv.setGravity(Gravity.CENTER_VERTICAL);
                tv.setText(info.getStatus().toString());
                //定义用于显示该InfoWindow的坐标点
                LatLng pt = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
                InfoWindow mInfoWindow = new InfoWindow(tv, pt, -47);
                //显示InfoWindow
                mBaiduMap.showInfoWindow(mInfoWindow);


//                Intent intent = new Intent(MainActivity.this, ItemsActivity.class);
//                Bundle data = new Bundle();
//                data.putSerializable("info",info);
//                data.putString("layerId", layerId);
//                if(info.getType() == TypeEnum.XSG) {
//                    data.putSerializable("lines", new SerilzeData<LineDomain>(mapData.getLineList()));
//                }
//                else {
//                    data.putSerializable("points", new SerilzeData<PointDomain>( mapData.getPointList()));
//                }
//                intent.putExtras(data);
//                startActivity(intent);
                return false;
            }
        });

    }

    private void InitBmb() {
        bmb.clearBuilders();
        bmb.addBuilder(new HamButton.Builder().normalImageRes(R.mipmap.location).normalText("定位").subNormalText("获取个人定位"));
        bmb.addBuilder((new HamButton.Builder()).normalImageRes(R.mipmap.layer).normalText("选择图层").subNormalText("选择某个图层"));
        bmb.setOnBoomListener(new OnBoomListenerAdapter() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                super.onClicked(index, boomButton);
                changeBoomButton(index);
            }
        });
    }



    private void saveButton(int index) {
        switch (index) {
            case 0:
                FindPosition();
                bmb.reboom();
                break;
            case 1:
                MapLoader mapLoader = new MapLoader();

                List<LineDomain> lines = new ArrayList<>();

                if(mapData.getType()==TypeEnum.XSG) {
                    lines.addAll(mapData.getLineList());
                    if(points.size()>=2) {
                        for(int i=0;i<points.size()-1;i++) {
                            lines.add(new LineDomain(points.get(i).getX(),points.get(i).getY(),points.get(i).getZ(),
                                    points.get(i+1).getX(),points.get(i+1).getY(),points.get(i+1).getZ(), StatusEnum.GOOD));
                        }

                        UpdateWebLineLayer updateWebLineLayer = new UpdateWebLineLayer(layerId, new CommonPipeDomain(lines));
                        mapLoader.updateLineLayer(updateWebLineLayer).subscribe(new Action1<BaseResponse<Object>>() {
                                                                                    @Override
                                                                                    public void call(BaseResponse<Object> objectBaseResponse) {
                                                                                        Log.v(TAG, "success");
                                                                                    }}
                                , new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        Log.e(TAG, "error message:" + throwable.getMessage());
                                    }
                                });


                    }

                }
                else {
                    points.addAll(mapData.getPointList());
                    UpdateWebPointLayer updateWebPointLayer = new UpdateWebPointLayer(layerId, new CommonCoverDomain(points));
                    mapLoader.updatePointLayer(updateWebPointLayer).subscribe(new Action1<BaseResponse<Object>>() {
                                                                                  @Override
                                                                                  public void call(BaseResponse<Object> objectBaseResponse) {
                                                                                      Log.v(TAG, "success");
                                                                                  }}
                            , new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    Log.e(TAG, "error message:" + throwable.getMessage());
                                }
                            });
                }
                bmb.reboom();
                break;
            case 2:
                saveItems = null;
                points.clear();
                InitBmb();
                bmb.reboom();
                break;
        }
    }

//    private void ShowWindow() {
//        //创建InfoWindow展示的view
//        Button button = new Button(getApplicationContext());
//        button.setBackgroundResource(R.drawable.popup);
////定义用于显示该InfoWindow的坐标点
//        LatLng pt = new LatLng(39.86923, 116.397428);
////创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
//        InfoWindow mInfoWindow = new InfoWindow(button, pt, -47);
////显示InfoWindow
//        mBaiduMap.showInfoWindow(mInfoWindow);
//    }




    private void getHistoryData() {
        MapLoader mapLoader = new MapLoader();
        mapLoader.getHistoriesByMapId(230).subscribe(new Action1<List<HistoryData>>() {
            @Override
            public void call(List<HistoryData> historyDatas) {
                historyData.addAll(historyDatas);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, "error message:" + throwable.getMessage());
            }
        });

    }

    private void changeBoomButton(int index) {

        final String[] items = {TypeEnum.YJG.toString(), TypeEnum.XSG.toString()};
        final TypeEnum[] types = {TypeEnum.YJG, TypeEnum.XSG};
        switch (index) {
            case 0:
                FindPosition();
                bmb.reboom();
                break;
            case 1:
                Intent intent = new Intent(CommonUserActivity.this, SelectLayerActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("history", new SerilzeData<HistoryData>(historyData));
                intent.putExtras(data);
                intent.setFlags(0);
                startActivityForResult(intent,0);
                bmb.reboom();
                break;
        }
    }


    class MyLocationListener implements BDLocationListener {

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

        /**
         * 接收位置的信息回调方法
         *
         * @param location
         */
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return;
            }
            if(saveItems!=null) {
                points.add(new PointDomain(location.getLongitude(), location.getLatitude(), 0, StatusEnum.GOOD));
                if(saveItems==TypeEnum.YJG) {

                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_map);
                    //创建一个图层选项
                    LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
                    OverlayOptions options = new MarkerOptions().position(latlng).icon(bitmapDescriptor);
                    mBaiduMap.addOverlay(options);
                    MapStatus mMapStatus = new MapStatus.Builder()
                            .target(latlng)
                            .zoom(16)
                            .build();
                    //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
                    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                    //改变地图状态
                    mBaiduMap.setMapStatus(mMapStatusUpdate);
                }
                else if(saveItems==TypeEnum.XSG) {

                    List<LatLng> newLine = new ArrayList<>();

                    for(int i=0;i<points.size();i++){
                        newLine.add(new LatLng(points.get(i).getY(),points.get(i).getX()));
                    }
                    LatLng target = new LatLng(points.get(points.size()-1).getY(),points.get(points.size()-1).getX());

                    OverlayOptions ooPolyline = new PolylineOptions().width(5)
                            .color(0xAAFF0000).points(newLine).visible(true);
                    mBaiduMap.addOverlay(ooPolyline);
                    MapStatus mMapStatus = new MapStatus.Builder()
                            .target(target)
                            .zoom(16)
                            .build();
                    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                    mBaiduMap.setMapStatus(mMapStatusUpdate);
                }
                return;
            }
            mBaiduMap.clear();


            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_map);
            //创建一个图层选项
            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
            OverlayOptions options = new MarkerOptions().position(latlng).icon(bitmapDescriptor);
            mBaiduMap.addOverlay(options);
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(latlng)
                    .zoom(16)
                    .build();
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            //改变地图状态
            mBaiduMap.setMapStatus(mMapStatusUpdate);


        }

    }

    private String layerId;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==0 && resultCode==0) {
            Bundle bundle = data.getExtras();
            int group = bundle.getInt("gIndex");
            int child = bundle.getInt("cIndex");
            MapLoader mapLoader = new MapLoader();
            layerId  = historyData.get(group).getData().get(child).getId();
            if(historyData.get(group).getData().get(child).getType()== TypeEnum.YJG) {
                mapLoader.getCoverLayerByLayerID(historyData.get(group).getData().get(child).getId()).subscribe(new Action1<LayerPointDate>() {
                    @Override
                    public void call(LayerPointDate layerDate) {
                        Log.e(TAG, "success");
                        mapData = new MapData(layerDate);
                        DrawCover();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG,"error message:"+throwable.getMessage());
                    }
                });
            }
            else {
                mapLoader.getLineLayerByLayerID(historyData.get(group).getData().get(child).getId()).subscribe(new Action1<LayerLineDate>() {
                    @Override
                    public void call(LayerLineDate layerDate) {
                        Log.e(TAG, "success");
                        mapData = new MapData(layerDate);
                        DrawLine();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG,"error message:"+throwable.getMessage());
                    }
                });
            }
        }


    }



    public void DrawLine(){
        mBaiduMap.clear();
        LatLng target = new LatLng(0,0);
        Map<List<LatLng>, StatusEnum> data = mapData.ToDrawList();
        // 添加图层功能
        int i=0;
        for(Map.Entry<List<LatLng>, StatusEnum> entry:data.entrySet()){
            int color;
            target = entry.getKey().get(0);
            if(entry.getValue()==StatusEnum.GOOD) {
                color = 0xAAFF0000;
            }
            else {
                color = 0xAAFF00FF;
            }
            OverlayOptions ooPolyline = new PolylineOptions().width(5)
                    .color(color).points(entry.getKey()).visible(true);
            Marker marker = (Marker)mBaiduMap.addOverlay(ooPolyline);
            Bundle bundle = new Bundle();
            bundle.putSerializable("info", new Information(TypeEnum.XSG, entry.getValue(),i));
            i++;
        }
        if(data.size()!=0) {
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(target)
                    .zoom(16)
                    .build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            mBaiduMap.setMapStatus(mMapStatusUpdate);
        }

    }

    public void DrawCover() {
        mBaiduMap.clear();

        LatLng latlng = new LatLng(10,10);
        for(int i=0;i<mapData.getPointList().size();i++) {
            BitmapDescriptor bitmapDescriptor;
            if(mapData.getPointList().get(i).getStatus()==StatusEnum.GOOD) {
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_map);
            }
            else {
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_map);
            }
            latlng = new LatLng(mapData.getPointList().get(i).getY(), mapData.getPointList().get(i).getX());
            OverlayOptions options = new MarkerOptions().position(latlng).icon(bitmapDescriptor);
            Marker marker = (Marker) mBaiduMap.addOverlay(options);
            Bundle bundle = new Bundle();

            bundle.putSerializable("info", new Information(mapData.getPointList().get(i),i));
            marker.setExtraInfo(bundle);

        }
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(latlng)
                .zoom(16)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }



    public void FindPosition() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 0; //5秒发送一次
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setNeedDeviceDirect(true); //返回的定位结果包含手机机头方向
        mLocationClient.setLocOption(option);
        mLocationClient.start(); //启动位置请求
        mLocationClient.requestLocation();//发送请求
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

}
