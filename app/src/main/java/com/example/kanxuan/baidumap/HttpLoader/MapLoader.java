package com.example.kanxuan.baidumap.HttpLoader;

import com.example.kanxuan.baidumap.Domain.BaseDomain;
import com.example.kanxuan.baidumap.Domain.HistoryData;
import com.example.kanxuan.baidumap.Domain.LayerLineDate;
import com.example.kanxuan.baidumap.Domain.LayerPointDate;
import com.example.kanxuan.baidumap.Domain.MapDomain;
import com.example.kanxuan.baidumap.Domain.MongoLayer;
import com.example.kanxuan.baidumap.Domain.MongoRepair;
import com.example.kanxuan.baidumap.Domain.UpdateWebLineLayer;
import com.example.kanxuan.baidumap.Domain.UpdateWebPointLayer;
import com.example.kanxuan.baidumap.Domain.WebRepair;
import com.example.kanxuan.baidumap.Http.BaseResponse;
import com.example.kanxuan.baidumap.Http.ObjectLoader;
import com.example.kanxuan.baidumap.Http.PayLoad;
import com.example.kanxuan.baidumap.Http.RetrofitServiceManager;
import com.example.kanxuan.baidumap.Http.UserLoginResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
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

    public Observable<BaseResponse<Object>> updatePointLayer(UpdateWebPointLayer webPointLayer){
        return observe(layerService.updatePointLayer(webPointLayer));
    }

    public Observable<BaseResponse<Object>> updateLineLayer(UpdateWebLineLayer webLineLayer){
        return observe(layerService.updateLineLayer(webLineLayer));
    }

    public Call<ResponseBody> getImg(String url) {
        return  mapService.downloadFileWithDynamicUrlSync(url);
    }

    public  Observable<UserLoginResponse> Login(String username, String password) {
        return observe(mapService.Login(username, password).map(new PayLoad<UserLoginResponse>()));
    }

    public  Observable<List<MongoLayer>> getAllLayer() {
        return observe(layerService.getAllLayer().map(new PayLoad<List<MongoLayer>>()));
    }

    public Observable<BaseResponse<Object>> createRepair(WebRepair webRepair){
        return observe(mapService.createRepair(webRepair));
    }



    public interface MapService {
        @GET("map/maps/id")
        Observable<BaseResponse<MapDomain>> getMapByID(@Query("mapId") int mapID);

        @GET("map/maps/accountidandfolderid")
        Observable<BaseResponse<List<MapDomain>>> getMapByAccountId(@Query("accountId") int accoubntID, @Query("folderId") int folderId);

        @GET
        Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

        @FormUrlEncoded
        @POST("auth/token")
        Observable<BaseResponse<UserLoginResponse>> Login(@Field("username") String username, @Field("password") String password);

        @POST("repair/repair")
        Observable<BaseResponse<Object>> createRepair(@Body WebRepair webRepair);

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

        @PATCH("layer/layers/point/id")
        Observable<BaseResponse<Object>> updatePointLayer(@Body UpdateWebPointLayer webLater);

        @GET("layer/layers/all")
        Observable<BaseResponse<List<MongoLayer>>> getAllLayer();


        @PATCH("layer/layers/line/id")
        Observable<BaseResponse<Object>> updateLineLayer(@Body UpdateWebLineLayer webLater);
    }

    public interface HistoryService {
        @GET("history/histories/mapId")
        Observable<BaseResponse<List<HistoryData>>> getHistoriesByMapId(@Query("mapId") int mapId);
    }






}
