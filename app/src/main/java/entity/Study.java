package entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by 55303 on 2017/4/27.
 */

public class Study extends BmobObject {

    int StudyID;
    String txtName;
    public int getStudyID() {
        return StudyID;
    }

    public void setStudyID(int studyID) {
        StudyID = studyID;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

}
