package com.yebimom.android.yebimom.ui.login;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yebimom.android.yebimom.R;
import com.yebimom.android.yebimom.authentication.JwtAuthLoginToken;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginFragment extends Fragment {

    private final static String TAG = LoginFragment.class.getSimpleName();

    @InjectView(R.id.loginId) EditText mLoginId;
    @InjectView(R.id.loginPassword) EditText mPassword;

    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Create fragment view with fragment_login.xml
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        // ButterKnife init
        ButterKnife.inject(this, rootView);

        return rootView;
    }

    // TODO : 프로그래스 띄워서 기다리고 성공인지 실패인지 받아낼것
    @OnClick(R.id.loginButton)
    public void loginButton(Button button) {

        // show progress
        showProgressDialog();

        String loginId = mLoginId.getText().toString();
        String loginPassword = mPassword.getText().toString();

        JwtAuthLoginToken jwtAuthLoginToken = JwtAuthLoginToken.getInstance();
        jwtAuthLoginToken.createLoginToken(loginId, loginPassword);
        Log.d(TAG, "Token : " + jwtAuthLoginToken.getLoginToken());

        // TODO : Wait 5 Sec, changed to handler
        getActivity().runOnUiThread(() -> {
            long local = Calendar.getInstance().getTimeInMillis();
            long now;
            while (true) {
                now = Calendar.getInstance().getTimeInMillis();
                if (now - local < 3000) {
                    if (jwtAuthLoginToken.getLoginToken() == null) {
                        continue;
                    } else {
                        dismissProgressDialog();
                        getActivity().finish();
                        break;
                    }
                } else {
                    Log.d(TAG, "login fail");
                    dismissProgressDialog();
                    getActivity().finish();
                    break;
                }
            }
        });

        // TODO : refactoring
    }

    class WaitLoginThread implements Runnable{

        @Override
        public void run() {
            long local = Calendar.getInstance().getTimeInMillis();
            long now;
            while(true){
                now = Calendar.getInstance().getTimeInMillis();
                if( now - local < 3000 ){
                    if(JwtAuthLoginToken.getInstance().getLoginToken() == null){
                        continue;
                    } else {

                        break;
                    }
                } else{
                    Log.d(TAG, "login fail");
                    break;
                }
            }
        }
    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            Log.d(TAG, getActivity().getApplicationContext().toString());
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Wait login");
        }
        mProgressDialog.show();
    }

    private void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
