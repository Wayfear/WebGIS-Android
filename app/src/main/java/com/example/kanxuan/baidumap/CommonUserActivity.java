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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
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
import com.example.kanxuan.baidumap.Domain.MongoLayer;
import com.example.kanxuan.baidumap.Domain.MyBaseDomain;
import com.example.kanxuan.baidumap.Domain.PointDomain;
import com.example.kanxuan.baidumap.Domain.SerilzeData;
import com.example.kanxuan.baidumap.Domain.UpdateWebLineLayer;
import com.example.kanxuan.baidumap.Domain.UpdateWebPointLayer;
import com.example.kanxuan.baidumap.Domain.WebLayer;
import com.example.kanxuan.baidumap.Enums.StatusEnum;
import com.example.kanxuan.baidumap.Enums.TypeEnum;
import com.example.kanxuan.baidumap.Http.BaseResponse;
import com.example.kanxuan.baidumap.HttpLoader.MapLoader;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.BoomButtonBuilder;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListenerAdapter;
import com.baidu.mapapi.map.InfoWindow;
import android.graphics.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

public class CommonUserActivity extends AppCompatActivity {

    private BoomMenuButton bmb;
    MapView mMapView = null;
    BaiduMap mBaiduMap = null;
    int publicUserID;
//    LocationHelper locationHelper;

    String publicLayerId;
    Marker publicUserMarker=null;

    private boolean isPopUp = false;

    LocationManager mLocationManager;
    private LocationClient mLocationClient = null;

    static private String TAG = "CommonUserActivity";
    Toolbar toolbar;

    List<MongoLayer> allData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.GCJ02);
        setContentView(R.layout.activity_common_user);
        Intent lastIntent = getIntent();
        publicUserID = lastIntent.getIntExtra("userId", 0);
        SerilzeData<MongoLayer> da = (SerilzeData<MongoLayer>)lastIntent.getSerializableExtra("layers");
        allData = da.getData();
        bmb = (BoomMenuButton)findViewById(R.id.bmb);
        assert bmb != null;

        InitBmb();

        mMapView = (MapView) findViewById(R.id.bmapView);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getApplicationContext());

        BDLocationListener listener = new CommonUserActivity.MyLocationListener();
        mLocationClient.registerLocationListener(listener);

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(marker==publicUserMarker) {
                    return false;
                }
                final PointDomain point = (PointDomain) marker.getExtraInfo().get("point");
                View linlayout=CommonUserActivity.this.getLayoutInflater().inflate(R.layout.pop_layout, null);
                Button btnClick=(Button) linlayout.findViewById(R.id.content);
                btnClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(CommonUserActivity.this, ReportActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("point", point);
                        intent.putExtra("layerId", publicLayerId);
                        intent.putExtra("userId", publicUserID);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                Button closeBtn=(Button) linlayout.findViewById(R.id.close);
                closeBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        mBaiduMap.hideInfoWindow();
                        isPopUp = false;
                    }
                });

                final LatLng ll = marker.getPosition();
                InfoWindow mInfoWindowDetail=new InfoWindow(linlayout, ll, -100);
                if(isPopUp) {
                    mBaiduMap.hideInfoWindow();
                    isPopUp = false;
                }
                else {
                    mBaiduMap.showInfoWindow(mInfoWindowDetail);
                    isPopUp = true;
                }

                return false;

            }
        });
//        FindPosition();
        DrawCover(0);
    }

    private void getAllData() {

    }

    private void InitBmb() {
        bmb.clearBuilders();
        bmb.addBuilder(new HamButton.Builder().normalImageRes(R.mipmap.location).normalText("定位").subNormalText("获取个人定位"));
        bmb.addBuilder((new HamButton.Builder()).normalImageRes(R.mipmap.layer).normalText("选择图层").subNormalText("选择某个图层"));
        bmb.addBuilder((new HamButton.Builder()).normalImageRes(R.mipmap.add).normalText("添加报修").subNormalText("此报修你现在的坐标"));
        bmb.setOnBoomListener(new OnBoomListenerAdapter() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                super.onClicked(index, boomButton);
                changeBoomButton(index);
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
                Intent intent = new Intent(CommonUserActivity.this, CommonUserSelectLayer.class);
                Bundle data = new Bundle();
                data.putSerializable("allData", new SerilzeData<MongoLayer>(allData));
                intent.putExtras(data);
                intent.putExtra("userId", publicUserID);
                intent.setFlags(0);
                startActivityForResult(intent,0);
                bmb.reboom();
                break;
            case 2:

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

            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_map);
            //创建一个图层选项
            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
            OverlayOptions options = new MarkerOptions().position(latlng).icon(bitmapDescriptor);
            if(publicUserMarker!=null) {
                publicUserMarker.remove();
            }
            publicUserMarker = (Marker)mBaiduMap.addOverlay(options);

            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(latlng)
                    .zoom(18)
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
            int index = data.getIntExtra("index", 0);
            DrawCover(index);
        }
    }
    int[][] picSelect = {
            {R.mipmap.well_black,R.mipmap.well_red,R.mipmap.well_yellow, R.mipmap.well_green, R.mipmap.well_blue},
            {R.mipmap.light_black, R.mipmap.light_red,R.mipmap.light_yellow,R.mipmap.light_green,R.mipmap.light_blue}};


    public void DrawCover(int index) {
        mBaiduMap.clear();


        if(allData==null||allData.size()==0)
            return;
        MongoLayer mongoLayer;
        if(allData.size()>index) {
            mongoLayer = allData.get(index);
        }
        else {
            mongoLayer = allData.get(0);
        }
        publicLayerId = mongoLayer.getId();
        LatLng latlng = new LatLng(10,10);
        List<PointDomain> data = mongoLayer.getData().getPointList();
        int indexi = 0, indexj = 0;
        if (mongoLayer.getData().getType()==TypeEnum.YJG) {
            indexi = 0;
        }
        else if(mongoLayer.getData().getType()==TypeEnum.LD) {
            indexi = 1;
        }
        for(int i=0;i<data.size();i++) {
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(picSelect[indexi][data.get(i).getStatus().getCode()]);
            latlng = new LatLng(data.get(i).getY(), data.get(i).getX());
            OverlayOptions options = new MarkerOptions().position(latlng).icon(bitmapDescriptor);
            Marker marker = (Marker) mBaiduMap.addOverlay(options);
            Bundle bundle = new Bundle();

            bundle.putSerializable("point", data.get(i));
            marker.setExtraInfo(bundle);

        }
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(latlng)
                .zoom(18)
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
