package com.example.kanxuan.baidumap;

import com.example.kanxuan.baidumap.Domain.MapDO;
import com.example.kanxuan.baidumap.http.BaseResponse;
import com.example.kanxuan.baidumap.http.ObjectLoader;
import com.example.kanxuan.baidumap.http.PayLoad;
import com.example.kanxuan.baidumap.http.RetrofitServiceManager;

import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by kanxuan on 2017/5/9.
 */

public class MapLoader extends ObjectLoader{

    private MapService mapService;

    public MapLoader(){
        mapService = RetrofitServiceManager.getInstance().create(MapService.class);
    }

    public Observable<MapDO> getMapbyID(int mapID){
       return observe(mapService.getMapByID(mapID)).map(new PayLoad<MapDO>());
    }

    public interface MapService {
        @GET("map/maps/id")
        Observable<BaseResponse<MapDO>> getMapByID(@Query("mapId") int mapID);
    }

    public interface LayerService {
        @POST("layer/emptyLayers")
        Observable<BaseResponse<Object>> addEmptyLayer(@Query("mapId") int mapID, @Query("type")String type);

        @PATCH("layer/layers/point/id")
        Observable<BaseResponse<Object>> updatePointLayer(@Query("mapId") int mapID, @Query("type")String type);

        @PATCH("layer/layers/line/id")
        Observable<BaseResponse<Object>> updateLineLayer(@Query("mapId") int mapID, @Query("type")String type);

    }






}
