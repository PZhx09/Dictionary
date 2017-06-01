package Dao;

/**
 * Created by 55303 on 2017/5/26.
 */

public interface VocabularyDAO {
    //增
    void InsertVocabulary(String username,String word);
    //查
    void FindVocabulary(String username);
    //删
    void DeleteVocabulary(String username,String word);
    void ClearVocabulary(String username);
}
