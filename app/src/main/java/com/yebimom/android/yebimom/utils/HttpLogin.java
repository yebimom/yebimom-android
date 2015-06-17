package com.yebimom.android.yebimom.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.yebimom.android.yebimom.ApplicationController;

import java.util.HashMap;
import java.util.Map;

import static com.yebimom.android.yebimom.utils.HttpConstants.LOGIN_URL;

/**
 * com.yebimom.android.yebimom.utils Need Comment!
 */
public class HttpLogin extends StringRequest {

    private String username, password;
    private static String token;
    private static boolean isLogin = false;

    public HttpLogin(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public HttpLogin(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public static HttpLogin create(){
        ResponseListener responseListener = new ResponseListener();
        return new HttpLogin(Method.POST, LOGIN_URL, responseListener, responseListener);
    }

    static class ResponseListener implements Response.Listener<String>,
                                              Response.ErrorListener{

        @Override
        public void onResponse(String response) {
            token = response;
            isLogin = true;
            VolleyLog.v("Response:%n %s", response);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            VolleyLog.e("Error: ", error.getMessage());
            isLogin = false;
        }
    }

    @Override
    protected Map<String,String> getParams(){
        Map<String,String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);
        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> params = new HashMap<>();
        params.put("Content-Type","application/x-www-form-urlencoded");
        return params;
    }

    public HttpLogin addQueue(){
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.addToRequestQueue(this);
        return this;
    }

    public HttpLogin setUsername(String username) {
        this.username = username;
        return this;
    }

    public HttpLogin setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getToken() {
        return token;
    }

    public boolean isLogin() {
        return isLogin;
    }
}
