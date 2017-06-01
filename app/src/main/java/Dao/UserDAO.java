package Dao;

/**
 * Created by 55303 on 2017/4/28.
 */

public interface UserDAO {
    //注册
    void register(String username,String password,String email);
    //登录
    void LogIn(String username,String password);
    //寻找
    void findUser(String username);
    //修改用户信息
    void  UserInfoChange(String username,String password,String email);


}
