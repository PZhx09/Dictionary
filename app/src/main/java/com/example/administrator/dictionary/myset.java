package com.example.administrator.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Dao.HistoryDAO;
import Dao.VocabularyDAO;
import Dao.impl.HistoryDAOimpl;
import Dao.impl.VocabularyDAOimpl;
import entity.History;

public class myset extends AppCompatActivity {
    private String username;
    private TextView txttime;
    private TextView txtnum;
    private TextView txtinfo;
    private TextView txtselect;
    private TextView clearhis;
    private TextView clearvoc;
    private TextView about;
    private TextView exit;
    private Handler handler;
    private Handler handler1;
    private Handler handler2;
    private BottomNavigationView mNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myset);
        txttime = (TextView)findViewById(R.id.txttime);
        txtnum = (TextView)findViewById(R.id.numWords);
        txtinfo = (TextView)findViewById(R.id.txtperson);
        clearhis = (TextView)findViewById(R.id.clear_history);
        clearvoc = (TextView)findViewById(R.id.clear_vocabulary);
        about = (TextView)findViewById(R.id.txtabout);
        exit = (TextView)findViewById(R.id.txtexit);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");

        txttime.setText(username);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        List<History> his;
                        his = (List<History>)msg.obj;
                        txtnum.setText(Integer.toString(his.size()));
                        break;
                    case 1:
                       txtnum.setText("0");
                        break;
                    case 2:
                        Toast.makeText(myset.this, "查询错误！", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };
        HistoryDAO his = new HistoryDAOimpl(handler);
        his.FindHistory(username);


        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigationView.getMenu().getItem(0).setChecked(false);
        mNavigationView.getMenu().getItem(4).setChecked(true);
        mNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if("词典".equals(item.getTitle().toString())){
                            Intent intent = new Intent(myset.this, dictionary.class);
                            intent.putExtra("username",username);
                            startActivity(intent);

                        }else if("翻译".equals(item.getTitle().toString())){
                            Intent intent = new Intent(myset.this, translate.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                        }else if("学习".equals(item.getTitle().toString())){
                            Intent intent = new Intent(myset.this, Study.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                        }else if("生词本".equals(item.getTitle().toString())){
                            Intent intent = new Intent(myset.this, vocabulary.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                        }
                        else if("我的".equals(item.getTitle().toString())){

                        }
                        return true;
                    }
                });

    }
    public void personinfo(View v)
    {
        Intent intent =new Intent(myset.this,resetpwd.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void clearhistory(View v){
        handler1 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        Toast.makeText(myset.this, "删除成功！", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(myset.this, "删除成功！", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(myset.this, "查询错误！", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };
        HistoryDAO his = new HistoryDAOimpl(handler1);
        his.DeleteAllHistory(username);
    }
    public void clearvocabulary(View v){
        handler2 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        Toast.makeText(myset.this, "删除成功！", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(myset.this, "删除成功！", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(myset.this, "查询错误！", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };
        VocabularyDAO voc = new VocabularyDAOimpl(handler2);
        voc.ClearVocabulary(username);

    }
    public void about(View v){
        Toast.makeText(myset.this, "2017畅词词典  All rights reserved. ", Toast.LENGTH_LONG).show();
    }

    public void exit(View v){
       System.exit(0);
    }

    public void search(View v)
    {
        Intent intent =new Intent(myset.this,dictionary.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void translate(View v)
    {
        Intent intent =new Intent(myset.this, translate.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void study(View v)
    {
        Intent intent =new Intent(myset.this,Study.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }
    public void vocabulary(View v)
    {
        Intent intent =new Intent(myset.this,vocabulary.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void myset(View v)
    {
    }
}
