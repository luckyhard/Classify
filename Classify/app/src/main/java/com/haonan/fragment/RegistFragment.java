package com.haonan.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import haonan.classify.R;


public class RegistFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "RegistFragment";
    private EditText etName;
    private EditText etPwd;
    private EditText etConfirmPwd;
    private EditText etEmail;
    private EditText etAddress;
    private EditText etMajor;
    private AppCompatButton btnRegist;


    public RegistFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.
        View root = inflater.inflate(R.layout.fragment_regist, container, false);
        InitView(root);
        Check(root);

        return root;
    }
    //初始化各个控件
    public void InitView(View root) {
        //姓名edit
        etName = (EditText) root.findViewById(R.id.etName);
        //密码edit和
        etPwd = (EditText) root.findViewById(R.id.etPwd);
        //验证密码edit
        etConfirmPwd = (EditText) root.findViewById(R.id.etConfirmPwd);
        //email的edit
        etEmail = (EditText) root.findViewById(R.id.etEmail);
        //地址edit
        etAddress = (EditText) root.findViewById(R.id.etAddress);
        //专业的edit
        etMajor = (EditText) root.findViewById(R.id.etMajor);

        btnRegist = (AppCompatButton) root.findViewById(R.id.btnRegist);
        btnRegist.setOnClickListener(this);
    }

    public boolean Reg(String reg, String str){
        Pattern pattern = Pattern.compile(reg);
        Matcher mathcer = pattern.matcher(str);
        boolean rs = mathcer.matches();

        return rs;
    }

    public boolean Check(View root){
        final boolean[] check = {true};
        final TextInputLayout tilName = (TextInputLayout) root.findViewById(R.id.tilName);
        final TextInputLayout tilPwd = (TextInputLayout) root.findViewById(R.id.tilPwd);
        final TextInputLayout tilConfirPwd = (TextInputLayout) root.findViewById(R.id.tilConfirPwd);
        final TextInputLayout tilEmail = (TextInputLayout) root.findViewById(R.id.tilEmail);
        final TextInputLayout tilMajor = (TextInputLayout) root.findViewById(R.id.tilMajor);
        final TextInputLayout tilAddress = (TextInputLayout) root.findViewById(R.id.tilAddress);
        //姓名输入框添加监听事件，检查
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    tilName.setError(null);
                    tilName.setErrorEnabled(false);
                    Log.i(TAG,"Name获得焦点");
                }else{
                    String name = etName.getText().toString();
                    String reg = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";
                   boolean rs = Reg(reg, name);
                    if(!rs){
                        tilName.setError("5-16位字母、数字和下划线");
                        tilName.setErrorEnabled(true);
                        check[0] = false;
                    }else{check[0] = true;}
                }
            }
        });
        //密码输入框添加监听事件，检查
        etPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    tilPwd.setError(null);
                    tilPwd.setErrorEnabled(false);
                    Log.i(TAG,"Pwd获得焦点");
                }else{
                    String pwd = etPwd.getText().toString();
                    String reg = "^[a-zA-Z]\\w{5,17}$";
                    boolean rs = Reg(reg,pwd);
                    if(!rs){
                        tilPwd.setError("6-18位只包含字母、数字和下划线");
                        tilPwd.setErrorEnabled(true);
                        check[0] = false;
                    }else{check[0] = true;}
                }
            }
        });
        //确认密码输入框添加监听事件，检查
        etConfirmPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    tilConfirPwd.setError(null);
                    tilConfirPwd.setErrorEnabled(false);
                    Log.i(TAG,"ConfirPwd获得焦点");
                }else{
                    String pwd = etPwd.getText().toString();
                    String pwdC = etConfirmPwd.getText().toString();
                    if(!pwd.equals(pwdC)){
                        tilConfirPwd.setError("密码不一致");
                        tilConfirPwd.setErrorEnabled(true);
                        check[0] = false;
                    }else {check[0] = true;}
                    Log.i(TAG,"ConfirmPwd失去焦点");
                }
            }
        });
        //邮箱输入框添加监听事件，检查
        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    tilEmail.setError(null);
                    tilEmail.setErrorEnabled(false);
                    Log.i(TAG,"Email获得焦点");
                }else{
                    String email = etEmail.getText().toString();
                    String reg = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

                    boolean rs = Reg(reg,email);
                    if(!rs){
                        tilEmail.setError("邮箱格式不正确");
                        tilEmail.setErrorEnabled(true);
                        Log.i(TAG,email);
                        check[0] = false;
                    }else {check[0] = true;}
                }
            }
        });
        //专业输入框添加监听事件，检查
        etMajor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    tilMajor.setError(null);
                    tilMajor.setErrorEnabled(false);
                    Log.i(TAG,"Major获得焦点");
                }else{
                    String major = etMajor.getText().toString();
                    String reg = "^[\\u4E00-\\u9FA5A-Za-z0-9]+$";

                    boolean rs = Reg(reg,major);
                    if(!rs){
                        tilMajor.setError("包含非法字符");
                        tilMajor.setErrorEnabled(true);
                        Log.i(TAG,major);
                        check[0] = false;
                    }else {check[0] = true;}
                }
            }
        });
        //地址输入框添加监听事件，检查
        etAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    tilAddress.setError(null);
                    tilAddress.setErrorEnabled(false);
                    Log.i(TAG,"Address获得焦点");
                }else{
                    String address = etAddress.getText().toString();
                    String reg = "^[\\u4E00-\\u9FA5A-Za-z0-9]+$";

                    boolean rs = Reg(reg,address);
                    if(!rs){
                        tilAddress.setError("包含非法字符");
                        tilAddress.setErrorEnabled(true);
                        Log.i(TAG,address);
                        check[0] = false;
                    }else {check[0] = true;}
                }
            }
        });
        return check[0];
    }

    //注册事件
    public void ClickRegist(View root) {
        if(!Check(root)){
            RegistFailed();
        }else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.activity_login, new LoginFragment())
                    .commit();

            //将注册信息上传到数据库

            //将邮箱和密码传递到登录fragment
        }
    }

    public void RegistFailed() {
        Toast.makeText(getActivity().getBaseContext(),"注册信息不正确",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegist:
                ClickRegist(v);
                break;
        }
    }
}
