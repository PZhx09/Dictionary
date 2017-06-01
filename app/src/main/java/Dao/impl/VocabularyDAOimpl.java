package Dao.impl;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import Dao.VocabularyDAO;
import entity.Vocabulary;

/**
 * Created by 55303 on 2017/5/26.
 */

public class VocabularyDAOimpl implements VocabularyDAO {

    public Handler handler;
    public VocabularyDAOimpl(Handler handler)
    {
        this.handler=handler;
    }

    public void InsertVocabulary(final String username,final String word)
    {
        //插生词功能  返回1插入成功。返回0表示已经存在，返回2查询错误
        BmobQuery<Vocabulary> query=new BmobQuery<Vocabulary>();
        query.addWhereEqualTo("username",username);
        query.addWhereEqualTo("word",word);
        query.findObjects(new FindListener<Vocabulary>() {
            @Override
            public void done(List<Vocabulary> object, BmobException e) {
                if(e==null){
                    System.out.println(object.size());
                    if(object.size()==0)
                    {
                        //表明没有生词，可以进行插入
                        final Vocabulary p2 = new Vocabulary();
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
    public void FindVocabulary(String username)
    {
        //消息传出1表明没有生词，0传出生词，2查询错误
        BmobQuery<Vocabulary> query=new BmobQuery<Vocabulary>();
        query.addWhereEqualTo("username",username);

        query.findObjects(new FindListener<Vocabulary>() {
            @Override
            public void done(List<Vocabulary> object, BmobException e) {
                if(e==null){
                    System.out.println(object.size());
                    if(object.size()==0)
                    {
                        //表明没有生词
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
    public void DeleteVocabulary(String username,String word)
    {
        //消息传出1表明没有找到生词，0传出生词，2查询错误
        BmobQuery<Vocabulary> query=new BmobQuery<Vocabulary>();
        query.addWhereEqualTo("username",username);
        query.addWhereEqualTo("word",word);
        query.findObjects(new FindListener<Vocabulary>() {
            @Override
            public void done(List<Vocabulary> object, BmobException e) {
                if(e==null){
                    if(object.size()==0)
                    {
                        //表明没有生词
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        //以消息为载体
                        //向handler发送消息
                        handler.sendMessage(message);
                    }
                    else{
                        Vocabulary vocabulary = new Vocabulary();
                        vocabulary.setObjectId(object.get(0).getObjectId());
                        vocabulary.delete(new UpdateListener() {

                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Log.i("bmob","成功");
                                    Message message = handler.obtainMessage();
                                    message.what = 0;
                                    //向handler发送消息，把找到的user放到了obj中
                                    handler.sendMessage(message);
                                }else{
                                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                    Message message = handler.obtainMessage();
                                    message.what = 2;
                                    //向handler发送消息，把找到的user放到了obj中
                                    handler.sendMessage(message);
                                }
                            }
                        });

                    }
                }else{
                    Message message = handler.obtainMessage();
                    message.what = 2;

                    handler.sendMessage(message);
                }
            }

        });
    }
    public  void ClearVocabulary(String username)
    {
        //对生词进行清除，返回1删除成功，0无历史记录，2查询错误
        BmobQuery<Vocabulary> query=new BmobQuery<Vocabulary>();
        query.addWhereEqualTo("username",username);

        query.findObjects(new FindListener<Vocabulary>() {
            @Override
            public void done(List<Vocabulary> object, BmobException e) {
                if(e==null){
                    if(object.size()!=0)
                    {
                        //对生词进行逐条删除
                        for(int i=0;i<object.size();i++)
                        {
                            final Vocabulary vocabulary=new Vocabulary();
                            vocabulary.setObjectId(object.get(i).getObjectId());
                            vocabulary.delete(new UpdateListener() {

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
