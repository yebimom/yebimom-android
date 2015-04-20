package com.yebimom.android.yebimom.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.yebimom.android.yebimom.ApplicationController;
import com.yebimom.android.yebimom.R;
import com.yebimom.android.yebimom.ui.CenterData;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * com.yebimom.android.yebimom.ui.detail Need Comment!
 */
public class DetailActivity extends ActionBarActivity {

    @InjectView(R.id.detailCenterImage)
    NetworkImageView detailCenterImage;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

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

        // Load Center Image from Web
        detailCenterImage.setImageUrl(centerData.getCenterImageUrl(), imageLoader);

    }

}
