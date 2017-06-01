package asynctask;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import entity.History;

/**
 * Created by a on 2017/5/19.
 */


public class GetHistory extends Thread{
    String username;
    Handler handler;
    Message message;
    public GetHistory(String username, Handler handler){
        super();
        this.username = username;
        this.handler = handler;
        this.message = this.handler.obtainMessage();
    }

    public void run(){
        BmobQuery<History> query = new BmobQuery<History>();
        query.addWhereEqualTo("username", username);

        query.findObjects(new FindListener<History>() {
            @Override
            public void done(List<History> object, BmobException e) {

                if(e==null){
                    //查询成功且有数据
                    message.arg1 = 0;
                    message.obj = object;
                    handler.sendMessage(message);
                    Log.i("bmob","成功，有数据:" + object.size());
                }
                //查询失败
                else {
                    message.arg1 = 1;
                    handler.sendMessage(message);
                    Log.i("bmob","失败有数据:" + e.getMessage()+e.getErrorCode());
                }
            }
        });
    }
}
