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
import Dao.UserDAO;
import entity.User;


/**
 * Created by 55303 on 2017/4/28.
 */
//需要传入一个handler才能向外发送信息，注意消息处理函数
public class UserDAOimpl implements UserDAO {
    public User user =new User();
    public Handler handler;

    public UserDAOimpl(Handler handler)
    {
        this.handler=handler;
    }


    public void register(final String username, final String password, final String email)
    {
        //注册功能  返回1注册成功。返回0表示已经存在，返回2查询错误
        //先查是否有重复的username
        BmobQuery<User> query=new BmobQuery<User>();
        query.addWhereEqualTo("username",username);

        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null){
                    System.out.println(object.size());
                    if(object.size()==0)
                    {
                        //表明没有user可以进行注册
                        final User p2 = new User();
                        p2.setUsername(username);
                        p2.setPassword(password);
                        p2.setEmail(email);
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
    public void LogIn(String username,String password)
    {
        //登录，如果没有用户发送消息1，有则发送消息0，云数据库异常发送消息2
        BmobQuery<User> query=new BmobQuery<User>();
        query.addWhereEqualTo("username",username);
        query.addWhereEqualTo("password",password);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null){

                    if(object.size()==0)
                    {
                        //表明没有user
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
                        //向handler发送消息
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
    public void findUser(String username)
    {
        //根据findUser来查是否有指定User    消息2 表示查询错误    0表示有并返回user，1表示没有user
        BmobQuery<User> query=new BmobQuery<User>();
        query.addWhereEqualTo("username",username);
        query.setLimit(10);

        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null){
                    System.out.println(object.size());
                    if(object.size()==0)
                    {
                        //表明没有user
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
    public void UserInfoChange(String username, final String password, final String email)
    {
        //返回2查询错误u，返回1找不到对应的username，返回0修改成功
        //主要步骤还是用过查询username得到id然后根据id来对对应的用户信息进行修改
        BmobQuery<User> query=new BmobQuery<User>();
        query.addWhereEqualTo("username",username);

        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null){
                    System.out.println(object.size());
                    if(object.size()==0)
                    {
                        //表明没有user
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        //以消息为载体
                        //向handler发送消息
                        handler.sendMessage(message);
                    }
                    else{
                        final User user=new User();
                        user.setPassword(password);
                        user.setEmail(email);
                        user.update(object.get(0).getObjectId(), new UpdateListener() {

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
}
