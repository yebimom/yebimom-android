package com.yebimom.android.yebimom.authentication;

import android.support.annotation.Nullable;
import android.util.Log;

import com.yebimom.android.yebimom.utils.HttpLogin;

/**
 * JwtAuthLoginToken class used to singleton pattern
 */
public class JwtAuthLoginToken {

    private volatile static JwtAuthLoginToken instance;

    private final static String TAG = JwtAuthLoginToken.class.getSimpleName();

    private String loginJson = null;
    private String loginToken = null;

    private JwtAuthLoginToken() {

    }

    @Nullable
    public String createLoginToken(String id, String password){

        HttpLogin httpLogin = HttpLogin.create()
                                      .setUsername(id)
                                      .setPassword(password)
                                      .addQueue();

        if(httpLogin.isLogin()){
            loginToken = httpLogin.getToken();
            Log.d(TAG, loginToken);
        }else{
            Log.d(TAG, "Login Error");
        }

        return loginToken;
    }

    public static JwtAuthLoginToken getInstance() {
        if (instance == null){
            synchronized(JwtAuthLoginToken.class) {
                if (instance == null) {
                    instance = new JwtAuthLoginToken();
                }
            }
        }
        return instance;
    }

    public String getLoginJson() {
        return loginJson;
    }

    public void setLoginJson(String loginJson) {
        this.loginJson = loginJson;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }
}
