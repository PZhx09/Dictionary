package com.example.administrator.dictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import Dao.UserPlanDAO;
import Dao.impl.UserPlanDAOimpl;
import asynctask.youdao;
import entity.Basic;
import entity.Web;
import entity.Word;

public class learnexm extends Activity {
    String username;
    String studyid;
    BufferedReader buffertxt;
    int  scheme= 0;
    TreeMap<String,Integer> tmp = new TreeMap<String, Integer>();
    List<String> lst = new ArrayList<String>();
    int process = 0;
    Handler handler;
    Word explain;
    TextView txtword;
    TextView txtpro;
    TextView exp1;
    TextView exp2;
    Handler handler1;
    Handler handler2;
    Handler handler3;
    ProgressBar pro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learnexm);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");
        studyid = intent.getStringExtra("studyid");
        scheme = Integer.parseInt(intent.getStringExtra("scheme"));
        int s = Integer.parseInt(studyid);
        txtword = (TextView)findViewById(R.id.txt_stuword);
        txtpro = (TextView)findViewById(R.id.txt_stupro);
        exp1 = (TextView)findViewById(R.id.txt_exp1);
        exp2 = (TextView)findViewById(R.id.txt_exp2);
        pro = (ProgressBar)findViewById(R.id.prolearn);
        pro.setProgress(scheme+1);
        process = scheme;
        InputStream inputStream= null;
        switch(s){
            case 1:
                inputStream = getResources().openRawResource(R.raw.s1);
                break;
            case 2:
                inputStream = getResources().openRawResource(R.raw.s2);
                break;
            case 3:
                inputStream = getResources().openRawResource(R.raw.s3);
                break;
            case 4:
                inputStream = getResources().openRawResource(R.raw.s4);
                break;
            case 5:
                inputStream = getResources().openRawResource(R.raw.s5);
                break;
        }

        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "gbk");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            buffertxt = new BufferedReader(inputStreamReader);

            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = buffertxt.readLine()) != null) {
                String[] arr = line.split("\\s+");

                tmp.put(arr[0], 0);
                lst.add(arr[0]);

            }
            buffertxt.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        handler = new Handler(new learnexm.GetResultCallBack());
        new youdao(lst.get(process), handler).start();

    }

    private class GetResultCallBack implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                List<String> exp;
                List<Web> web; ;
                explain = (Word)msg.obj;
                Basic bs ;
                bs = explain.getBasic();
                txtword.setText(explain.getQuery());
                if(bs.getPhonetic().isEmpty()){
                }else {
                    String s = "|" +bs.getPhonetic()+"|";
                    txtpro.setText(s);
                }
                web = explain.getWeb();
                if(web.size()==0||web.size()==1){
                }
                else if(web.size()==2)
                {
                    exp1.setText(web.get(1).getKey());
                }
                else {
                    exp1.setText(web.get(1).getKey());
                    exp2.setText(web.get(2).getKey());
                }
            }

            return true;
        }
    }

    public void know(View v){
        process++;
        if(scheme<lst.size()) {
            scheme++;
        }
        exp1.setVisibility(View.INVISIBLE);
        exp2.setVisibility(View.INVISIBLE);

        handler1 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        break;
                    case 1:
                        Toast.makeText(learnexm.this, "找不到指定的用户！", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(learnexm.this, "查询错误！", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };
        UserPlanDAO usp = new UserPlanDAOimpl(handler1);
        usp.ChangePlan(username,Integer.parseInt(studyid),scheme);
        if(process>=lst.size()){

            if(Integer.parseInt(studyid)<5){

            handler2 = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 0:
                            break;
                        case 1:
                            Toast.makeText(learnexm.this, "找不到指定的用户！", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(learnexm.this, "查询错误1！", Toast.LENGTH_SHORT).show();
                            break;

                    }
                }
            };
            UserPlanDAO usp1 = new UserPlanDAOimpl(handler2);
                usp1.ChangePlan(username,Integer.parseInt(studyid)+1,0);}
            Toast.makeText(learnexm.this, "学习结束!", Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(learnexm.this,dictionary.class);
            intent.putExtra("username",username);
            startActivity(intent);
            this.finish();
        }else {
            pro.setProgress(process);
            handler = new Handler(new learnexm.GetResultCallBack());
            new youdao(lst.get(process), handler).start();
        }
    }

    public void notknow(View v){
        if(exp1.getVisibility()==View.VISIBLE){
            if(exp2.getVisibility()==View.VISIBLE)
            {
                process++;
                exp1.setVisibility(View.INVISIBLE);
                exp2.setVisibility(View.INVISIBLE);
                if(process>=lst.size()){
                    if(Integer.parseInt(studyid)<5){
                        handler3 = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                switch (msg.what) {
                                    case 0:
                                        break;
                                    case 1:
                                        Toast.makeText(learnexm.this, "找不到指定的用户！", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 2:
                                        Toast.makeText(learnexm.this, "查询错误！", Toast.LENGTH_SHORT).show();
                                        break;

                                }
                            }
                        };
                        UserPlanDAO usp1 = new UserPlanDAOimpl(handler3);
                        usp1.ChangePlan(username,Integer.parseInt(studyid)+1,0);}
                    Toast.makeText(learnexm.this, "学习结束！", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(learnexm.this,dictionary.class);
                    intent.putExtra("username",username);
                    startActivity(intent);
                    this.finish();
                }else {
                    handler1 = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            switch (msg.what) {
                                case 0:
                                    break;
                                case 1:
                                    Toast.makeText(learnexm.this, "找不到指定的用户！", Toast.LENGTH_SHORT).show();
                                    break;
                                case 2:
                                    Toast.makeText(learnexm.this, "查询错误！", Toast.LENGTH_SHORT).show();
                                    break;

                            }
                        }
                    };
                    pro.setProgress(process);
                    handler = new Handler(new learnexm.GetResultCallBack());
                    new youdao(lst.get(process), handler).start();
                }
            }else {
                exp2.setVisibility(View.VISIBLE);
            }

        }else {
            exp1.setVisibility(View.VISIBLE);
        }

    }

    public void detail(View v){
        Intent intent = new Intent(learnexm.this, Result.class);
        intent.putExtra("username",username);
        intent.putExtra("word",lst.get(process));
        startActivity(intent);

    }

    public void back(View v){
        Intent intent = new Intent(learnexm.this, Study.class);
        intent.putExtra("username",username);
        startActivity(intent);
        this.finish();


    }
}
