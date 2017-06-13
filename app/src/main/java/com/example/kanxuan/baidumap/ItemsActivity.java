package com.example.kanxuan.baidumap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;

import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.model.LatLng;
import com.example.kanxuan.baidumap.Domain.CommonCoverDomain;
import com.example.kanxuan.baidumap.Domain.CommonPipeDomain;
import com.example.kanxuan.baidumap.Domain.LineDomain;
import com.example.kanxuan.baidumap.Domain.PointDomain;
import com.example.kanxuan.baidumap.Domain.UpdateWebLineLayer;
import com.example.kanxuan.baidumap.Domain.UpdateWebPointLayer;
import com.example.kanxuan.baidumap.Enums.StatusEnum;
import com.example.kanxuan.baidumap.Http.BaseResponse;
import com.getbase.floatingactionbutton.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kanxuan.baidumap.Domain.BaseDomain;
import com.example.kanxuan.baidumap.Domain.Information;
import com.example.kanxuan.baidumap.Domain.LayerLineDate;
import com.example.kanxuan.baidumap.Domain.LayerPointDate;
import com.example.kanxuan.baidumap.Domain.MapData;
import com.example.kanxuan.baidumap.Domain.SerilzeData;
import com.example.kanxuan.baidumap.Enums.TypeEnum;
import com.example.kanxuan.baidumap.HttpLoader.MapLoader;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;

public class ItemsActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    FloatingActionsMenu menu;
    ImageView imageView;
    final static String TAG = "items";

    String[] spinnerContent = {"丢失", "损坏", "堵塞", "完好", "维修"};

    List<PointDomain> points;

    List<LineDomain> lines;

    String layerId;

    Information info;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        Intent lastIntent = getIntent();
        info = (Information)lastIntent.getSerializableExtra("info");
        layerId = lastIntent.getStringExtra("layerId");
        String url;
        String id;
        if(info.getType()==TypeEnum.XSG) {
            SerilzeData<LineDomain> da = (SerilzeData<LineDomain>)lastIntent.getSerializableExtra("lines");
            lines = da.getData();
            url = lines.get(info.getIndex()).getUrl();
            id = lines.get(info.getIndex()).getId();
        }
        else {
            SerilzeData<PointDomain> da = (SerilzeData<PointDomain>)lastIntent.getSerializableExtra("points");
            points = da.getData();
            url = points.get(info.getIndex()).getUrl();
            id = points.get(info.getIndex()).getId();
        }



        menu = (FloatingActionsMenu)findViewById(R.id.menu);

        FloatingActionButton save = (FloatingActionButton) findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MapLoader mapLoader = new MapLoader();
                if(info.getType()!=TypeEnum.YJG) {
                    points.get(info.getIndex()).setUrl(" http://oae23pecn.bkt.clouddn.com/"+key);
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
                else {
                    points.get(info.getIndex()).setUrl(" http://oae23pecn.bkt.clouddn.com/"+key);
                    UpdateWebPointLayer updateWebPointLayer = new UpdateWebPointLayer(layerId, new CommonCoverDomain(points));
                    mapLoader.updatePointLayer(updateWebPointLayer).subscribe(new Action1<BaseResponse<Object>>() {
                              @Override
                              public void call(BaseResponse<Object> objectBaseResponse) {
                                  Log.v(TAG, "success");
                                  menu.collapse();
                              }}
                            , new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    Log.e(TAG, "error message:" + throwable.getMessage());
                                    menu.collapse();
                                }
                            });
                }


            }
        });

        FloatingActionButton takepic = (FloatingActionButton) findViewById(R.id.photo);

        takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        imageView = (ImageView)findViewById(R.id.background);

        EditText oid = (EditText)findViewById(R.id.id);
        if(id!="") {
            oid.setText(id);
        }




        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.my_simple_spinner_item, spinnerContent);
        adapter.setDropDownViewResource(R.layout.my_drop_down_item);

        spinner .setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        spinner.setSelection(info.getStatus().ordinal());

        if(url!=""&&url!=null) {
            MapLoader mapLoader = new MapLoader();
            mapLoader.getImg(url).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.e(TAG, "Success");
                    if(response.body()!=null) {
                        InputStream inputStream = response.body().byteStream();

                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageView.setImageBitmap(bitmap);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }

    }



    String key;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            UploadManager uploadManager = new UploadManager();
//            File data = <File对象、或 文件路径、或 字节数组>
            long time=System.currentTimeMillis();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Date d1=new Date(time);
            key = format.format(d1) + ".png";
            String token = "0kgFvvZPnN4KioE-Dah_t2MtLWH0S3ZREmXArbMI:AhUstiXtv5ELhlHVGB_8UEUc5vQ=:eyJzY29wZSI6Imthbnh1YW4iLCJkZWFkbGluZSI6MTQ5OTE3OTY5MH0=";
            uploadManager.put(Bitmap2Bytes(imageBitmap), key, token,
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject res) {
                            Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                        }
                    }, null);

            imageView.setImageBitmap(imageBitmap);
            menu.collapse();
            //oae23pecn.bkt.clouddn.com/2017-06-04-22-53-02.png
        }
    }

    private byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

}
