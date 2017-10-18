package com.haonan.util;

import android.content.Context;
import android.util.Log;

import com.haonan.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/27.
 */

public class SaveUser {
    private static final String FILENAME = "userinfo.json";
    private static final String TAG = "SaveUser";

    //保存用户登录信息
    public static void SaveUserList(Context context, ArrayList<User> users) throws Exception{
        Log.i(TAG, "正在保存");
        Writer writer = null;
        OutputStream out = null;
        JSONArray jsonArray = new JSONArray();
        for(User user : users){
            jsonArray.put(user.toJSON());
        }

        try {
            out = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            Log.i(TAG, "json的值：" + jsonArray.toString());
            writer.write(jsonArray.toString());
        }catch (Exception e){
            Log.i(TAG,"保存错误");
        }
        finally {
            if (writer != null){
                writer.close();
            }
        }
    }

    //获取用户登录信息
    public static ArrayList<User> getUserList(Context context){
        FileInputStream in = null;
        ArrayList<User> users = new ArrayList<User>();
        try {
            in = context.openFileInput(FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            JSONArray jsonArray = new JSONArray();
            String line;
            while ((line = reader.readLine()) != null){
                jsonString.append(line);
            }
            Log.i(TAG, jsonString.toString());
            jsonArray = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < jsonArray.length(); i++) {
                User user = new User(jsonArray.getJSONObject(i));
                users.add(user);
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
