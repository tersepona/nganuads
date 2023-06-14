package com.aliendroid.alienads;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aliendroid.alienads.interfaces.natives.OnLoadMediumNativesAdmob;
import com.aliendroid.alienads.interfaces.natives.OnLoadMediumNativesAlien;
import com.aliendroid.alienads.interfaces.natives.OnLoadMediumNativesApplovinMax;
import com.aliendroid.alienads.interfaces.natives.OnLoadMediumNativesFacebook;
import com.aliendroid.alienads.interfaces.natives.OnLoadMediumNativesStartApp;
import com.aliendroid.alienads.interfaces.natives.OnLoadSmallNativesAdmob;
import com.aliendroid.alienads.interfaces.natives.OnLoadSmallNativesAlien;
import com.aliendroid.alienads.interfaces.natives.OnLoadSmallNativesApplovinMax;
import com.aliendroid.alienads.interfaces.natives.OnLoadSmallNativesFacebook;
import com.aliendroid.alienads.interfaces.natives.OnLoadSmallNativesStartApp;
import com.aliendroid.sdkads.interfaces.OnLoadNative;
import com.aliendroid.sdkads.type.mediation.AlienMediationAds;
import com.aliendroid.sdkads.type.view.AlienViewAds;
import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.startapp.sdk.ads.nativead.NativeAdDetails;
import com.startapp.sdk.ads.nativead.StartAppNativeAd;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AliendroidNative {
    private static NativeAd nativeAd;
    public static OnLoadSmallNativesAdmob onLoadSmallNativesAdmob;
    public static OnLoadSmallNativesApplovinMax onLoadSmallNativesApplovinMax;
    public static OnLoadSmallNativesFacebook onLoadSmallNativesFacebook;
    public static OnLoadSmallNativesStartApp onLoadSmallNativesStartApp;
    public static OnLoadSmallNativesAlien onLoadSmallNativesAlien;
    public static StartAppNativeAd startAppNativeAd;

    public static OnLoadMediumNativesAdmob onLoadMediumNativesAdmob;
    public static OnLoadMediumNativesApplovinMax onLoadMediumNativesApplovinMax;
    public static OnLoadMediumNativesFacebook onLoadMediumNativesFacebook;
    public static OnLoadMediumNativesStartApp onLoadMediumNativesStartApp;
    public static OnLoadMediumNativesAlien onLoadMediumNativesAlien;

    public static void SmallNativeAdmob(Activity activity, RelativeLayout layNative, String selectAdsBackup, String nativeId, String idNativeBackup, String Hpk1,
                                        String Hpk2, String Hpk3, String Hpk4, String Hpk5) {
        AdLoader.Builder builder = new AdLoader.Builder(activity, nativeId);
        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(@NonNull NativeAd nativeAds) {
                if (onLoadSmallNativesAdmob !=null){
                    onLoadSmallNativesAdmob.onNativeAdLoaded();
                }
                if (nativeAd != null) {
                    nativeAd.destroy();
                }

                nativeAd = nativeAds;
                NativeAdView adView = (NativeAdView) activity.getLayoutInflater()
                        .inflate(R.layout.admob_small_native, null);
                populateNativeAdView2(nativeAds, adView);
                layNative.removeAllViews();
                layNative.addView(adView);
            }

        });

        VideoOptions videoOptions = new VideoOptions.Builder()
                .build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();
        builder.withNativeAdOptions(adOptions);

        AdRequest request = new AdRequest.Builder().addKeyword(Hpk1).addKeyword(Hpk2)
                .addKeyword(Hpk3).addKeyword(Hpk4).addKeyword(Hpk5)
                .build();
        AdLoader adLoader =
                builder
                        .withAdListener(
                                new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                                        if (onLoadSmallNativesAdmob !=null){
                                            onLoadSmallNativesAdmob.onAdFailedToLoad("");
                                        }
                                        switch (selectAdsBackup) {
                                            case "STARTAPP":
                                                startAppNativeAd = new StartAppNativeAd(activity);
                                                View adViewNative = (View) activity.getLayoutInflater()
                                                        .inflate(R.layout.startapp_small_native, null);
                                                AdEventListener adListener = new AdEventListener() {
                                                    @Override
                                                    public void onReceiveAd(@NonNull com.startapp.sdk.adsbase.Ad ad) {
                                                        ArrayList ads = startAppNativeAd.getNativeAds();    // get NativeAds list
                                                        Iterator iterator = ads.iterator();
                                                        while (iterator.hasNext()) {
                                                            Log.d("MyApplication", iterator.next().toString());
                                                        }
                                                        NativeAdDetails adDetails = (NativeAdDetails) ads.get(0);
                                                        if (adDetails != null) {
                                                            TextView title = adViewNative.findViewById(R.id.ad_headline);
                                                            title.setText(adDetails.getTitle());
                                                            ImageView icon = adViewNative.findViewById(R.id.ad_app_icon);
                                                            Glide.with(activity).load(adDetails.getSecondaryImageUrl()).into(icon);
                                                            TextView description = adViewNative.findViewById(R.id.ad_body);
                                                            description.setText(adDetails.getDescription());
                                                            Button open = adViewNative.findViewById(R.id.ad_call_to_action);
                                                            open.setText(adDetails.isApp() ? "Install" : "Open");
                                                            adDetails.registerViewForInteraction(adViewNative);
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailedToReceiveAd(@Nullable com.startapp.sdk.adsbase.Ad ad) {

                                                    }

                                                };
                                                startAppNativeAd.loadAd(adListener);
                                                layNative.addView(adViewNative);
                                                break;
                                        }
                                    }
                                })
                        .build();
        adLoader.loadAd(request);

    }

    public static void SmallNativeMax(Activity activity, RelativeLayout layNative, String selectAdsBackup, String nativeId, String idNativeBackup) {

    }


    public static void SmallNativeFan(Activity activity, RelativeLayout layNative, String selectAdsBackup, String nativeId, String idNativeBackup) {

    }


    public static void SmallNativeStartApp(Activity activity, RelativeLayout layNative, String selectAdsBackup, String nativeId, String idNativeBackup) {
    }

    public static void MediumNativeStartApp(Activity activity, RelativeLayout layNative, String selectAdsBackup, String nativeId, String idNativeBackup) {

    }

    public static void MediumNativeAdmob(Activity activity, RelativeLayout layNative, String selectAdsBackup, String nativeId, String idNativeBackup, String Hpk1,
                                         String Hpk2, String Hpk3, String Hpk4, String Hpk5) {

        AdLoader.Builder builder = new AdLoader.Builder(activity, nativeId);
        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(@NonNull NativeAd nativeAds) {
                if (onLoadMediumNativesAdmob!=null){
                    onLoadMediumNativesAdmob.onNativeAdLoaded();
                }
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = nativeAds;
                NativeAdView adView = (NativeAdView) activity.getLayoutInflater()
                        .inflate(R.layout.admob_big_native, null);
                populateNativeAdView(nativeAds, adView);
                layNative.removeAllViews();
                layNative.addView(adView);
            }


        });

        VideoOptions videoOptions = new VideoOptions.Builder()
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);

        AdRequest request = new AdRequest.Builder().addKeyword(Hpk1).addKeyword(Hpk2)
                .addKeyword(Hpk3).addKeyword(Hpk4).addKeyword(Hpk5)
                .build();
        AdLoader adLoader =
                builder
                        .withAdListener(
                                new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                                        if (onLoadMediumNativesAdmob!=null){
                                            onLoadMediumNativesAdmob.onAdFailedToLoad("");
                                        }
                                        switch (selectAdsBackup) {
                                            case "STARTAPP":
                                                startAppNativeAd = new StartAppNativeAd(activity);
                                                View adViewNative = (View) activity.getLayoutInflater()
                                                        .inflate(R.layout.startapp_medium_native, null);
                                                AdEventListener adListener = new AdEventListener() {
                                                    @Override
                                                    public void onReceiveAd(@NonNull com.startapp.sdk.adsbase.Ad ad) {
                                                        ArrayList ads = startAppNativeAd.getNativeAds();    // get NativeAds list
                                                        Iterator iterator = ads.iterator();
                                                        while (iterator.hasNext()) {
                                                            Log.d("MyApplication", iterator.next().toString());
                                                        }
                                                        NativeAdDetails adDetails = (NativeAdDetails) ads.get(0);
                                                        if (adDetails != null) {
                                                            TextView title = adViewNative.findViewById(R.id.ad_headline);
                                                            title.setText(adDetails.getTitle());
                                                            ImageView icon = adViewNative.findViewById(R.id.ad_app_icon);
                                                            Glide.with(activity).load(adDetails.getSecondaryImageUrl()).into(icon);
                                                            ImageView details = adViewNative.findViewById(R.id.imgDetail);
                                                            Glide.with(activity).load(adDetails.getImageUrl()).into(details);
                                                            TextView description = adViewNative.findViewById(R.id.ad_body);
                                                            description.setText(adDetails.getDescription());
                                                            Button open = adViewNative.findViewById(R.id.ad_call_to_action);
                                                            open.setText(adDetails.isApp() ? "Install" : "Open");
                                                            adDetails.registerViewForInteraction(adViewNative);
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailedToReceiveAd(@Nullable com.startapp.sdk.adsbase.Ad ad) {

                                                    }

                                                };
                                                startAppNativeAd.loadAd(adListener);
                                                layNative.addView(adViewNative);
                                                break;


                                        }
                                    }
                                })
                        .build();
        adLoader.loadAd(request);
    }

    public static void MediumNativeMax(Activity activity, RelativeLayout layNative, String selectAdsBackup, String nativeId, String idNativeBackup) {

    }

    public static void MediumNativeFan(Activity activity, RelativeLayout layNative, String selectAdsBackup, String nativeId, String idNativeBackup) {

    }


    public static void MediumNativeAlien(Activity activity, RelativeLayout layNative, String selectAdsBackup, String nativeId, String idNativeBackup) {

    }

    public static void SmallNativeAlien(Activity activity, RelativeLayout layNative, String selectAdsBackup, String nativeId, String idNativeBackup) {

    }

    public static void SmallNativeAdmobRectangle(Activity activity, RelativeLayout layNative, String selectAdsBackup, String nativeId, String idNativeBackup, String Hpk1,
                                                 String Hpk2, String Hpk3, String Hpk4, String Hpk5) {

        AdLoader.Builder builder = new AdLoader.Builder(activity, nativeId);
        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(@NonNull NativeAd nativeAds) {
                if (onLoadMediumNativesAdmob!=null){
                    onLoadMediumNativesAdmob.onNativeAdLoaded();
                }
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = nativeAds;
                NativeAdView adView = (NativeAdView) activity.getLayoutInflater()
                        .inflate(R.layout.admob_small_rectangle_native, null);
                populateNativeAdView2(nativeAds, adView);
                layNative.removeAllViews();
                layNative.addView(adView);
            }


        });

        VideoOptions videoOptions = new VideoOptions.Builder()
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);

        AdRequest request = new AdRequest.Builder().addKeyword(Hpk1).addKeyword(Hpk2)
                .addKeyword(Hpk3).addKeyword(Hpk4).addKeyword(Hpk5)
                .build();
        AdLoader adLoader =
                builder
                        .withAdListener(
                                new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                                        if (onLoadMediumNativesAdmob!=null){
                                            onLoadMediumNativesAdmob.onAdFailedToLoad("");
                                        }
                                        switch (selectAdsBackup) {
                                            case "STARTAPP":
                                                startAppNativeAd = new StartAppNativeAd(activity);
                                                View adViewNative = (View) activity.getLayoutInflater()
                                                        .inflate(R.layout.startapp_small_rectangle_native, null);
                                                AdEventListener adListener = new AdEventListener() {
                                                    @Override
                                                    public void onReceiveAd(@NonNull com.startapp.sdk.adsbase.Ad ad) {
                                                        ArrayList ads = startAppNativeAd.getNativeAds();    // get NativeAds list
                                                        Iterator iterator = ads.iterator();
                                                        while (iterator.hasNext()) {
                                                            Log.d("MyApplication", iterator.next().toString());
                                                        }
                                                        NativeAdDetails adDetails = (NativeAdDetails) ads.get(0);
                                                        if (adDetails != null) {
                                                            TextView title = adViewNative.findViewById(R.id.ad_headline);
                                                            title.setText(adDetails.getTitle());
                                                            ImageView icon = adViewNative.findViewById(R.id.ad_app_icon);
                                                            Glide.with(activity).load(adDetails.getSecondaryImageUrl()).into(icon);
                                                            ImageView details = adViewNative.findViewById(R.id.imgDetail);
                                                            Glide.with(activity).load(adDetails.getImageUrl()).into(details);
                                                            TextView description = adViewNative.findViewById(R.id.ad_body);
                                                            description.setText(adDetails.getDescription());
                                                            Button open = adViewNative.findViewById(R.id.ad_call_to_action);
                                                            open.setText(adDetails.isApp() ? "Install" : "Open");
                                                            adDetails.registerViewForInteraction(adViewNative);
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailedToReceiveAd(@Nullable com.startapp.sdk.adsbase.Ad ad) {

                                                    }

                                                };
                                                startAppNativeAd.loadAd(adListener);
                                                layNative.addView(adViewNative);
                                                break;
                                        }

                                    }
                                })
                        .build();
        adLoader.loadAd(request);
    }

    public static void SmallNativeMaxRectangle(Activity activity, RelativeLayout layNative, String selectAdsBackup, String nativeId, String idNativeBackup) {



    }

    public static void SmallNativeFanRectangle(Activity activity, RelativeLayout layNative, String selectAdsBackup, String nativeId, String idNativeBackup) {
    }


    public static void SmallNativeAlienRectangle(Activity activity, RelativeLayout layNative, String selectAdsBackup, String nativeId, String idNativeBackup) {

    }

    //Rectangle
    public static void SmallNativeStartAppRectangle(Activity activity, RelativeLayout layNative, String selectAdsBackup, String nativeId, String idNativeBackup) {

    }



    private static void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.GONE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.GONE);
        }
        adView.setNativeAd(nativeAd);
    }

    private static void populateNativeAdView2(NativeAd nativeAd, NativeAdView adView) {
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.GONE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.GONE);
        }
        adView.setNativeAd(nativeAd);
    }

}
