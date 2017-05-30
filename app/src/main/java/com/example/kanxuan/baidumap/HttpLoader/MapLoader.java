package com.example.kanxuan.baidumap.HttpLoader;

import com.example.kanxuan.baidumap.Domain.BaseDomain;
import com.example.kanxuan.baidumap.Domain.HistoryData;
import com.example.kanxuan.baidumap.Domain.LayerLineDate;
import com.example.kanxuan.baidumap.Domain.LayerPointDate;
import com.example.kanxuan.baidumap.Domain.MapDomain;
import com.example.kanxuan.baidumap.Http.BaseResponse;
import com.example.kanxuan.baidumap.Http.ObjectLoader;
import com.example.kanxuan.baidumap.Http.PayLoad;
import com.example.kanxuan.baidumap.Http.RetrofitServiceManager;

import java.util.List;

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
    private LayerService layerService;

    private HistoryService historyService;

    public MapLoader(){
        mapService = RetrofitServiceManager.getInstance().create(MapService.class);
        layerService = RetrofitServiceManager.getInstance().create(LayerService.class);
        historyService = RetrofitServiceManager.getInstance().create(HistoryService.class);
    }

    public Observable<MapDomain> getMapbyID(int mapID){
       return observe(mapService.getMapByID(mapID)).map(new PayLoad<MapDomain>());
    }

    public Observable<List<MapDomain>> getMapsByAccountId(int accountId, int folderId){
        return observe(mapService.getMapByAccountId(accountId, folderId)).map(new PayLoad<List<MapDomain>>());
    }

//    public Observable<WebMapContent>  getLayersByMapId(int mapId){
//        return observe(layerService.getLayerByMapID(mapId)).map(new PayLoad<WebMapContent>());
//    }

    public Observable<List<BaseDomain>> getLayersByMapId(int mapId) {
        return observe(layerService.getLayerByMapID(mapId)).map(new PayLoad<List<BaseDomain>>());
    }

    public Observable<LayerPointDate> getCoverLayerByLayerID(String layerId) {
        return observe(layerService.getCoverLayerByLayerID(layerId)).map(new PayLoad<LayerPointDate>());
    }

    public Observable<LayerLineDate> getLineLayerByLayerID(String layerId) {
        return observe(layerService.getLineLayerByLayerID(layerId)).map(new PayLoad<LayerLineDate>());
    }
    public Observable<List<HistoryData>> getHistoriesByMapId(int mapId) {
        return observe(historyService.getHistoriesByMapId(mapId)).map(new PayLoad<List<HistoryData>>());
    }



    public interface MapService {
        @GET("map/maps/id")
        Observable<BaseResponse<MapDomain>> getMapByID(@Query("mapId") int mapID);

        @GET("map/maps/accountidandfolderid")
        Observable<BaseResponse<List<MapDomain>>> getMapByAccountId(@Query("accountId") int accoubntID, @Query("folderId") int folderId);

    }

    public interface LayerService {
        @POST("layer/emptyLayers")
        Observable<BaseResponse<Object>> addEmptyLayer(@Query("mapId") int mapID, @Query("type")String type);

        @PATCH("layer/layers/point/id")
        Observable<BaseResponse<Object>> updatePointLayer(@Query("mapId") int mapID, @Query("type")String type);

        @PATCH("layer/layers/line/id")
        Observable<BaseResponse<Object>> updateLineLayer(@Query("mapId") int mapID, @Query("type")String type);

//        @GET("layer/layers")
//        Observable<BaseResponse<WebMapContent>> getLayerByMapID(@Query("mapId") int mapID);

        @GET("map/maps/layerId...type")
        Observable<BaseResponse<List<BaseDomain>>> getLayerByMapID(@Query("mapId") int mapId);

        @GET("layer/layer")
        Observable<BaseResponse<LayerPointDate>> getCoverLayerByLayerID(@Query("layerId") String layerId);

        @GET("layer/layer")
        Observable<BaseResponse<LayerLineDate>> getLineLayerByLayerID(@Query("layerId") String layerId);
    }

    public interface HistoryService {
        @GET("history/histories/mapId")
        Observable<BaseResponse<List<HistoryData>>> getHistoriesByMapId(@Query("mapId") int mapId);
    }






}
