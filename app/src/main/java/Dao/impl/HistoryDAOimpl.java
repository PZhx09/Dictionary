package Dao.impl;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.List;

import Dao.HistoryDAO;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import entity.History;

/**
 * Created by 55303 on 2017/5/26.
 */

public class HistoryDAOimpl implements HistoryDAO {
    //查询历史记录
    //查询历史记录
    Handler handler;

  public  HistoryDAOimpl(Handler handler)
    {
        this.handler=handler;
    }
    public void FindHistory(String username)
    {
        //消息传出1表明没有历史记录，0传出历史记录，2查询错误
        BmobQuery<History> query=new BmobQuery<History>();
        query.addWhereEqualTo("username",username);

        query.findObjects(new FindListener<History>() {
            @Override
            public void done(List<History> object, BmobException e) {
                if(e==null){
                    System.out.println(object.size());
                    if(object.size()==0)
                    {
                        //表明没有历史记录
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        //以消息为载体
                        //向handler发送消息
                        handler.sendMessage(message);
                    }
                    else{
                        Message message = handler.obtainMessage();
                        message.what = 0;
                        //以消息为载体
                        message.obj = object;//这里的list就是查询出list
                        //向handler发送消息，把找到的user放到了obj中
                        handler.sendMessage(message);
                    }
                }else{
                    Message message = handler.obtainMessage();
                    message.what = 2;

                    handler.sendMessage(message);
                }
            }

        });
    }
    //插入历史记录
    public void InsertHistory(final String username,final String word)
    {
        //插生词功能  返回1插入成功。返回0表示已经存在，返回2查询错误
        BmobQuery<History> query=new BmobQuery<History>();
        query.addWhereEqualTo("username",username);
        query.addWhereEqualTo("word",word);
        query.findObjects(new FindListener<History>() {
            @Override
            public void done(List<History> object, BmobException e) {
                if(e==null){
                    System.out.println(object.size());
                    if(object.size()==0)
                    {
                        //表明没有该条历史，可以进行插入
                        final History p2 = new History();
                        p2.setUsername(username);
                        p2.setWord(word);
                        p2.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId,BmobException e) {
                                if(e==null){
                                    Message message = handler.obtainMessage();
                                    message.what = 1;
                                    message.obj = p2;
                                    handler.sendMessage(message);
                                }else{
                                    Message message = handler.obtainMessage();
                                    message.what = 2;

                                    handler.sendMessage(message);
                                }
                            }
                        });
                    }
                    else{
                        Message message = handler.obtainMessage();
                        message.what = 0;
                        //向handler发送消息，找到的存在obj中
                        handler.sendMessage(message);
                    }
                }else{
                    Message message = handler.obtainMessage();
                    message.what = 2;

                    handler.sendMessage(message);
                }
            }

        });
    }
    //清空历史记录
    public void DeleteAllHistory(final String username)
    {
        //对历史记录进行清除，返回1删除成功，0无历史记录，2查询错误
        BmobQuery<History> query=new BmobQuery<History>();
        query.addWhereEqualTo("username",username);

        query.findObjects(new FindListener<History>() {
            @Override
            public void done(List<History> object, BmobException e) {
                if(e==null){
                    if(object.size()!=0)
                    {
                        //对历史记录进行逐条删除
                        for(int i=0;i<object.size();i++)
                        {
                            final History history=new History();
                            history.setObjectId(object.get(i).getObjectId());
                            history.delete(new UpdateListener() {

                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                        Log.i("bmob","成功");
                                    }else{
                                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                    }
                                }
                            });
                        }
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        //向handler发送消息，找到的存在obj中
                        handler.sendMessage(message);
                    }
                    else{
                        Message message = handler.obtainMessage();
                        message.what = 0;
                        //向handler发送消息，找到的存在obj中
                        handler.sendMessage(message);
                    }
                }else{
                    Message message = handler.obtainMessage();
                    message.what = 2;

                    handler.sendMessage(message);
                }
            }

        });
    }
}
