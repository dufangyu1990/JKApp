package com.example.jkapp.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jkapp.R;
import com.example.jkapp.activity.MyApplication;
import com.example.jkapp.helper.EventHelper;

import static com.example.jkapp.utils.Constant.TCPDISLINK;
import static com.example.jkapp.utils.Constant.TCPLINK;


/**
 * Created by dufangyu on 2017/8/30.
 */

public class LoginView extends ViewImpl{


    private EditText accountText;
    private EditText passwordText;
    private TextView mTitleText,loginText,linkStateText;
    private String loginNameStr,pwdStr;
    private CheckBox SavePassCheck;
    private boolean p_bSavePassON = false;



    @Override
    public void initView() {

        mTitleText = findViewById(R.id.title_text);
        mTitleText.setText(mRootView.getContext().getString(R.string.app_name));
        accountText = findViewById(R.id.user_name_et);
        passwordText = findViewById(R.id.pass_word_et);
        loginText = findViewById(R.id.login_button);
        linkStateText = findViewById(R.id.netstateTv);
        SavePassCheck = findViewById(R.id.SavePassCheck);
        setListeners();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void bindEvent() {
        EventHelper.click(mPresent,loginText);
    }



    private void setListeners()
    {
        accountText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                checkInvalid();
            }
        });

        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                checkInvalid();
            }
        });
        SavePassCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 记住密码
                    p_bSavePassON = true;
                } else {
                    // 不记密码
                    p_bSavePassON = false;
                }
            }
        });
    }


    public void checkInvalid()
    {
        loginNameStr = accountText.getText().toString();
        pwdStr = passwordText.getText().toString();
        if(loginNameStr.length()>0 &&pwdStr.length()>0)
        {
            loginText.setEnabled(true);
        }else{
            loginText.setEnabled(false);
        }
    }


    public String getTextValue(int id)
    {

        if(id ==R.id.user_name_et)
        {
            return accountText.getText().toString().trim();
        }else if(id ==R.id.pass_word_et)
        {
            return passwordText.getText().toString().trim();
        }
        return "";
    }


    public void setNetState(int state,String str)
    {
        if(state ==TCPDISLINK)
        {
            accountText.setEnabled(false);
            passwordText.setEnabled(false);
            accountText.setTextColor(0x90000000);
            passwordText.setTextColor(0x90000000);
            loginText.setEnabled(false);

        }else if(state==TCPLINK)
        {
            accountText.setEnabled(true);
            passwordText.setEnabled(true);
            accountText.setTextColor(mRootView.getContext().getResources().getColor(R.color.text_color));
            passwordText.setTextColor(mRootView.getContext().getResources().getColor(R.color.text_color));
            checkInvalid();
        }
        linkStateText.setText(str);
    }


    /**
     * 保存用户名密码
     */
    public void saveAccountNdPwd()
    {
        if (p_bSavePassON) {
            // 记住密码
            MyApplication.getInstance().setStringPerference("UserName", accountText.getText().toString().trim());
            MyApplication.getInstance().setStringPerference("Password", passwordText.getText().toString().trim());
            MyApplication.getInstance().setBooleanPerference("isRemember", true);
            MyApplication.getInstance().setStringPerference("isFirst","NO");
        }else{
            MyApplication.getInstance().setBooleanPerference("isRemember", false);
            MyApplication.getInstance().setStringPerference("UserName", accountText.getText().toString().trim());
            MyApplication.getInstance().setStringPerference("Password", passwordText.getText().toString().trim());
        }
    }



    public void initPwdCheckBox()
    {

        String strShareUserName = MyApplication.getInstance().getStringPerference("UserName");
        String strSharePassword =MyApplication.getInstance().getStringPerference("Password");
        boolean isRemember = MyApplication.getInstance().getBooleanPerference("isRemember");
        if (isRemember) {
            accountText.setText(strShareUserName);
            passwordText.setText(strSharePassword);
            SavePassCheck.setChecked(true);
        }else{
            accountText.setText(strShareUserName);
        }
    }

}
