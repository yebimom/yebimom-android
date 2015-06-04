package com.yebimom.android.yebimom.ui.login;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.LoginButton;
import com.yebimom.android.yebimom.R;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * com.yebimom.android.yebimom.ui.login Need Comment!
 */
public class LoginMainFragment extends Fragment {

    private final static String TAG = LoginMainFragment.class.getSimpleName();

    // Facebook Session Status
    private Session.StatusCallback statusCallback =
            new SessionStatusCallback();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login_main, container, false);

        // Init ButterKnife
        ButterKnife.inject(this, rootView);

        // Facebook Login
        LoginButton FacebookAuthButton = (LoginButton) rootView.findViewById(R.id.facebookAuthButton);
        FacebookAuthButton.setReadPermissions(Arrays.asList("public_profile"));

        return rootView;
    }

    @OnClick(R.id.mainLoginButton)
    public void loginButton() {
        Log.d(TAG, "Login Click");
        ViewPager parentViewPager = (ViewPager) getActivity().findViewById(R.id.loginViewPager);
        parentViewPager.setCurrentItem(1, true);

    }


    @Override
    public void onResume() {
        super.onResume();

        // Logs facebook 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(getActivity().getApplicationContext());
    }

    @Override
    public void onPause() {
        super.onPause();

        // Logs facebook 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(getActivity().getApplicationContext());
    }


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

    private class SessionStatusCallback implements Session.StatusCallback {

        @Override
        public void call(Session session, SessionState state, Exception exception) {
            // Respond to session state changes, ex: updating the view
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Dismiss ButterKnife
        ButterKnife.reset(this);
    }
}
