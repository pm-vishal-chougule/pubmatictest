package com.example.javven.pubmatictest;


import com.pubmatic.sdk.banner.PMBannerAdView;

/**
 *
 */

public class Ad extends ListItem {
    private PMBannerAdView adView;

    public Ad() {

    }
    public Ad(PMBannerAdView ad) {
        adView = ad;
    }

    public void setAdView(PMBannerAdView view) {
        adView = view;
    }

    public PMBannerAdView getAdView() {
        return adView;
    }
}
