package com.example.jkapp.biz;

/**
 * Created by dufangyu on 2017/9/6.
 */

public interface IModifyPwd {
    void modifyPwd(String loginName,String newPwd,ModifyPwdListener listener);
}
