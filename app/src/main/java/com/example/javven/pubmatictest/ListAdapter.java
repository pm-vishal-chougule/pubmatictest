package com.example.javven.pubmatictest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.pubmatic.sdk.banner.PMBannerAdView;
import com.pubmatic.sdk.banner.pubmatic.PMBannerAdRequest;
import com.pubmatic.sdk.common.PMAdSize;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int TYPE_VEHICLE = 1;

  private static final int TYPE_BANNER = 2;

  private List<Vehicle> vehicles = new ArrayList<>();

  public void addAll(Collection<Vehicle> vehicles) {
    this.vehicles.addAll(vehicles);
  }

  @NonNull @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (viewType == TYPE_VEHICLE) {
      View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_vh, parent, false);
      return new VehicleHolder(rootView);
    } else if (viewType == TYPE_BANNER) {
      View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_vh, parent, false);
      return new PubmaticViewHolder(rootView);
    } else {
      throw new RuntimeException("Unknown item type.");
    }
  }

  @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof VehicleHolder) {
      ((VehicleHolder) holder).bind(vehicles.get(position));
    } else if (holder instanceof PubmaticViewHolder) {
      ((PubmaticViewHolder) holder).bind();
    }
  }

  @Override public int getItemCount() {
    return vehicles.size();
  }

  @Override public int getItemViewType(int position) {
    return position == 6 || position == 16? TYPE_BANNER : TYPE_VEHICLE;
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

  private class PubmaticViewHolder extends RecyclerView.ViewHolder {

    PMBannerAdView adView;

    PMBannerAdRequest adRequest;

    private PubmaticViewHolder(View itemView) {
      super(itemView);
      adView = itemView.findViewById(R.id.ad_view);
    }

    private void bind() {
      adRequest = PMBannerAdRequest.createPMBannerAdRequest("156453", "219778", "1178234");
      adRequest.setAdSize(PMAdSize.PMBANNER_SIZE_300x250);
      adView.loadRequest(adRequest);
    }
  }
}
