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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Dao.UserPlanDAO;
import Dao.impl.UserPlanDAOimpl;
import entity.UserPlan;

public class Study extends AppCompatActivity {
    private ImageButton stu_dictionary_button;
    private ImageButton stu_translate_button;
    private ImageButton stu_learn_button;
    private ImageButton stu_vocubulary_button;
    private ImageButton stu_myset_button;
    private ImageView totalview;
    private Button begin_learn;
    private String username;
    private TextView txtfinish;
    private Handler handler;
    private int stuid= 0;
    private  UserPlan usp;
    private BottomNavigationView mNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");
        txtfinish = (TextView)findViewById(R.id.txt_finished);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:

                        usp = (UserPlan)msg.obj;
                        System.out.println(usp.getUsername()+"  "+usp.getScheme()+"aaaaaaaaaaaaaaa");
                        String s= Integer.toString(usp.getScheme());
                        txtfinish.setText(s);
                        stuid = usp.getStudyID();
                        System.out.println("sadddd    "  +stuid);
                        break;
                    case 1:
                        Toast.makeText(Study.this, "暂无计划！", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(Study.this, "查询错误！", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };


        UserPlanDAO usp = new UserPlanDAOimpl(handler);
        usp.GetPlan(username);


        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigationView.getMenu().getItem(0).setChecked(false);
        mNavigationView.getMenu().getItem(2).setChecked(true);
        mNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if("词典".equals(item.getTitle().toString())){
                            Intent intent = new Intent(Study.this, dictionary.class);
                            intent.putExtra("username",username);
                            startActivity(intent);

                        }else if("翻译".equals(item.getTitle().toString())){
                            Intent intent = new Intent(Study.this, translate.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                        }else if("学习".equals(item.getTitle().toString())){
                        }else if("生词本".equals(item.getTitle().toString())){
                            Intent intent = new Intent(Study.this, vocabulary.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                        }
                        else if("我的".equals(item.getTitle().toString())){
                            Intent intent = new Intent(Study.this, myset.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                        }
                        return true;
                    }
                });

    }

    public void overview(View v)
    {
        Intent intent =new Intent(Study.this,learnplan.class);
        intent.putExtra("username",username);
        String stu = Integer.toString(stuid);
        intent.putExtra("studyid",stu);
        String sch = Integer.toString(usp.getScheme());
        intent.putExtra("scheme",sch);
        startActivity(intent);

    }
    public void begin(View v)
    {
        Intent intent =new Intent(Study.this,learnexm.class);
        intent.putExtra("username",username);
        String stu = Integer.toString(stuid);
        intent.putExtra("studyid",stu);
        String sch = Integer.toString(usp.getScheme());
        intent.putExtra("scheme",sch);
        startActivity(intent);
        this.finish();
    }



}
