package com.avigail.android.testavigail;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import android.widget.TextView;

/**
 * Created by Avigail on 25/01/2019.
 */


public class MainActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;

    private ArrayList<Month> listMonths;

    private SliderAdapter sliderAdapter;

    private TextView textSubTitle;

    private TextView textTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textSubTitle = (TextView) findViewById(R.id.textSubTitle);
        textTitle = (TextView) findViewById(R.id.textTitle);

        listMonths = new ArrayList<Month>();

        listMonths = DataHelper.getGraphsDataFromJson("data.json", this);

        mSlideViewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderAdapter = new SliderAdapter(this,listMonths);
        mSlideViewPager.setAdapter(sliderAdapter);

        if(listMonths.size()>0){
            textSubTitle.setText(listMonths.get(0).getNameChild()+ "'s " +getString(R.string.sub_title));
            textTitle.setText(listMonths.get(0).getNameChild()+ "'s " +getString(R.string.title));
        }


    }


}
