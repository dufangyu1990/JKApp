package com.example.jkapp.biz;

/**
 * Created by dufangyu on 2017/9/5.
 */

public interface IMain {

    void sendLoginCommad(String loginName,String password);
    void getDepResult(String depCode,String loginName);

}
