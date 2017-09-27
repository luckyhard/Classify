package com.haonan.entity;

import android.util.Log;

import com.haonan.util.AES;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2017/9/27.
 */

public class User {
    private String id;
    private String pwd;
    private static final String masterPwd = "LUCKY";    //加密种子
    private static final String JSON_ID = "userId";
    private static final String JSON_PWD = "userPwd";
    private static final String TAG = "User";

    public User(String id, String pwd) {
        this.id = id;
        this.pwd = pwd;
    }

    public User(JSONObject json) throws Exception {
        if(json.has(JSON_ID)){
            String id = json.getString(JSON_ID);
            String pwd = json.getString(JSON_PWD);

            this.id = AES.decrypt(masterPwd, id);
            this.pwd = AES.decrypt(masterPwd, pwd);
        }
    }

    public JSONObject toJSON() throws Exception {
        String id = AES.encrypt(masterPwd, this.id);
        String pwd = AES.encrypt(masterPwd, this.pwd);

        Log.i(TAG, "用户名和密码为" + id + " " + pwd);

        JSONObject json = new JSONObject();
        json.put(JSON_ID, id);
        json.put(JSON_PWD, pwd);

        return json;
    }

    public String getId(){
        return this.id;
    }

    public String getPwd(){
        return this.pwd;
    }

}
