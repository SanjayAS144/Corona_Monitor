package com.myalaram.webview;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Moht extends Fragment {

    WebView webView;
    ProgressBar prograssbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vi = inflater.inflate(R.layout.fragment_moht, container, false);
        webView = (WebView) vi.findViewById(R.id.webview);
        prograssbar=(ProgressBar)vi.findViewById(R.id.progress_circular_country);
        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings websetting=webView.getSettings();
        websetting.setLoadWithOverviewMode(true);
        websetting.setUseWideViewPort(true);
        websetting.setDomStorageEnabled(true);
        websetting.setLoadsImagesAutomatically(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://twitter.com/MoHFW_INDIA");
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




        return vi;
    }
}
