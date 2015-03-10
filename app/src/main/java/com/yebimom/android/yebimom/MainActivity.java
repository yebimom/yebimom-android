package com.yebimom.android.yebimom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.LoginButton;

import java.util.Arrays;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs facebook 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs facebook 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(getApplicationContext());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a fragment_main view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            LoginButton authButton = (LoginButton) rootView.findViewById(R.id.authButton);
            authButton.setReadPermissions(Arrays.asList("public_profile"));

            return rootView;
        }

        private Session.StatusCallback statusCallback =
                new SessionStatusCallback();

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
    }


}
