package com.yebimom.android.yebimom.ui.Login;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.yebimom.android.yebimom.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginFragment extends Fragment{

    @InjectView(R.id.loginId) EditText mLoginId;
    @InjectView(R.id.loginPassword) EditText mPassword;
    @InjectView(R.id.loginButton) Button mLoginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Create Fragment View withd fragment_login.xml
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        // ButterKnife init
        ButterKnife.inject(this, rootView);


        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
