package com.example.jkapp.biz;

import com.example.jkapp.CallBack.DataCallBackImp;
import com.example.jkapp.socketUtils.TcpConnectUtil;

/**
 * Created by dufangyu on 2017/9/7.
 */

public class AdvBiz implements IAdv{


    private AdvListener listener;
    public AdvBiz(AdvListener listener)
    {
        this.listener =listener;
        TcpConnectUtil.getTcpInstance().setDataCallBack(serverCallBack);
    }

    @Override
    public void getAdvsFromServer(int searchFlag,String searchValue,int curIndex,int pageNum,String loginName) {

        TcpConnectUtil.getTcpInstance().IntiTemp();
        TcpConnectUtil.getTcpInstance().ClintSendBcCommData(1107, "58", searchFlag+"", searchValue, "1", curIndex+"", pageNum+"", "", "", "", "",loginName, "", "", "", "", "", "", "");

    }



    private DataCallBackImp serverCallBack = new DataCallBackImp()
    {

        @Override
        public void onReceiveServerResult(int intDataType, String strDataType, String strSetSN, String strSetSN1, String strAlmComType, String strParam1, String strParam2, String strParam3) {

            if(intDataType==1106||intDataType==1107)
            {
                if(strDataType.equals("58"))
                {
                    listener.getSocketResult(TcpConnectUtil.p_strTempList,TcpConnectUtil.p_intTempCount);
                }
            }


        }


    };

}
