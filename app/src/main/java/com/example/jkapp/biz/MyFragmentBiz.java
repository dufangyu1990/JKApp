package com.example.jkapp.biz;

import com.example.jkapp.manager.DataManager;
import com.example.jkapp.socketUtils.TcpConnectUtil;

/**
 * Created by dufangyu on 2017/9/6.
 */

public class MyFragmentBiz implements IMyFragment{



    @Override
    public void exitApp() {
        //发送注销登录指令给服务器
        TcpConnectUtil.getTcpInstance().IntiTemp();
        TcpConnectUtil.getTcpInstance().ClintSendBcCommData(1105, "0004", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
        DataManager.getManagerInstance().resetData();

    }

}
