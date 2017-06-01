package entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by 55303 on 2017/4/27.
 */

public class UserPlan extends BmobObject {
    String username;
    int StudyID;
    int scheme;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStudyID() {
        return StudyID;
    }

    public void setStudyID(int studyID) {
        StudyID = studyID;
    }

    public int getScheme() {
        return scheme;
    }

    public void setScheme(int scheme) {
        this.scheme = scheme;
    }
}
