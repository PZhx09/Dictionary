package com.example.administrator.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Dao.UserDAO;
import Dao.impl.UserDAOimpl;
import entity.User;

public class resetpwd extends AppCompatActivity {
    private EditText old_pwd;
    private EditText new_pwd;
    private EditText confirm_new_pwd;
    private TextView old_email;
    private EditText new_email;
    private Handler handler;
    private Handler handler1;
    private String username ;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpwd);
        old_pwd=(EditText)findViewById(R.id.oldpwd_edit_name);
        new_pwd=(EditText)findViewById(R.id.newpwd_edit_name);
        confirm_new_pwd=(EditText)findViewById(R.id.confirmpwd_edit_pwd);
        new_email=(EditText)findViewById(R.id.newemail_edit);
        old_email=(TextView) findViewById(R.id.oldemail_edit);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        User user = (User)msg.obj;
                        old_email.setText(user.getEmail());
                        password = user.getPassword();
                        break;
                    case 1:
                        Toast.makeText(resetpwd.this, "修改失败，用户不存在！", Toast.LENGTH_SHORT).show();

                        break;
                    case 2:
                        Toast.makeText(resetpwd.this, "查询错误！", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        UserDAO userdao = new UserDAOimpl(handler);
        userdao.findUser(username);

    }
    public void confirm(View v) {


        String oldpass = old_pwd.getText().toString();
        String newpass = new_pwd.getText().toString();
        String con = confirm_new_pwd.getText().toString();

        String new_emai = new_email.getText().toString();
        if(new_emai.equals("")){
            new_emai = old_email.getText().toString();
        }
        if (oldpass.equals(password)) {

            if (newpass.equals(con)) {
                handler1 = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 0:
                                Toast.makeText(resetpwd.this, "修改成功！", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(resetpwd.this, myset.class);
                                intent.putExtra("username",username);
                                startActivity(intent);
                                break;
                            case 1:
                                Toast.makeText(resetpwd.this, "修改失败，用户不存在！", Toast.LENGTH_SHORT).show();

                                break;
                            case 2:
                                Toast.makeText(resetpwd.this, "查询错误！", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };
                if(newpass.equals("")){
                    newpass = password;
                }
                UserDAO userdao = new UserDAOimpl(handler1);
                userdao.UserInfoChange(username, newpass, new_emai);
            } else {
                Toast.makeText(resetpwd.this, "两次输入的密码不同！", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(resetpwd.this, "旧密码错误！", Toast.LENGTH_SHORT).show();
        }
    }

    public void clear(View v){
        old_pwd.setText("");
        new_pwd.setText("");
        new_email.setText("");
        confirm_new_pwd.setText("");
    }
}
