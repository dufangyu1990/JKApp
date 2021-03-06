package com.example.jkapp.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.jkapp.R;
import com.example.jkapp.present.ActivityPresentImpl;
import com.example.jkapp.view.SystemSetView;

/**
 * Created by dufangyu on 2017/9/6.
 */

public class SystemSetActivity extends ActivityPresentImpl<SystemSetView>implements View.OnClickListener{
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back_img:
                finish();
                break;
        }
    }



    public  static void actionStart(Context context, String loginName)
    {
        Intent intent = new Intent(context,SystemSetActivity.class);
        intent.putExtra("loginName", loginName);
        context.startActivity(intent);
    }


}
