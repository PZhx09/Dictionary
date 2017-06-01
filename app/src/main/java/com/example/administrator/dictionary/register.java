package com.example.administrator.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Dao.UserDAO;
import Dao.UserPlanDAO;
import Dao.impl.UserDAOimpl;
import Dao.impl.UserPlanDAOimpl;

public class register extends AppCompatActivity {
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText confirmpassword;
    private Handler handler;
    private Handler handler1;
    private String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.reg_edit_name);
        email = (EditText) findViewById(R.id.reg_edit_email);
        password = (EditText) findViewById(R.id.reg_edit_pwd);
        confirmpassword = (EditText) findViewById(R.id.reg_edit_com);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        Toast.makeText(register.this, "注册失败，用户已存在！", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(register.this, "注册成功！", Toast.LENGTH_SHORT).show();

                        break;
                    case 2:
                        Toast.makeText(register.this, "查询错误！", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };

        handler1 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        Toast.makeText(register.this, "用户已经有相应的计划！", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Intent intent = new Intent(register.this, dictionary.class);
                        intent.putExtra("username",user);
                        startActivity(intent);
                        break;
                    case 2:
                        Toast.makeText(register.this, "查询错误！", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };
    }

    public void confirm(View v){
        user = username.getText().toString();
        String em = email.getText().toString();
        String pass = password.getText().toString();
        String con = confirmpassword.getText().toString();
        if(user.equals("")){
            Toast.makeText(register.this,"请输入用户名！",Toast.LENGTH_SHORT).show();
        }else{

        if(pass.equals(con))
        {
            UserDAO userdao = new UserDAOimpl(handler);
            userdao.register(user,pass,em);
            UserPlanDAO uspdao = new UserPlanDAOimpl(handler1);
            uspdao.InsertPlan(user,1,0);
        }else{
            Toast.makeText(register.this,"两次输入的密码不同！",Toast.LENGTH_SHORT).show();
        }

        }
    }

    public void cancel(View v){
        this.finish();
    }
}
