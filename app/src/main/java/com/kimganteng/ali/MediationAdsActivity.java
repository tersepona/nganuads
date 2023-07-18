package com.kimganteng.ali;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.aliendroid.sdkads.type.mediation.AlienMediationAds;

public class MediationAdsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediation_ads);

        AlienMediationAds.SmallBanner(this,findViewById(R.id.layAdsView),SettingsAlien.BackupBanner);
        AlienMediationAds.LoadInterstitial(this,SettingsAlien.BackupIntertitial);
        AlienMediationAds.LoadRewarded(SettingsAlien.BackupReward);
        AlienMediationAds.MediumNatives(this,findViewById(R.id.layAlienNatives),SettingsAlien.BackupNatives);
    }

    public void INTERSTITIAL(View view){
      AlienMediationAds.ShowInterstitial(MediationAdsActivity.this);

    }

    public void REWARD(View view){
      AlienMediationAds.ShowReward();
    }
}