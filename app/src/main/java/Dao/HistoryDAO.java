package Dao;

/**
 * Created by 55303 on 2017/5/26.
 */

public interface HistoryDAO {
    //查询历史记录
    void FindHistory(String username);
    //插入历史记录
    void InsertHistory(String username,String word);
    //清空历史记录
    void DeleteAllHistory(String username);

}
