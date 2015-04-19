package com.yebimom.android.yebimom.ui.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.yebimom.android.yebimom.R;
import com.yebimom.android.yebimom.authentication.JwtAuthLoginToken;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginFragment extends Fragment {

    private final static String TAG = LoginFragment.class.getSimpleName();

    @InjectView(R.id.loginId) EditText mLoginId;
    @InjectView(R.id.loginPassword) EditText mPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Create fragment view with fragment_login.xml
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        // ButterKnife init
        ButterKnife.inject(this, rootView);

        ActionBar actionbar = ((ActionBarActivity)getActivity()).getSupportActionBar();

        if(actionbar != null){
            actionbar.show();
        }

        return rootView;
    }

    @OnClick(R.id.loginButton)
    public void loginButton(Button button){
        String loginId = mLoginId.getText().toString();
        String loginPassword = mPassword.getText().toString();

        JwtAuthLoginToken jwtAuthLoginToken = JwtAuthLoginToken.getInstance();
        jwtAuthLoginToken.createLoginToken(loginId, loginPassword);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
