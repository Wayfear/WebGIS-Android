package com.example.kanxuan.baidumap;

import com.example.kanxuan.baidumap.http.BaseResponse;
import com.example.kanxuan.baidumap.http.ObjectLoader;
import com.example.kanxuan.baidumap.http.PayLoad;
import com.example.kanxuan.baidumap.http.RetrofitServiceManager;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.functions.Func1;


/**
 * Created by kanxuan on 2017/5/9.
 */

public class MapLoader extends ObjectLoader{

    private MapService mapService;

    public MapLoader(){
        mapService = RetrofitServiceManager.getInstance().create(MapService.class);
    }

    public Observable<MapDO> getMapbyID(int mapID){
//       return observe(mapService.getMapByID(mapID).map(new Func1<BaseResponse<MapDO>, MapDO>() {
//           @Override
//           public MapDO call(BaseResponse<MapDO> mapDOBaseResponse) {
//               return mapDOBaseResponse.data;
//           }
//       }));
       return observe(mapService.getMapByID(mapID)).map(new PayLoad<MapDO>());
    }

    public interface MapService {
        @GET("map/maps/id")
        Observable<BaseResponse<MapDO>> getMapByID(@Query("mapId") int mapID);
    }




}
