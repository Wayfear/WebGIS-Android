package com.example.kanxuan.baidumap;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kanxuan.baidumap.Domain.AuthenticationRequest;
import com.example.kanxuan.baidumap.Domain.MapDomain;
import com.example.kanxuan.baidumap.Domain.MongoLayer;
import com.example.kanxuan.baidumap.Domain.SerilzeData;
import com.example.kanxuan.baidumap.Http.BaseResponse;
import com.example.kanxuan.baidumap.Http.UserLoginResponse;
import com.example.kanxuan.baidumap.HttpLoader.AccountLoader;
import com.example.kanxuan.baidumap.HttpLoader.MapLoader;

import java.io.Serializable;
import java.util.List;

import rx.functions.Action1;

public class LoginActivity extends AppCompatActivity {

    private EditText emailView;
    private EditText passwordView;
    private Button button;
    final static private String TAG = "Login Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailView = (EditText) findViewById(R.id.et_email);
        passwordView = (EditText) findViewById(R.id.et_password);
        button = (Button) findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailView.getText().toString();
                String password = passwordView.getText().toString();

                MapLoader mapLoader = new MapLoader();
                mapLoader.Login(email, password).subscribe(new Action1<UserLoginResponse>() {
                    @Override
                    public void call(final UserLoginResponse userLoginResponseBaseResponse) {

                        if(userLoginResponseBaseResponse.getRole().equals("User")) {

                            MapLoader mapLoader = new MapLoader();
                            mapLoader.getAllLayer().subscribe(new Action1<List<MongoLayer>>() {
                                @Override
                                public void call(List<MongoLayer> mongoLayers) {
                                    Intent intent = new Intent(LoginActivity.this, CommonUserActivity.class);
                                    intent.putExtra("userId", userLoginResponseBaseResponse.getId());
                                    Bundle data = new Bundle();
                                    data.putSerializable("layers", new SerilzeData<MongoLayer>(mongoLayers));
                                    intent.putExtras(data);
                                    startActivity(intent);
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    Log.e(TAG, "Fail");
                                }
                            });

                        }
                        else {

                            MapLoader mapLoader = new MapLoader();
                            mapLoader.getMapsByAccountId(32, 0).subscribe(new Action1<List<MapDomain>>() {
                                @Override
                                public void call(List<MapDomain> o) {
                                    Intent intent = new Intent(LoginActivity.this, SelectMapActivity.class);
                                    Bundle data = new Bundle();
                                    data.putString("role", userLoginResponseBaseResponse.getRole());
                                    data.putSerializable("maps", new SerilzeData<MapDomain>(o));
                                    intent.putExtras(data);
                                    startActivity(intent);

                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    Log.e(TAG, throwable.toString());

                                }
                            });



                        }


                    }


//                if(email.length()==0||password.length()==0){
//                    Toast.makeText(getApplicationContext(), "请输入账号密码", Toast.LENGTH_SHORT).show();
//                }
//                else if(!isEmailValid(email)){
//                    Toast.makeText(getApplicationContext(), "邮箱格式不对", Toast.LENGTH_SHORT).show();
//                }
//                else if(!isPasswordValid(password)){
//                    Toast.makeText(getApplicationContext(), "密码长度过短", Toast.LENGTH_SHORT).show();
//                }
//                else {
                    // login
//                    AuthenticationRequest authenticationRequest = new AuthenticationRequest(email,password);

//                    AccountLoader accountLoader = new AccountLoader();
//                    MapLoader mapLoader = new MapLoader();
//
//                    if(true) {
//                        mapLoader.getMapsByAccountId(1,0).subscribe(new Action1<List<MapDomain>>() {
//                            @Override
//                            public void call(List<MapDomain> o) {
//                                Intent intent = new Intent(LoginActivity.this, SelectMapActivity.class);
//                                Bundle data = new Bundle();
//                                data.putSerializable("maps", new SerilzeData<MapDomain>(o));
//                                intent.putExtras(data);
//                                startActivity(intent);
//                            }
//                        }, new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable throwable) {
//                                Log.e(TAG,"error message:"+throwable.getMessage());
//                            }
//                        });
//                    }
//                    else {
//                        Toast.makeText(getApplicationContext(), "账号或者密码不对", Toast.LENGTH_SHORT).show();
//                    }


//
//                }
//            }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, throwable.toString());

                    }
                });


            }
        });
    }


    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }




}
