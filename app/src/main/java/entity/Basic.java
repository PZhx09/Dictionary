package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 55303 on 2017/4/28.
 */

public class Basic {


    private List<String> explains=new ArrayList<String>();

    private String us_phonetic=new String();

    public String getUk_phonetic() {
        return uk_phonetic;
    }

    public void setUk_phonetic(String uk_phonetic) {
        this.uk_phonetic = uk_phonetic;
    }

    public String getUs_phonetic() {
        return us_phonetic;
    }

    public void setUs_phonetic(String us_phonetic) {
        this.us_phonetic = us_phonetic;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    private String phonetic=new String();
    private String uk_phonetic=new String();
    public List<String> getExplains() {
        return explains;
    }

    public void setExplains(List<String> explains) {
        this.explains = explains;
    }



}
