package com.haonan.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import haonan.classify.R;


public class LoginFragment extends Fragment implements View.OnClickListener {

    private Button login;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        login = (Button)root.findViewById(R.id.loginBtn);
        login.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBtn:
                LoginClick();
                break;
//            case R.id.loginBtn:
//                RegistClick();
//                break;
        }
    }
    //登录事件
    public void LoginClick(){
        getFragmentManager().beginTransaction()
                .replace(R.id.activity_login, new MainFragment())
                .commit();
    }
    //注册事件
    public void RegistClick() {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_regist, new MainFragment())
                .commit();
    }
}
