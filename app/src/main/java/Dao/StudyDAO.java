package Dao;

/**
 * Created by 55303 on 2017/5/28.
 */

public interface StudyDAO {
    //根据studyID得到txt文档的内容(其实没有必要，默认StudyID前面加一个s就是txt文档的名字)
    void GettxtName(int StudyID);
    //创建新的学习(也没有必要用因为学习在后台数据库里面插入即可)
    void InsertStudy(int StudyID,String txtName);
}
