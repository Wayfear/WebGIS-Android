package com.example.kanxuan.baidumap;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText emailView;
    private EditText passwordView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailView = (EditText)findViewById(R.id.et_email);
        passwordView = (EditText)findViewById(R.id.et_password);
        button = (Button)findViewById(R.id.btn_login);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailView.getText().toString();
                String password = passwordView.getText().toString();
                if(email.length()==0||password.length()==0){
                    Toast.makeText(getApplicationContext(), "请输入账号密码", Toast.LENGTH_SHORT).show();
                }
                else if(isEmailValid(email)){
                    Toast.makeText(getApplicationContext(), "邮箱格式不对", Toast.LENGTH_SHORT).show();
                }
                else if(isPasswordValid(password)){
                    Toast.makeText(getApplicationContext(), "密码长度过短", Toast.LENGTH_SHORT).show();
                }
                else {
                    // login
                }
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
