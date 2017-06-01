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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asynctask.baidu;
import asynctask.youdao;
import entity.Word;

public class translate extends AppCompatActivity {
    private ImageButton  tra_dictionary_button;
    private ImageButton  tra_translate_button;
    private ImageButton  tra_learn_button;
    private ImageButton  tra_vocubulary_button;
    private ImageButton  tra_myset_button;
    private TextView Txtresult;
    private EditText edt;
    private Spinner spi;
    private Handler handler;
    private Handler handler1;
    private Word word;
    private String username;
    private String[] dictName = {"有道", "百度"};
    private String dict = "有道";
    private List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
    private BottomNavigationView mNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        Txtresult = (TextView)  findViewById(R.id.resulttext);
        edt = (EditText) findViewById(R.id.edt_input);
        spi = (Spinner) findViewById(R.id.spi_dict);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");
        fillDataList();
        SimpleAdapter adapter = new SimpleAdapter(this, dataList, R.layout.dict_select,
                new String[]{"DictName"},
                new int[]{ R.id.dictname});
        spi.setAdapter(adapter);
        spi.setOnItemSelectedListener(new OnItemSelectedListenerImpl());


        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigationView.getMenu().getItem(0).setChecked(false);
        mNavigationView.getMenu().getItem(1).setChecked(true);
        mNavigationView.setOnNavigationItemSelectedListener(

                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        if("词典".equals(item.getTitle().toString())){
                            Intent intent = new Intent(translate.this, dictionary.class);
                            intent.putExtra("username",username);
                            startActivity(intent);

                        }else if("翻译".equals(item.getTitle().toString())){
                        }else if("学习".equals(item.getTitle().toString())){
                            Intent intent = new Intent(translate.this, Study.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                        }else if("生词本".equals(item.getTitle().toString())){
                            Intent intent = new Intent(translate.this, vocabulary.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                        }
                        else if("我的".equals(item.getTitle().toString())){
                            Intent intent = new Intent(translate.this, myset.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                        }
                        return true;
                    }
                });




    }

    private class OnItemSelectedListenerImpl implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);

            dict  = (String) map.get("DictName");

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    private void fillDataList(){

        for(int i=0; i<dictName.length; i++){

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("DictName", dictName[i]);

            dataList.add(map);
        }
    }


    private class GetResultCallBack implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                List<String> translation=new ArrayList<String>();
                // System.out.println( msg.obj);
                word = (Word)msg.obj;
                translation = word.getTranslation();
                //String test =  bs.getUk_phonetic();
                StringBuilder xxx = new StringBuilder();
                for(int i = 0;i<translation.size();i++)
                    xxx.append(translation.get(i)).append(";");
                Txtresult.setText(xxx);
            }
            return true;
        }
    }
    public void delete(View v)
    {
        edt.setText("");

    }

    private class GetResultBack implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                Txtresult.setText(msg.obj.toString());
            }
            return true;
        }
    }

    public void dotran(View v)
    {
        String content = edt.getText().toString();
        if(content.equals("")){
            Toast.makeText(translate.this, "未输入内容！", Toast.LENGTH_SHORT).show();
        }else {
            if(dict.equals("有道"))
            {handler = new Handler(new GetResultCallBack());
            new youdao(content, handler).start();}
            else {
                handler1 = new Handler(new GetResultBack());
                new baidu(content, handler1).start();

            }
        }
    }


    public void search(View v)
    {
        Intent intent =new Intent(translate.this,dictionary.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void translate(View v)
    {
    }

    public void study(View v)
    {
        Intent intent =new Intent(translate.this,Study.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }
    public void vocabulary(View v)
    {
        Intent intent =new Intent(translate.this,vocabulary.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void myset(View v)
    {
        Intent intent =new Intent(translate.this,myset.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }
}
