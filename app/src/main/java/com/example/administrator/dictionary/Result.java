package com.example.administrator.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Adapter.ResultAdapter;
import Adapter.WebAdapter;
import Dao.HistoryDAO;
import Dao.VocabularyDAO;
import Dao.impl.HistoryDAOimpl;
import Dao.impl.VocabularyDAOimpl;
import asynctask.youdao;
import entity.Basic;
import entity.Vocabulary;
import entity.Web;
import entity.Word;

public class Result extends AppCompatActivity {
    private String username;
    private String word;
    private TextView SearchWord;
    private Handler handler;
    private Handler handler1;
    private Handler handlerhis;
    private Handler handleradd;
    private Handler handlerdelete;
    private Word explain;
    private TextView pronounciation;
    private ListView expres;
    private ListView webres;
    private ImageView img;
    private boolean issave = false;
    private List<Vocabulary> voc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");
        word = intent.getStringExtra("word");
        SearchWord = (TextView)findViewById(R.id.text_word);
        pronounciation = (TextView)findViewById(R.id.txt_pronounce);
        expres = (ListView)findViewById(R.id.list_view_explain) ;
        webres = (ListView)findViewById(R.id.list_view_webexplain) ;
        SearchWord.setText(word);
        img = (ImageView)findViewById(R.id.img_save);
        handler = new Handler(new Result.GetResultCallBack());
        new youdao(word, handler).start();

        handler1 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        voc= (List<Vocabulary>)msg.obj;
                        for(int i = 0;i<voc.size();i++){
                            if(voc.get(i).getWord().equals(word)){
                                issave = true;
                                img.setImageResource(R.drawable.ic_favorite);
                                break;
                            }
                        }
                        if(!issave){
                            img.setImageResource(R.drawable.ic_favorite_border);
                        }
                        break;
                    case 1:
                        issave = false;
                        img.setImageResource(R.drawable.ic_favorite_border);


                        break;
                    case 2:
                        Toast.makeText(Result.this, "查询错误！", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };

        handleradd = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        break;
                    case 1:
                        Toast.makeText(Result.this, "插入成功", Toast.LENGTH_SHORT).show();
                        img.setImageResource(R.drawable.ic_favorite);
                        issave=true;
                        break;
                    case 2:
                        Toast.makeText(Result.this, "查询错误！", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };


        handlerdelete = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        Toast.makeText(Result.this, "已从生词本中删除！", Toast.LENGTH_SHORT).show();
                        img.setImageResource(R.drawable.ic_favorite_border);
                        issave=false;
                        break;
                    case 1:
                        Toast.makeText(Result.this, "删除失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(Result.this, "查询错误！", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };


        handlerhis = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                    //    Toast.makeText(Result.this, "插入失败！单词已存在", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:


                        break;
                    case 2:
                        Toast.makeText(Result.this, "插入历史记录失败！", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };

        HistoryDAO his = new HistoryDAOimpl(handlerhis);
        his.InsertHistory(username,word);

        VocabularyDAO vol = new VocabularyDAOimpl(handler1);
        vol.FindVocabulary(username);
    }

    public void back(View v) {

        this.finish();
    }
    public void edit(View v) {
        if(issave){

            VocabularyDAO vol1 = new VocabularyDAOimpl(handlerdelete);
            vol1.DeleteVocabulary(username,word);
        }else{
            VocabularyDAO vol = new VocabularyDAOimpl(handleradd);
            vol.InsertVocabulary(username,word);
        }



    }

    private class GetResultCallBack implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                List<String> exp;
                List<Web> web; ;
                explain = (Word)msg.obj;
                StringBuilder pronounce = new StringBuilder();
                Basic bs ;
                bs = explain.getBasic();
                if(bs.getUk_phonetic().isEmpty()||bs.getUs_phonetic().isEmpty()){
                }else {
                    pronounce.append(" 英式发音：|").append(bs.getUk_phonetic()).append("|").append("\r\n" ).append("美式发音：|").append(bs.getUs_phonetic()).append("|");
                    pronounciation.setText(pronounce);
                }
                exp = bs.getExplains();
                if (exp.size() > 0) {
                    ResultAdapter pla = new ResultAdapter(Result.this, exp);
                    expres.setAdapter(pla);
                    pla.notifyDataSetChanged();
                }
                web = explain.getWeb();
                if(web.size()>0)
                {
                    WebAdapter wba = new WebAdapter(Result.this,web);
                    webres.setAdapter(wba);
                    wba.notifyDataSetChanged();
                }
            }
            return true;
        }
    }
}
