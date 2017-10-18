package com.haonan.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.hardware.display.DisplayManagerCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.haonan.entity.User;
import com.haonan.util.SaveUser;

import org.w3c.dom.Text;

import java.util.ArrayList;

import haonan.classify.LoginActivity;
import haonan.classify.MainActivity;
import haonan.classify.R;


public class LoginFragment extends Fragment implements  PopupWindow.OnDismissListener {

    public static final String TAG = "LoginFragment";
    private LinearLayout loginLL;   //登录内容容器
    private LinearLayout IdLL;      //下拉弹出窗口在次容器下方显示
    private Animation translate;    //位移动画
    private Dialog loginingDialog;  //正在登录的Dialog
    private EditText IdEt;          //ID编辑框
    private EditText pwdEt;         //密码编辑框
    private ImageView moreUser;     //显示更多用户图标
    private Button loginBtn;        //登录按钮
    private TextView registTV;       //注册tv
    private ImageView moreUserView; //弹出下拉弹出框的按钮
    private String Id;
    private String pwd;
    private ArrayList<User> users;  //用户列表
    private ListView IdListView;    //下拉弹出框显示的ListView对象
    private PopupWindow pop;        //下拉弹出框
    private MyAdapter adapter;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        InitView(root);
        loginLL.startAnimation(translate);
        //注册成功之后自动填充email和密码
        if(getArguments() != null){
            String name = getArguments().getString("email");
            String pwd = getArguments().getString("pwd");
            IdEt.setText(name);
            pwdEt.setText(pwd);
        }
        //历史用户查找
        users = SaveUser.getUserList(getActivity());
        if(users.size() > 0){
            IdEt.setText(users.get(0).getId());
            pwdEt.setText(users.get(0).getPwd());
        }
        LinearLayout parent = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.userifo_listview, null);
        IdListView = (ListView) parent.findViewById(R.id.list);
        parent.removeView(IdListView);
        //必须新建OnItemClickListener，不能直接使用this。因为fragment不支持
        IdListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IdEt.setText(users.get(position).getId());
                pwdEt.setText(users.get(position).getPwd());
                pop.dismiss();
            }
        });
        adapter = new MyAdapter(users);
        IdListView.setAdapter(adapter);
        return root;
    }



    class MyAdapter extends ArrayAdapter<User>{
        public MyAdapter(ArrayList<User> users) {
         super(getActivity(), 0, users);
        }

        public View getView(final int position, View convertView, ViewGroup parent){
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(
                        R.layout.login_listview, null);
            }
            TextView userIdText = (TextView) convertView
                    .findViewById(R.id.loginIdEt);
            userIdText.setText(getItem(position).getId());

            ImageView deleteUser = (ImageView) convertView
                    .findViewById(R.id.login_delete_user);

            deleteUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getItem(position).getId().equals(Id)) {
                        Id = "";
                        pwd = "";
                        IdEt.setText(Id);
                        pwdEt.setText(pwd);
                    }
                    users.remove(getItem(position));
                    adapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }

    private void setListener() {
        IdEt.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Id = s.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        pwdEt.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                pwd = s.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

    }

    //初始化各个控件
    public void InitView(View root) {
        IdEt = (EditText)root.findViewById(R.id.loginIdEt);
        pwdEt = (EditText)root.findViewById(R.id.loginPwdEt);
        moreUser = (ImageView)root.findViewById(R.id.loginMourUser);
        loginBtn = (Button)root.findViewById(R.id.loginBtn);
        moreUserView = (ImageView) root.findViewById(R.id.loginMourUser);
        registTV = (TextView)root.findViewById(R.id.loginRegist);
        translate = AnimationUtils.loadAnimation(getActivity(), R.anim.translate);
        loginLL = (LinearLayout) root.findViewById(R.id.LoginLLayout);
        IdLL = (LinearLayout) root.findViewById(R.id.userLLayout);

        initLoginingDialog(root);

        //登录事件添加
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowLoginingDialog();
                LoginClick();
            }
        });

        //下拉事件添加
        moreUserView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreUserClick();
            }
        });

        //注册事件添加
        registTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistClick();
            }
        });
    }

    //设置Dialog
    public void initLoginingDialog(View root) {
        loginingDialog = new Dialog(getActivity(), R.style.loginingDlg);
        loginingDialog.setContentView(R.layout.logining_dlg);

        //获取当前窗口属性，目的将Dialog置于中间
        Window window = loginingDialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        //获取屏幕尺寸
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int cxScreen = dm.widthPixels;
        int cyScreen = dm.heightPixels;

        int height = (int) getResources().getDimension(
                R.dimen.loginingdlg_height);
        int lrMargin = (int) getResources().getDimension(
                R.dimen.loginingdlg_lr_margin);
        int topMargin = (int) getResources().getDimension(
                R.dimen.loginingdlg_top_margin);
        params.y = (-(cyScreen - height) / 2) + topMargin;

        params.width = cxScreen;
        params.height = height;
        //点击Dialog外部任意区域关闭Dialog
        loginingDialog.setCanceledOnTouchOutside(true);
    }
    //设置下拉菜单
    public void InitPop() {
        int width = IdLL.getWidth() - 4;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;

        pop = new PopupWindow(IdListView, width, height, true);
        pop.setOnDismissListener(this);
        pop.setBackgroundDrawable(new ColorDrawable(0xffffffff));

    }

    //显示正在登录对话框
    private void ShowLoginingDialog() {
        if(loginingDialog != null){
            loginingDialog.show();
        }
    }
    //关闭正在登录的对话框
    private void CloseLoginingDialog() {
        if(loginingDialog != null && loginingDialog.isShowing()){
            loginingDialog.dismiss();
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        IdEt.setText(users.get(position).getId());
        pwdEt.setText(users.get(position).getPwd());
        pop.dismiss();
    }


    //登录事件
    public void LoginClick(){
//        getFragmentManager().beginTransaction()
//                .addToBackStack(null)
//                .replace(R.id.activity_login, new MainFragment())
//                .commit();
        startActivity(new Intent(getActivity(), MainActivity.class));
        String name = IdEt.getText().toString();
        String pwd = pwdEt.getText().toString();

        User user = new User(name,pwd);
        //需要从数据库查询用户名密码,返回结果正确则保存用户
        users.add(user);
        try {
            SaveUser.SaveUserList(getActivity(),users);
        } catch (Exception e) {
           Log.i(TAG,"用户列表保存出错");
        }
        CloseLoginingDialog();
    }
    //注册事件
    public void RegistClick() {
        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.activity_login, new RegistFragment())
                .commit();
    }

    @Override
    public void onDismiss() {
        moreUser.setImageResource(R.drawable.login_more_up);
    }
    //下拉事件
    public void MoreUserClick() {
        if(pop == null) {
            InitPop();
        }

        if(!pop.isShowing() && users.size() > 0){
            moreUser.setImageResource(R.drawable.login_more_down);
            pop.showAsDropDown(IdLL, 2, 1);
        }
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        try {
//            SaveUser.SaveUserList(getActivity(), users);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
