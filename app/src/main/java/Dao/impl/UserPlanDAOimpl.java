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
import Dao.UserPlanDAO;
import entity.UserPlan;

/**
 * Created by 55303 on 2017/5/28.
 */

public class UserPlanDAOimpl implements UserPlanDAO {

    Handler handler;
    public UserPlanDAOimpl(Handler handler)
    {
        this.handler=handler;
    }
    public void GetPlan(String username)
    {
        //获得用户的学习计划
        //根据GetPlan来查是否有指定学习计划    消息2 表示查询错误    0表示有并返回plan，1表示没有plan
        BmobQuery<UserPlan> query=new BmobQuery<UserPlan>();
        query.addWhereEqualTo("username",username);

        query.findObjects(new FindListener<UserPlan>() {
            @Override
            public void done(List<UserPlan> object, BmobException e) {
                if(e==null){
                    System.out.println(object.size());
                    if(object.size()==0)
                    {
                        //表明没有plan
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
                        //向handler发送消息，把找到的plan放到了obj中
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

    public void ChangePlan(String username, final int StudyID, final int sheme)
    {
        //返回2查询错误u，返回1找不到对应的username，返回0修改成功
        //主要步骤还是用过查询username得到id然后根据id来对对应的学习计划进行修改
        BmobQuery<UserPlan> query=new BmobQuery<UserPlan>();
        query.addWhereEqualTo("username",username);

        query.findObjects(new FindListener<UserPlan>() {
            @Override
            public void done(List<UserPlan> object, BmobException e) {
                if(e==null){
                    System.out.println(object.size());
                    if(object.size()==0)
                    {
                        //表明没有plan
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        //以消息为载体
                        //向handler发送消息
                        handler.sendMessage(message);
                    }
                    else{
                        final UserPlan userplan=new UserPlan();
                        userplan.setStudyID(StudyID);
                        userplan.setScheme(sheme);

                        userplan.update(object.get(0).getObjectId(), new UpdateListener() {

                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Log.i("bmob","更新成功");
                                    Message message = handler.obtainMessage();
                                    message.what = 0;
                                    //以消息为载体
                                    //向handler发送消息，把找到的user放到了obj中
                                    handler.sendMessage(message);
                                }else{
                                    Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                                    Message message = handler.obtainMessage();
                                    message.what = 2;

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

    public void InsertPlan(final String username, int StudyID, int sheme)
    {
        //用于新用户的学习计划创建(先使用GetPlan 确定用户没有学习计划了)
        //返回1插入成功，返回新计划，2表示查询错误，返回0表明用户已经有相应的计划
        BmobQuery<UserPlan> query=new BmobQuery<UserPlan>();
        query.addWhereEqualTo("username",username);

        query.findObjects(new FindListener<UserPlan>() {
            @Override
            public void done(List<UserPlan> object, BmobException e) {
                if(e==null){
                    System.out.println(object.size());
                    if(object.size()==0)
                    {
                        //表明没有plan,可以进行插入
                        final UserPlan p2 = new UserPlan();
                        p2.setUsername(username);
                        p2.setStudyID(1);
                        p2.setScheme(0);
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
