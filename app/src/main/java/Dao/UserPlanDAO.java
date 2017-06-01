package Dao;

/**
 * Created by 55303 on 2017/5/28.
 */

public interface UserPlanDAO {
    //获得用户当前的学习计划
    void GetPlan(String username);
    //修改当前用户学习计划(既可改进度，也可以改学习的计划)
    void ChangePlan(String username,int StudyID,int sheme);
    //建立学习计划(先通过GetPlan来查用户是否有计划，没有则进行插入，也可以设计为用户自行选择插入)
    void InsertPlan(String username,int StudyID,int sheme);
}
