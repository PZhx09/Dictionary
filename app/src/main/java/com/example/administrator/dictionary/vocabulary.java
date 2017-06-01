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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapter.VocabularyAdapter;
import Dao.VocabularyDAO;
import Dao.impl.VocabularyDAOimpl;
import entity.Vocabulary;

public class vocabulary extends AppCompatActivity {
    private ListView list_view_vocubulary;
    private EditText edt;
    private Handler handler;
    private String username;
    List<Vocabulary> voc ;
    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);
        list_view_vocubulary=(ListView)findViewById(R.id.msg_list_view);
        edt = (EditText)findViewById(R.id.edt_word);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("username");
        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigationView.getMenu().getItem(0).setChecked(false);
        mNavigationView.getMenu().getItem(3).setChecked(true);
        mNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if("词典".equals(item.getTitle().toString())){
                            Intent intent = new Intent(vocabulary.this, dictionary.class);
                            intent.putExtra("username",username);
                            startActivity(intent);

                        }else if("翻译".equals(item.getTitle().toString())){
                            Intent intent = new Intent(vocabulary.this, translate.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                        }else if("学习".equals(item.getTitle().toString())){
                            Intent intent = new Intent(vocabulary.this, Study.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                        }else if("生词本".equals(item.getTitle().toString())){
                        }
                        else if("我的".equals(item.getTitle().toString())){
                            Intent intent = new Intent(vocabulary.this, myset.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                        }
                        return true;
                    }
                });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        voc = (List<Vocabulary>)msg.obj;
                        VocabularyAdapter vo = new VocabularyAdapter(vocabulary.this, voc,username);
                        list_view_vocubulary.setAdapter(vo);
                        vo.notifyDataSetChanged();
                        break;
                    case 1:
                        Toast.makeText(vocabulary.this, "暂无生词！", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(vocabulary.this, "查询错误！", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };

        VocabularyDAO vol = new VocabularyDAOimpl(handler);
        vol.FindVocabulary(username);

    }

    public void filter(View v){
        if(voc.size()==0)
        {
            Toast.makeText(vocabulary.this, "生词本为空！", Toast.LENGTH_SHORT).show();
        }else{
            String wo = edt.getText().toString();
            if(wo.isEmpty())
            {
                VocabularyAdapter vo = new VocabularyAdapter(vocabulary.this, voc,username);
                list_view_vocubulary.setAdapter(vo);
                vo.notifyDataSetChanged();
            }else{
                Vocabulary voca = new Vocabulary();
                voca.setUsername(username);
                voca.setWord(wo);

                boolean hasword = false;
             //   System.out.println(wo+username);
                for(int i = 0;i<voc.size();i++){
                   if(voc.get(i).getWord().equals(wo)){
                       hasword = true;
                       break;
                   }
                }
                if(hasword){
                    List<Vocabulary> list = new ArrayList<Vocabulary>();
                    list.add(voca);
                    VocabularyAdapter vo = new VocabularyAdapter(vocabulary.this, list,username);
                    list_view_vocubulary.setAdapter(vo);
                    vo.notifyDataSetChanged();

                }else{
                    Toast.makeText(vocabulary.this, "生词本中不存在！", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    public void search(View v)
    {
        Intent intent =new Intent(vocabulary.this,dictionary.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void translate(View v)
    {
        Intent intent =new Intent(vocabulary.this, translate.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void study(View v)
    {
        Intent intent =new Intent(vocabulary.this,Study.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }
    public void vocabulary(View v)
    {
    }

    public void myset(View v)
    {
        Intent intent =new Intent(vocabulary.this,myset.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }
}
