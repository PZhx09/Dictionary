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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import Adapter.HistoryAdapter;
import asynctask.GetHistory;
import entity.History;

/**
 * Created by Administrator on 2017-05-01.
 */
public class dictionary extends AppCompatActivity {
    private ListView list_view_history;
    private ImageButton dic_dictionary_button;
    private ImageButton dic_translate_button;
    private ImageButton dic_learn_button;
    private ImageButton dic_vocubulary_button;
    private ImageButton dic_myset_button;
    private ImageButton Btn_search;
    private Handler handler;
    private String username;
    private List<History> dataList;
    private EditText word;
    private BottomNavigationView  mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        list_view_history = (ListView) findViewById(R.id.list_view_history);
        word = (EditText)findViewById(R.id.edt_word);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");
        dataList = new ArrayList<History>();
        handler = new Handler(new GetHistoryCallBack());
        new GetHistory(username, handler).start();
        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        mNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if("词典".equals(item.getTitle().toString())){

                        }else if("翻译".equals(item.getTitle().toString())){
                            Intent intent = new Intent(dictionary.this, translate.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                        }else if("学习".equals(item.getTitle().toString())){
                            Intent intent = new Intent(dictionary.this, Study.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                        }else if("生词本".equals(item.getTitle().toString())){
                            Intent intent = new Intent(dictionary.this, vocabulary.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                        }
                        else if("我的".equals(item.getTitle().toString())){
                            Intent intent = new Intent(dictionary.this, myset.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                        }
                        return true;
                    }
                });
    }







        private class GetHistoryCallBack implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.arg1 == 0) {
               // System.out.println( msg.obj);
                dataList = (List<History>) msg.obj;
                if (dataList.size() > 0) {
                    HistoryAdapter pla = new HistoryAdapter(dictionary.this, dataList,username);
                    list_view_history.setAdapter(pla);
                    pla.notifyDataSetChanged();
                }
            }
            return true;
        }
    }
   /* public void search(View v) {

    }

    public void translate(View v) {

        Intent intent = new Intent(dictionary.this, translate.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void study(View v) {
        Intent intent = new Intent(dictionary.this, Study.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void vocabulary(View v) {
        Intent intent = new Intent(dictionary.this, vocabulary.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void myset(View v) {
        Intent intent = new Intent(dictionary.this, myset.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }*/

    public void find(View v) {
        String searchword = word.getText().toString();
        if(searchword.isEmpty()){

        }else {
            Intent intent = new Intent(dictionary.this, Result.class);
            intent.putExtra("word", searchword);
            intent.putExtra("username", username);
            startActivity(intent);
        }
    }
}
