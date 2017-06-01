package Dao.impl;

import android.os.Handler;
import android.os.Message;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import Dao.StudyDAO;
import entity.Study;

/**
 * Created by 55303 on 2017/5/28.
 */

public class StudyDAOimpl implements StudyDAO {

    Handler handler;
    public StudyDAOimpl(Handler handler)
    {
        this.handler=handler;
    }
    public void GettxtName(int StudyID)
    {
        //返回1没有找到相应的学习，返回0找到了并在obj中传回，返回2查询错误
        BmobQuery<Study> query=new BmobQuery<Study>();
        query.addWhereEqualTo("StudyID",StudyID);

        query.findObjects(new FindListener<Study>() {
            @Override
            public void done(List<Study> object, BmobException e) {
                if(e==null){
                    System.out.println(object.size());
                    if(object.size()==0)
                    {
                        //表明没有study
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
                        message.obj = object.get(0);//这里的list就是查询出list
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

    @Override
    public void InsertStudy(final int StudyID, final String txtName)
    {
        //插入功能  返回1插入成功。返回0表示已经存在，返回2查询错误
        //先查是否有重复的username
        BmobQuery<Study> query=new BmobQuery<Study>();
        query.addWhereEqualTo("StudyID",StudyID);

        query.findObjects(new FindListener<Study>() {
            @Override
            public void done(List<Study> object, BmobException e) {
                if(e==null){
                    System.out.println(object.size());
                    if(object.size()==0)
                    {
                        //表明没有Study可以插入
                        final Study p2 = new Study();
                        p2.setStudyID(StudyID);
                        p2.setTxtName(txtName);
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
                        //以消息为载体
                        message.obj = object.get(0);//这里的list就是查询出list
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
