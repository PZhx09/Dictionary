package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 55303 on 2017/4/27
 */
//待定，后面可能会改
// 对有道词典进行封装json
    //Web类和Basic类都是对json结果进行封装  okn
public class Word {
    private List<String> translation=new ArrayList<String>();//对translation进行封装
    private Basic basic=new Basic();//basic封装完毕

    private String query=new String();

    private int errorCode;

    private List<Web> web=new ArrayList<Web>();

    public List<Web> getWeb() {
        return web;
    }

    public void setWeb(List<Web> web) {
        this.web = web;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }



}
