package com.yebimom.android.yebimom.ui.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.yebimom.android.yebimom.ApplicationController;
import com.yebimom.android.yebimom.R;
import com.yebimom.android.yebimom.ui.CenterData;

import org.apache.http.conn.MultihomePlainSocketFactory;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 센터의 상세정보를 표시하는 액티비티
 */
public class DetailActivity extends ActionBarActivity {

    private final static String TAG = DetailActivity.class.getSimpleName();

    @InjectView(R.id.detailCenterImage) NetworkImageView detailCenterImage;
    @InjectView(R.id.detailCenterName) TextView centerName;
    @InjectView(R.id.detailAddress) TextView centerAddress;
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.detailContactHost) TextView centerContactHost;

    private CenterData centerData;
    private ImageLoader imageLoader = ApplicationController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Init ButterKnife
        ButterKnife.inject(this);

        // Set Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Load Center Data
        Intent intent = getIntent();
        centerData = (CenterData) intent.getSerializableExtra("centerData");

        // Set Center Content
        centerName.setText(centerData.getCenterName());
        centerAddress.setText(centerData.getCenterAddress());

        // Load Center Image from Web
        detailCenterImage.setImageUrl(centerData.getCenterImageUrl(), imageLoader);

        // Contact Host with phone number
        centerContactHost.setOnClickListener(listener -> {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+centerData.getCenterPhoneNumber()));
            startActivity(callIntent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, ""+item.getItemId());
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Log.d(TAG, "HOME BUTTON");
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
