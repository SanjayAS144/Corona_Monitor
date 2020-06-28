package com.myalaram.webview;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity  {

    WebView webView;
    ProgressBar prograssbar;
    ImageView imgno;
    TextView txtno;
    RelativeLayout layout;
    Button btnno;
    private String weburl="https://www.who.int/emergencies/diseases/novel-coronavirus-2019/advice-for-public";
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        layout=(RelativeLayout)findViewById(R.id.noconnectionlayout);
        imgno=(ImageView)findViewById(R.id.noconnectionimg);
        txtno=(TextView)findViewById(R.id.txtnoconnection);
        btnno=(Button)findViewById(R.id.noconnectionbtn);
        prograssbar=(ProgressBar)findViewById(R.id.prograssBar);
        webView=(WebView)findViewById(R.id.webview);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading_Please_wait");
        if(savedInstanceState!=null){
            webView.restoreState(savedInstanceState);
        }
        else{

            WebSettings websetting=webView.getSettings();
            websetting.setJavaScriptEnabled(true);
            websetting.setLoadWithOverviewMode(true);
            websetting.setUseWideViewPort(true);
            websetting.setDomStorageEnabled(true);
            websetting.setLoadsImagesAutomatically(true);
            Checkconnection();

        }








        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String url, final String userAgent, final String contentDisposition, final String mimetype, long contentLength) {
                Dexter.withActivity(MainActivity.this)
                        .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {

                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                                request.setMimeType(mimetype);
                                String cookies= CookieManager.getInstance().getCookie(url);
                                request.addRequestHeader("cookie",cookies);
                                request.addRequestHeader("User-Agent",userAgent);
                                request.setDescription("Downloading File.....");
                                request.setTitle(URLUtil.guessFileName(url,contentDisposition,mimetype));
                                request.allowScanningByMediaScanner();
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                request.setDestinationInExternalPublicDir(

                                        Environment.DIRECTORY_DOWNLOADS,URLUtil.guessFileName(url,contentDisposition,mimetype)
                                );
                                DownloadManager downloadManager=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                                downloadManager.enqueue(request);
                                Toast.makeText(MainActivity.this,"Downloading File.....",Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                    token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                prograssbar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_in));
                prograssbar.setProgress(newProgress);
                progressDialog.show();

                if(newProgress==100){
                    prograssbar.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    prograssbar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_out));
                }else
                    super.onProgressChanged(view, newProgress);
            }
        });


        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Checkconnection();
            }
        });


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
    public void Checkconnection(){


        ConnectivityManager connectivityManager= (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo network=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


        if(wifi.isConnected()){
            webView.loadUrl(weburl);
            webView.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
        }else if(network.isConnected()){
            webView.loadUrl(weburl);
            webView.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
        }else{
            webView.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.tool_who_web:
                webView.loadUrl("https://www.who.int/");
                return true;

            case R.id.tool_who_twitter:
                webView.loadUrl("https://twitter.com/who");
                return true;

            case R.id.WHO_help:
                webView.loadUrl("https://www.who.int/about/who-we-are/contact-us");
                return true;




            default:
                return super.onOptionsItemSelected(item);


        }

    }
}
