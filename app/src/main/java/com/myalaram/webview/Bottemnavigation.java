package com.myalaram.webview;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Bottemnavigation extends AppCompatActivity {



   private BottomNavigationView navigation;
    WebView webView;
    private String weburl="https://www.who.int/emergencies/diseases/novel-coronavirus-2019/advice-for-public";
    private FrameLayout Frame;
    private homefragment homefragment1;
    private CountryFragment countryfragment;
    private publicinstructionfragment pfragment;
    private instructionfragment ifragment;
    private schoolfragment sfragment;
    ProgressBar prograssbar;
    ImageView imgno;
    TextView txtno;
    RelativeLayout layout;
    Button btnno;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottemnavigation);

        navigation= findViewById(R.id.navigation);
        Frame=findViewById(R.id.mainframe);
        homefragment1 = new homefragment();
        countryfragment = new CountryFragment();
        pfragment=new publicinstructionfragment();
        ifragment=new instructionfragment();
        sfragment=new schoolfragment();
        prograssbar=(ProgressBar)findViewById(R.id.prograssBar);
        BottomNavigationView bn11 = findViewById(R.id.navigation);
        bn11.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainframe,
                new homefragment()).commit();

        LayoutInflater inflater = (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate( R.layout.activity_main,null,false );
        webView=(WebView)view.findViewById(R.id.webview);
        WebSettings websetting=webView.getSettings();
        websetting.setJavaScriptEnabled(true);
        websetting.setLoadWithOverviewMode(true);
        websetting.setUseWideViewPort(true);
        websetting.setDomStorageEnabled(true);
        websetting.setLoadsImagesAutomatically(true);






    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        switch(item.getItemId()){

            case R.id.tool_who_web:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainframe,
                        new FragmentWHOweb()).commit();
                return true;


            case R.id.tool_who_twitter:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainframe,
                        new FragmentWHOtwitter()).commit();
                return true;

            case R.id.WHO_help:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainframe,
                        new Helpalert()).commit();
                return true;

            case R.id.tool_whatsapp:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=41798931892&text=hi&source=&data="));
                finish();
                startActivity(browserIntent);
                return true;

            case R.id.request:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9154153916"));
                finish();
                startActivity(intent);
                return true;


            case R.id.call:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainframe,
                        new FragmentCONTACT()).commit();
                return true;

            case R.id.moh_twitter:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainframe,
                        new Moht()).commit();
            return true;

            case R.id.moh_web:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainframe,
                        new Mohw()).commit();
                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }






    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }
        else
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to Exit")
                    .setNegativeButton("no",null)
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    }).show();
        }
    }












    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener(){

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new homefragment();
                            break;
                        case R.id.nav_preventaion:
                            selectedFragment = new publicinstructionfragment();
                            break;
                        case R.id.nav_instruction:
                            selectedFragment = new instructionfragment();
                            break;
                        case R.id.nav_school:
                            selectedFragment = new schoolfragment();
                            break;
                        case R.id.nav_globelstat:
                            selectedFragment = new CountryFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainframe,
                            selectedFragment).commit();
                    return true;
                }
            };


}
