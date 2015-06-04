package com.yebimom.android.yebimom.ui.centers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.yebimom.android.yebimom.ApplicationController;
import com.yebimom.android.yebimom.R;
import com.yebimom.android.yebimom.ui.CenterData;
import com.yebimom.android.yebimom.ui.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.yebimom.android.yebimom.utils.HttpConstants.CENTER_API_URL;

/**
 * com.yebimom.android.yebimom.ui.centers Need Comment!
 */
public class CenterListActivity extends ActionBarActivity {

    private final static String TAG = CenterListActivity.class.getSimpleName();

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @InjectView(R.id.centerCardList) RecyclerView recyclerView;
    @InjectView(R.id.left_drawer) View naviagtionDrawer;
    @InjectView(R.id.left_drawer_loginlogout) View loginlogout;

    private ActionBarDrawerToggle dtToggle;
    private ProgressDialog mProgressDialog;
    private ArrayList<CenterData> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_list);

        // Set ButterKnife
        ButterKnife.inject(this);

        setNavigationToolbar();

        // Use recyclerView. This view need to set LinearLayoutManager.
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        // Set CardView List Adapter
        final CardViewAdapter adapter = new CardViewAdapter(data);
        recyclerView.setAdapter(adapter);

        // Show Progress Dialog to wait for getting request data.
        showProgressDialog();

        // Request json data from server.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, CENTER_API_URL, response -> {
            dismissProgressDialog();

            for (int i = 0; i < response.length(); i++) {
                try {
                    CenterData item = new CenterData();
                    JSONObject jsonObject = response.getJSONObject(i);

                    // set item name
                    item.setCenterName(jsonObject.getString("name"));

                    // set item address
                    item.setCenterAddress(jsonObject.getString("address"));

                    // set item phone number
                    item.setCenterPhoneNumber(jsonObject.getString("phone"));

                    // Todo : 서버에서 잘못보내주는 url을 처리중
                    // API 수정시 다시 고쳐야함
                    String imageUrl;
                    if ( jsonObject.getString("main_image_url").contains("http") ){
                        imageUrl = jsonObject.getString("main_image_url");
                    }else {
                        imageUrl = "https://yebimom.com";
                        imageUrl += jsonObject.getString("main_image_url");
                    }

                    // set imageUrl
                    item.setCenterImageUrl(imageUrl);

                    data.add(item);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            adapter.notifyDataSetChanged();
        }, error -> {
                    VolleyLog.d(TAG, "ERROR : " + error.getMessage());
                    dismissProgressDialog();

                });

        // Adding request to request queue
        ApplicationController.getInstance().addToRequestQueue(jsonArrayRequest);

    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading...");
        }
        mProgressDialog.show();
    }

    private void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    private void setNavigationToolbar() {
        // Set ActionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set Navigation Drawer
        dtToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(dtToggle);
    }

    @OnClick(R.id.left_drawer_loginlogout)
    public void loginlogout(){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        dtToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (dtToggle.onOptionsItemSelected(item)) {
//            return true;
//        }

        return dtToggle.onOptionsItemSelected(item)||super.onOptionsItemSelected(item);
    }
}
