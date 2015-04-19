package com.yebimom.android.yebimom.ui.centers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.yebimom.android.yebimom.ApplicationController;
import com.yebimom.android.yebimom.R;
import com.yebimom.android.yebimom.ui.CenterData;
import com.yebimom.android.yebimom.ui.detail.DetailActivity;

import java.util.ArrayList;

/**
 * com.yebimom.android.yebimom.ui.centers Need Comment!
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private ArrayList<CenterData> data;
    private ImageLoader mImageLoader = ApplicationController.getInstance().getImageLoader();

    public CardViewAdapter(ArrayList<CenterData> data) {
        this.data = data;
    }

    public void addData(CenterData newData) {
        data.add(newData);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.center_list_cardview, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (mImageLoader == null) {
            mImageLoader = ApplicationController.getInstance().getImageLoader();
        }

        CenterData centerData = data.get(position);
        holder.centerName.setText(centerData.getCenterName());
        holder.centerImage.setImageUrl(centerData.getCenterImageUrl(), mImageLoader);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = ApplicationController.getInstance().getApplicationContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("centerData", data.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView centerName;
        protected NetworkImageView centerImage;
        protected View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            centerName = (TextView) itemView.findViewById(R.id.cardCenterName);
            centerImage = (NetworkImageView) itemView.findViewById(R.id.centerImage);
        }

    }

}
