package com.example.jkapp.utils;


import com.example.jkapp.activity.MyApplication;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.Part;
import cn.finalteam.okhttpfinal.RequestParams;
import cn.finalteam.okhttpfinal.StringHttpRequestCallback;


/**
 * Created by dufangyu on 2017/6/13.
 * http请求类
 */

public class NetWork {

    public static NetWork instance = new NetWork();

    private List<Part> requestParams = new ArrayList<Part>();

    public static NetWork getInstance()
    {
        return instance;
    }


    public void setRequestParam(String key, String value)
    {
        Part part = new Part(key, value);
        requestParams.add(part);
    }


    public void doGetNetWork(HttpCycleContext context, final HttpCallBack callBack, String url)
    {



        RequestParams params = new RequestParams(context);
        for(int i =0;i<requestParams.size();i++)
        {
            params.addFormDataPart(requestParams.get(i).getKey(),requestParams.get(i).getValue());
        }



        params.addHeader("Authorization", MyApplication.getInstance().getStringPerference("code"));
        HttpRequest.get(url, params, new StringHttpRequestCallback() {
            @Override
            protected void onSuccess(String s) {
                super.onSuccess(s);
                requestParams.clear();
                callBack.getHttpResult(s);

            }

            @Override
            public void onFailure(int errorCode, String msg) {
                LogUtil.d("dfy","onFailure errorCode ="+errorCode+",msg = "+msg);
                super.onFailure(errorCode, msg);
                requestParams.clear();
                callBack.getHttpErrorResult(errorCode, msg);
            }
        });
    }




    public void doPostNetWork(HttpCycleContext context, final HttpCallBack callBack, String url, String action)
    {
        RequestParams params = new RequestParams(context);
        for(int i =0;i<requestParams.size();i++)
        {
            params.addFormDataPart(requestParams.get(i).getKey(),requestParams.get(i).getValue());
        }



        HttpRequest.post(url, params, new StringHttpRequestCallback() {
            @Override
            protected void onSuccess(String s) {
                super.onSuccess(s);
                requestParams.clear();
                callBack.getHttpResult(s);



            }

            @Override
            public void onFailure(int errorCode, String msg) {
                LogUtil.d("dfy","onFailure errorCode ="+errorCode+",msg = "+msg);
                super.onFailure(errorCode, msg);
                requestParams.clear();
                callBack.getHttpErrorResult(errorCode, msg);
            }
        });
    }




}
