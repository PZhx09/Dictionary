package com.example.administrator.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public class FlyPageActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fly_page);


        handler =new Handler(){
            @Override
            public void handleMessage(Message msg) {

                Intent intent =new Intent(FlyPageActivity.this,login.class);
                startActivity(intent);
                FlyPageActivity.this.finish();
                super.handleMessage(msg);
            }
        };
        new Thread(new DelayThread()).start();
    }



    public class DelayThread implements Runnable
    {

        @Override
        public void run() {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);
        }

    }

}
