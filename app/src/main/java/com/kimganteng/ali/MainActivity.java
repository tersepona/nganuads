package com.kimganteng.ali;

import static com.kimganteng.ali.SettingsAlien.BackupIntertitial;
import static com.kimganteng.ali.SettingsAlien.BackupReward;
import static com.kimganteng.ali.SettingsAlien.Backup_Initialize;
import static com.kimganteng.ali.SettingsAlien.MainIntertitial;
import static com.kimganteng.ali.SettingsAlien.MainRewards;
import static com.kimganteng.ali.SettingsAlien.Select_Backup_Ads;
import static com.kimganteng.ali.SettingsAlien.Select_Main_Ads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aliendroid.alienads.AlienGDPR;
import com.aliendroid.alienads.AlienNotif;
import com.aliendroid.alienads.AliendroidInitialize;
import com.aliendroid.alienads.AliendroidIntertitial;
import com.aliendroid.alienads.AliendroidReward;
import com.aliendroid.alienads.interfaces.interstitial.admob.OnFullScreenContentCallbackAdmob;
import com.aliendroid.alienads.interfaces.interstitial.show.OnShowInterstitialAdmob;
import com.aliendroid.alienads.interfaces.rewards.load.OnLoadRewardsAdmob;
import com.aliendroid.sdkads.config.AppPromote;
import com.aliendroid.sdkads.config.InitializeAlienAds;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AppPromote.initializeAppPromote(this);
        InitializeAlienAds.LoadSDK();
        /*
        if (SettingsAlien.Select_Open_Ads.equals("2")) {

            AlienViewAds.OpenApp(MainActivity.this,AppIDViewAds);
        }
         */


        AliendroidInitialize.SelectAdsAdmob(this,Select_Backup_Ads,Backup_Initialize);
        AlienGDPR.loadGdpr(this,Select_Main_Ads,true);
        AliendroidIntertitial.LoadIntertitialAdmob(MainActivity.this,Select_Backup_Ads,MainIntertitial,BackupIntertitial,
                "","","","","");

        AliendroidIntertitial.onShowInterstitialAdmob = new OnShowInterstitialAdmob() {
            @Override
            public void onAdSuccess() {
                AliendroidIntertitial.onFullScreenContentCallbackAdmob = new OnFullScreenContentCallbackAdmob() {
                    @Override
                    public void onAdClicked() {

                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        Intent open = new Intent(MainActivity.this,BannerActivity.class);
                        startActivity(open);
                    }

                    @Override
                    public void onAdImpression() {

                    }

                    @Override
                    public void onAdShowedFullScreenContent() {

                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent() {

                    }
                };
            }

            @Override
            public void onAdFailedShow() {
                Intent open = new Intent(MainActivity.this,BannerActivity.class);
                startActivity(open);
            }
        };

        AliendroidReward.LoadRewardAdmob(this,Select_Backup_Ads,MainRewards,BackupReward);
        AliendroidReward.onLoadRewardsAdmob = new OnLoadRewardsAdmob() {
            @Override
            public void onAdFailedToLoad() {

            }

            @Override
            public void onAdLoaded(String error) {

            }
        };

    }





    public void BANNER(View view){
        Intent open = new Intent(MainActivity.this,BannerActivity.class);
        startActivity(open);

    }

    public void VIEWADS(View view){
        Intent open = new Intent(MainActivity.this,ViewAdsActivity.class);
        startActivity(open);

    }

    public void NATIVES(View view){
        Intent open = new Intent(MainActivity.this,NativeActivity.class);
        startActivity(open);

    }


    public void MEDIATION(View view){
        Intent open = new Intent(MainActivity.this,MediationAdsActivity.class);
        startActivity(open);

    }

    public void INTERSTITIAL(View view){
        AliendroidIntertitial.ShowIntertitialAdmob(MainActivity.this,Select_Backup_Ads,MainIntertitial,BackupIntertitial,0,"",
        "","","","");
    }

    public void REWARD(View view){
        AliendroidReward.ShowRewardAdmob(MainActivity.this,Select_Backup_Ads,MainRewards,BackupReward);
    }

    public void onBackPressed(){
        finishAffinity();
        System.exit(0);
    }
}