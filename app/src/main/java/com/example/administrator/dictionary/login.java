package com.example.administrator.dictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import Dao.UserDAO;
import Dao.impl.UserDAOimpl;

public class login extends Activity {
    private Button login_button;
    private Button register_button;
    private EditText username;
    private EditText password;
    private Handler handler;
    private String user;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bmob.initialize(this, "43649bece4c39942cf9d855696be135e");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("login_activity","onCreate");
        username = (EditText) findViewById(R.id.login_edit_account);
        password = (EditText) findViewById(R.id.login_edit_pwd);
        login_button=(Button) findViewById(R.id.login_btn_login);
        img = (ImageView)findViewById(R.id.imageView);
        img.setImageResource(R.mipmap.icon_meitu_1);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        Toast.makeText(login.this, "登录成功！", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(login.this, dictionary.class);
                        intent.putExtra("username",user);
                        startActivity(intent);
                        break;
                    case 1:

                        Toast.makeText(login.this, "登录失败，用户不存在！", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(login.this, "查询错误！", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };

       login_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               user = username.getText().toString();
               String pwd = password.getText().toString();
               UserDAO userdao = new UserDAOimpl(handler);
               userdao.LogIn(user,pwd);
           }
       });
        register_button=(Button)findViewById(R.id.login_btn_register);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this,register.class) ;
                startActivity(intent) ;
            }
        });
    }

}
