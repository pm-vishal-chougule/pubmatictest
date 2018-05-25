package com.example.javven.pubmatictest;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.pubmatic.sdk.banner.PMBannerAdView;
import com.pubmatic.sdk.banner.pubmatic.PMBannerAdRequest;
import com.pubmatic.sdk.common.PMAdSize;
import com.pubmatic.sdk.common.PMError;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_VEHICLE = 1;
    private static final int TYPE_BANNER = 2;
    private final int AD_REFRESH_TIME_IN_SEC = 0;
    private int[] adIndexArray = {1, 9};
    private List<ListItem> vehicles = new ArrayList<>();
    private String TAG = "ListAdapter";
    private Context mContext;
    public ListAdapter(Context context) {
        mContext = context;
    }
    public void addAll(Collection<ListItem> vehicles) {
        this.vehicles.addAll(vehicles);
        // Insert dummy Ad place holder for ad indexes
        for (int adIndex = 0; adIndex < adIndexArray.length; adIndex++) {
            this.vehicles.add(adIndexArray[adIndex], new Ad());
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreate viewType=" + viewType);
        if (viewType == TYPE_VEHICLE) {
            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_vh, parent, false);
            return new VehicleHolder(rootView);
        } else if (viewType == TYPE_BANNER) {
            Log.d(TAG, "OnCreate AD View is Type 2");
            View adViewParent = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.banner_vh, parent, false);
            PubMaticViewHolder adViewHolder = new PubMaticViewHolder((FrameLayout) adViewParent);
            adViewHolder.setIsRecyclable(false);
            // Return PubMatic view holder
            return adViewHolder;
        } else {
            throw new RuntimeException("Unknown item type.");
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBind() position" + position);
        if (holder instanceof VehicleHolder) {
            ((VehicleHolder) holder).bind((Vehicle) vehicles.get(position));
        } else if (holder instanceof PubMaticViewHolder) {
            PubMaticViewHolder adViewHolder = (PubMaticViewHolder) holder;
            Ad ad = ((Ad) vehicles.get(position));
            PMBannerAdView bannerAdView = ad.getAdView();
            // Create Banner if user is first time viewing the ad at this position
            if (bannerAdView == null) {
                bannerAdView = new PMBannerAdView(mContext);
                ad.setAdView(bannerAdView);
                loadPMAd(adViewHolder, bannerAdView);
            }
            // Reuse the loaded ad and bind it in view. It will show the loaded ad instead of new ad
            else {
                adViewHolder.bind(bannerAdView);
            }
        }
    }
    @Override
    public int getItemCount() {
        return vehicles.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (vehicles.get(position) instanceof Vehicle)
            return TYPE_VEHICLE;
        else
            return TYPE_BANNER;
    }
    private void loadPMAd(final PubMaticViewHolder adViewHolder, PMBannerAdView bannerAdView) {
        if (adViewHolder != null && bannerAdView != null) {
            bannerAdView.setUpdateInterval(AD_REFRESH_TIME_IN_SEC);
            bannerAdView.setRequestListener(new PMBannerAdView.BannerAdViewDelegate.RequestListener() {
                @Override
                public void onFailedToReceiveAd(PMBannerAdView pmBannerAdView, PMError pmError) {
                    // Implement your own logic to handle failure of PubMatic Ad request
                }
                @Override
                public void onReceivedAd(PMBannerAdView pmBannerAdView) {
                    adViewHolder.bind(pmBannerAdView);
                }
            });
            // Create an Ad request and load the PubMatic Banner Ad.
            PMBannerAdRequest adRequest = PMBannerAdRequest.createPMBannerAdRequest("156453", "219778", "1178234");
            adRequest.setAdSize(PMAdSize.PMBANNER_SIZE_300x250);
            bannerAdView.loadRequest(adRequest);
        }
    }
    private class VehicleHolder extends RecyclerView.ViewHolder {
        TextView kmView;
        TextView priceView;
        TextView modelView;
        public VehicleHolder(View itemView) {
            super(itemView);
            kmView = itemView.findViewById(R.id.km);
            priceView = itemView.findViewById(R.id.price);
            modelView = itemView.findViewById(R.id.model);
        }
        private void bind(Vehicle vehicle) {
            kmView.setText(vehicle.km);
            priceView.setText(vehicle.price);
            modelView.setText(vehicle.name);
        }
    }
    private class PubMaticViewHolder extends RecyclerView.ViewHolder {
        FrameLayout parent;
        public PubMaticViewHolder(FrameLayout adViewParent) {
            super(adViewParent);
            parent = adViewParent;
        }
        private void bind(PMBannerAdView adView) {
            //Remove view from parent if already added
            FrameLayout adViewParent = (FrameLayout) adView.getParent();
            if (adViewParent != null) {
                adViewParent.removeView(adView);
            }
            // Add ad view in Releative layout parent
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    getDpToPix(50));// Customize an Ad height
            parent.addView(adView, params);
        }
    }
    private int getDpToPix(int dp) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        int px = (int) ((float) dp * displayMetrics.density + 0.5F);
        return px;
    }
}