package com.myalaram.webview;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CovidCountrydetailsof extends AppCompatActivity {

    TextView tvdetailscountry,tvdetailstotalcases,tvdetailstodayscases,tvdetailestotaldeaths,tvdetailstodaydeaths,tvdetailstotalrecoverde;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_countrydetailsof);


        tvdetailscountry=findViewById(R.id.tvcountarynamed);
        tvdetailstotalcases=findViewById(R.id.tvTotalcasesd);
        tvdetailstodayscases=findViewById(R.id.tvTodayscases);
        tvdetailestotaldeaths=findViewById(R.id.tvtotaldeths);
        tvdetailstodaydeaths=findViewById(R.id.tvtodaysdeths);
        tvdetailstotalrecoverde=findViewById(R.id.tvtotalrecovered);


        CovidCountry covidcountry=getIntent().getParcelableExtra("EXTRA_COVID");




        tvdetailscountry.setText(covidcountry.getmCovidCountry());
        tvdetailstotalcases.setText(covidcountry.getMcases());
        tvdetailstodayscases.setText(covidcountry.getMtodaycases());
        tvdetailestotaldeaths.setText(covidcountry.getmDeaths());
        tvdetailstodaydeaths.setText(covidcountry.getMtodayDeaths());
        tvdetailstotalrecoverde.setText(covidcountry.getMrecovered());






    }

}

