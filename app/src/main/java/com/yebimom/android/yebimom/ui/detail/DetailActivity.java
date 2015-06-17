package com.yebimom.android.yebimom.ui.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.yebimom.android.yebimom.ApplicationController;
import com.yebimom.android.yebimom.R;
import com.yebimom.android.yebimom.authentication.JwtAuthLoginToken;
import com.yebimom.android.yebimom.ui.CenterData;
import com.yebimom.android.yebimom.ui.ReviewData;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.yebimom.android.yebimom.utils.HttpConstants.CENTER_API_URL;

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
    @InjectView(R.id.reviewText) TextView reviewText;
    @InjectView(R.id.centerDetailAddress) TextView detailCenterAddress;
    @InjectView(R.id.centerDetailPhoneNumber) TextView detailCenterPhoneNumber;

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
        // TODO : deal nullException
        getSupportActionBar().setTitle("");
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

        // Set Detail Content
        detailCenterAddress.setText(centerData.getCenterAddress());
        detailCenterPhoneNumber.setText(centerData.getCenterPhoneNumber());

        // TODO : **네트워크 리뷰 데이터 볼리 큐에 다시 요청**
        // 요청하는동안 UI가 죽지 않게 프로그래스바 띄워둘것
        // 댓글을 모두 로드시켜두고 보여주는건 하나만 보여줌.
        // more reviews를 누르면 가져왔던 댓글들을 리스트로 뿌려줌.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                CENTER_API_URL+centerData.getCenterHashId()+"/reviews/visit/", response -> {
            // Deal response json array
            try {
                JSONObject jsonObject = response.getJSONObject(0);
                ReviewData item = new ReviewData();

                // set review content
                item.setCenterReview(jsonObject.getString("content"));

                runOnUiThread(() -> {
                    reviewText.setText(item.getCenterReview());
                });

            } catch (JSONException e){
                e.printStackTrace();
            }
        }, error -> {
            // Deal response error
            NetworkResponse networkResponse = error.networkResponse;
            if(networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED){
                Log.d(TAG, "Review : UNAUTHORIZED ");
            } else if(networkResponse != null && networkResponse.statusCode == HttpStatus.SC_FORBIDDEN){
                String jsonString = new String(networkResponse.data);
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    jsonString = jsonObject.getString("detail");
                    Log.d(TAG, jsonString);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
            VolleyLog.d(TAG, "ERROR : " + error.getMessage());
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("Authorization", "JWT " + JwtAuthLoginToken.getInstance().getLoginToken());
                return param;
            }

            // TODO : error! 왜 안되는지 확인
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
//                if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
//                    VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
//                    volleyError = error;
//                }
                return super.parseNetworkError(volleyError);
            }
        };

        ApplicationController.getInstance().addToRequestQueue(request);
    }


    // TODO : 동작하게 고쳐야함 아직 안돌아감
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
