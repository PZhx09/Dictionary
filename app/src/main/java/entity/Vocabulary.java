package entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by 55303 on 2017/4/27.
 */

public class Vocabulary extends BmobObject {
    String username;
    String word;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
