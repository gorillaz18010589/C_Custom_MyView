package com.example.c_custom_myview;
//目的：自訂Ｖiew複習
//1.xml看到的元件,button,layout,textView,等其實全部都是繼承父類別Ｖiew
//2.當這些元素無法滿足你客戶的需求,比如說他要畫一個曲線圖
//3.有時為了業主美術一定要一模一樣時,那就必須自己來寫
//4.換言之自訂Ｖiew就是就是繼承View,有可能你繼承Button,TextView,但他們也都是繼承Ｖiew
//5.物件導向觀念非常重要,將影像你會不會寫自訂View,尤其多形觀念
//6.

//A.xml基本設定先故意寫一個Ｖiew,設定背景為綠色
//B.用Java寫一個類叫MyView =>
//C.繼承Ｖiew
//D.發現紅字,因為父類別Ｖiew並沒有無傳參數的建構式,引此子類別要自己寫建構式
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("hank","MainActivity -> onCreate super前,Ａ");
        super.onCreate(savedInstanceState);
        Log.v("hank","MainActivity -> onCreate super後,setContentView前,Ｂ");
        setContentView(R.layout.activity_main);//因為有setContentView所以他到xml,去執行MyView
        Log.v("hank","MainActivity -> onCreate super後,setContentView後,C");
    }
}