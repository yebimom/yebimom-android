package com.yebimom.android.yebimom.ui.login;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Button;

import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.LoginButton;
import com.yebimom.android.yebimom.R;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends ActionBarActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    // Facebook Session Status
    private Session.StatusCallback statusCallback =
            new SessionStatusCallback();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        ButterKnife.inject(this);

        // Facebook Login
        LoginButton FacebookAuthButton = (LoginButton) findViewById(R.id.facebookAuthButton);
        FacebookAuthButton.setReadPermissions(Arrays.asList("public_profile"));
    }

    @OnClick(R.id.mainLoginButton)
    public void loginButton(Button button) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, 0, 0)
                .add(R.id.loginMainFragment, new LoginFragment())
                .addToBackStack(null)
                .commit();
        Log.d(TAG, "commit");
    }


    @Override
    public void onResume() {
        super.onResume();

        // Logs facebook 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(getApplicationContext());
    }

    @Override
    public void onPause() {
        super.onPause();

        // Logs facebook 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(getApplicationContext());
    }

    /*
    private void onClickLogin() {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this)
                    .setPermissions(Arrays.asList("public_profile"))
                    .setCallback(statusCallback));
        } else {
            Session.openActiveSession(getActivity(), this, true, statusCallback);
        }
    }
    */
    private class SessionStatusCallback implements Session.StatusCallback {

        @Override
        public void call(Session session, SessionState state, Exception exception) {
            // Respond to session state changes, ex: updating the view
        }
    }
}