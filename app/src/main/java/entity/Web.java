package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 55303 on 2017/4/28.
 */

public class Web {


    private List<String> value=new ArrayList<String>();
    private String key=new String();

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }





}
