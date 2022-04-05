package com.bam.note_v2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.common.InitializationListener;
import com.yandex.mobile.ads.common.MobileAds;
import com.yandex.mobile.ads.interstitial.InterstitialAd;
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener;


public class MainActivity extends AppCompatActivity {


    public int repostAdmob1 = 0;
    public int repostAdmob2 = 0;
    public int repostAdmob3 = 0;
    public int repostAdmob4 = 0;


    private static final String YANDEX_MOBILE_ADS_TAG = "YandexMobileAds";
    private static final String BLOCK_ID = "adf-406974/1252810";
    private InterstitialAd __ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, () -> {
            Log.d(YANDEX_MOBILE_ADS_TAG, "SDK initialized");

        });
    }


    // кнопка отключить рекламу, проверка на выполнения условий, если все 4 условия выполнены,
    // убирается рекламный блок и сохраняется статус, реклама заблокировна
    // и выводится сообщение , Реклама отключена
    public void disableAdMob(View view) {
        TextView repost1 = findViewById(R.id.repost_complete_text1);
        TextView repost2 = findViewById(R.id.repost_complete_text2);
        TextView repost3 = findViewById(R.id.repost_complete_text3);
        TextView repost4 = findViewById(R.id.repost_complete_text5);

        if (repost1.getText().toString().equals("репост отправлен")) {
            repostAdmob1 = 1;
        }
        if (repost2.getText().toString().equals("репост отправлен")) {
            repostAdmob2 = 1;
        }
        if (repost3.getText().toString().equals("репост отправлен")) {
            repostAdmob3 = 1;
        }
        if (repost4.getText().toString().equals("ролик просмотрен")) {
            repostAdmob4 = 1;
        }
        if (repostAdmob1 + repostAdmob2 + repostAdmob3 + repostAdmob4 == 4) {

            RecyclerView noteRecyclerView = findViewById(R.id.main_recyclerview);
            noteRecyclerView.setPadding(0, 0, 0, 0);


            RelativeLayout _relativeLayout = findViewById(R.id.repost_container);
            _relativeLayout.setVisibility(View.INVISIBLE);


            SharedPreferences pref = getSharedPreferences("block", MODE_PRIVATE);
            SharedPreferences.Editor edit = pref.edit();
            edit.putBoolean("blockAdMob", true);
            edit.apply();
            recreate();
            Toast.makeText(MainActivity.this, "Реклама отключена", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Выполните условия", Toast.LENGTH_SHORT).show();
        }
    }

// кнопка в блоке репостов, если реклама активна то просмотр рекламы и затем ставится галочка что условие выполнено
    // если реклама не работает то просто ставится галочка что условие выполнено
    public void adMob(View view) {

        __ad = new InterstitialAd(this);

        __ad.setAdUnitId(BLOCK_ID);

        final AdRequest adRequest = new AdRequest.Builder().build();

        __ad.setInterstitialAdEventListener(new InterstitialAdEventListener() {
            @Override
            public void onAdLoaded() {

                __ad.show();
                LinearLayout _repostItem4 = findViewById(R.id.repost_item4);
                changeRepostViewStatus2(_repostItem4);
            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
                Toast.makeText(MainActivity.this, "Реклама не работает", Toast.LENGTH_SHORT).show();
                LinearLayout _repostItem4 = findViewById(R.id.repost_item4);
                changeRepostViewStatus2(_repostItem4);
            }

            @Override
            public void onAdShown() {

            }

            @Override
            public void onAdDismissed() {

            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onLeftApplication() {

            }

            @Override
            public void onReturnedToApplication() {
                Toast.makeText(MainActivity.this, "Реклама просмотрена", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {

            }
        });


        __ad.loadAd(adRequest);

        }
//
    private void changeRepostViewStatus2(LinearLayout linearLayout) {
        linearLayout.setOnClickListener(null);

        ImageView _repostStatus = (ImageView) linearLayout.getChildAt(0);
        TextView _repostSuccessText = (TextView) linearLayout.getChildAt(1);
        ImageView _whatsAppIc = (ImageView) linearLayout.getChildAt(2);

        _repostStatus.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
        _repostSuccessText.setText("ролик просмотрен");
        _whatsAppIc.setVisibility(View.INVISIBLE);

        SharedPreferences pref = getSharedPreferences("repost4", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean("status4", true);
        edit.apply();
    }
}











