package com.myalaram.webview;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class homefragment extends Fragment {


    WebView webView;
    ProgressBar prograssbar;
    ImageView imgno;
    TextView txtno;
    RelativeLayout layout;
    Button btnno;
    private String weburl="https://www.who.int/emergencies/diseases/novel-coronavirus-2019/advice-for-public";
    ProgressDialog progressDialog;


    public homefragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vi = inflater.inflate(R.layout.fragment_homefragment, container, false);
        webView = (WebView) vi.findViewById(R.id.webview);
        prograssbar=(ProgressBar)vi.findViewById(R.id.progress_circular_country);
        btnno=(Button)vi.findViewById(R.id.noconnectionbtn);
        imgno=(ImageView)vi.findViewById(R.id.noconnectionimg);
        txtno=(TextView)vi.findViewById(R.id.txtnoconnection);
        layout=(RelativeLayout) vi.findViewById(R.id.noconnectionlayout);


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
        webView.canGoBack();
        webView.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });


        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.setWebViewClient(new WebViewClient(){


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                prograssbar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                prograssbar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });


        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkconnection();
            }
        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String url, final String userAgent, final String contentDisposition, final String mimetype, long contentLength) {
                Dexter.withActivity(getActivity())
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
                                DownloadManager downloadManager=(DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                                downloadManager.enqueue(request);
                                Toast.makeText(getActivity(),"Downloading File.....",Toast.LENGTH_SHORT).show();


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





        return vi;
    }







    public void Checkconnection(){


        ConnectivityManager connectivityManager= (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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
            prograssbar.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
        }
    }

}
