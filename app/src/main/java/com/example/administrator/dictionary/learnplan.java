package com.example.administrator.dictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class learnplan extends Activity {
    String username;
    String studyid;
    int scheme;
    BufferedReader buffertxt;
    TreeMap<String,Integer> tmp = new TreeMap<String, Integer>();
    private List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
    ListView lst;
    ProgressBar pro;
    TextView wordpro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learnplan);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");
        studyid = intent.getStringExtra("studyid");
        scheme = Integer.parseInt(intent.getStringExtra("scheme"));
        lst= (ListView)findViewById(R.id.msg_list_view);
        int s = Integer.parseInt(studyid);
        pro = (ProgressBar)findViewById(R.id.wordprogress) ;
        wordpro = (TextView)findViewById(R.id.txtprocess);
        String p = "已完成："+ scheme;
        wordpro.setText(p);

        pro.setProgress(scheme);
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

           }
           buffertxt.close();
       }catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
        fillDataList();
        SimpleAdapter adapter = new SimpleAdapter(this, dataList, R.layout.learn_res,
                new String[]{"word", "status"},
                new int[]{R.id.txtword, R.id.txtres});

        //步骤2---绑定适配器
        lst.setAdapter(adapter);
        lst.setOnItemClickListener(new OnItemClickListenerImpl());

    }

    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(final AdapterView<?> parent, View view,final int position, long id) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);

                    String word = (String) map.get("word");
                    System.out.println(word);
                    Intent intent = new Intent(learnplan.this, Result.class);
                    intent.putExtra("username",username);
                    intent.putExtra("word",word);
                    startActivity(intent);
                }
            });

        }
    }

    private void fillDataList(){

        Set<Map.Entry<String,Integer>> entrySet = tmp.entrySet();
        Iterator<Map.Entry<String,Integer>> it = entrySet.iterator();
        while (it.hasNext()){
            Map.Entry<String,Integer> em= it.next();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("word", em.getKey());
            map.put("status","查看");


            dataList.add(map);
        }
    }
    public  void back(View v){
        this.finish();
    }


}
