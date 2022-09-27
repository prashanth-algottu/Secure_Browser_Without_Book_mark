package com.emm.securebrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BrowserActivity extends AppCompatActivity {
    EditText urlInput;
    ImageView clearUrl;
    Switch web_fav_button;
    ProgressBar progressBar;
    WebView webView;
    ImageView webBack,webForward,webRefresh,webShare,webHome;
    List<String> whiteList = new ArrayList<>();
    DBHelper DB;
    boolean first_time=false;
    ArrayList<String> database = new ArrayList();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_browser);
        DB = new DBHelper(BrowserActivity.this);

        View decorview = findViewById(R.id.ovrlay);
        decorview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                |View.SYSTEM_UI_FLAG_FULLSCREEN);


        urlInput = findViewById(R.id.url_input);
       clearUrl = findViewById(R.id.web_clear);
        progressBar = findViewById(R.id.progress_bar);
        webView = findViewById(R.id.web_view);
        webHome = findViewById(R.id.web_home);
//        web_fav_button = findViewById(R.id.web_fav_button);

        webBack = findViewById(R.id.web_back);
        webForward = findViewById(R.id.web_forward);
        webRefresh = findViewById(R.id.web_refresh);
        webShare = findViewById(R.id.web_share);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });

        urlInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if(i == EditorInfo.IME_ACTION_GO || i == EditorInfo.IME_ACTION_DONE){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(urlInput.getWindowToken(),0);
                    whiteList.add("youtube.com");
                    whiteList.add("facebook.com");
                    whiteList.add("TECTORO.COM");
                    whiteList.add("twitter.com");
                    whiteList.add("myntra.com");
                    whiteList.add("flipkart.com");
//                        web_fav_button.setBackground(getDrawable(R.drawable.book));
//                        Toast.makeText(BrowserActivity.this,"white color",Toast.LENGTH_SHORT).show();
//
//                        ArrayList<String> datab = DB.getdata();
//
//                        for (String dat:datab) {
//                            if(dat.equalsIgnoreCase(urlInput.getText().toString())){
//                                web_fav_button.setBackground(getDrawable(R.drawable.bookmarks_svg));
//                            }else{
//                                web_fav_button.setBackground(getDrawable(R.drawable.book));
//                            }
//                        }
                    loadMyUrl(urlInput.getText().toString());
                    return true;
                }
                return false;
            }
        });

        clearUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlInput.getText().clear();
            }
        });

        webBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(webView.canGoBack()){
                    webView.goBack();
                }
            }
        });
        webHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               finish();
            }
        });

        webForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(webView.canGoForward()){
                    webView.goForward();
                }
            }
        });

        webRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.reload();
            }
        });

        webShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
                intent.setType("text/plain");
                startActivity(intent);
            }
        });
        webShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
                intent.setType("text/plain");
                startActivity(intent);
            }
        });


//        web_fav_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ArrayList<String> dataBaseList= DB.getdata();
//                String url = urlInput.getText().toString();
//
//
//            }
//        });


//        web_fav_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//               ArrayList<String> dataBaseList= DB.getdata();
//                String url = urlInput.getText().toString();
//
//
//
//
//                if(b){
//                    if(dataBaseList.size()>0){
//                        System.out.println("Count-- "+dataBaseList.size());
//
//                        for(String data : dataBaseList){
//                            if(data.equalsIgnoreCase(url)){
//                                Boolean checkudeletedata = DB.deletedata(url);
//                                Toast.makeText(BrowserActivity.this,"Deleted"+url,Toast.LENGTH_SHORT).show();
//                                dataBaseList.remove(url);
//                                if (checkudeletedata){
//                                    Toast.makeText(BrowserActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
//                                    web_fav_button.setBackground(getDrawable(R.drawable.book));
//                                }
//                            }
//                            else{
//                                // 2nd time insert operation
//                                Boolean checkinsertdata = DB.insertuserdata(url);
//                                if(checkinsertdata){
//                                    Toast.makeText(BrowserActivity.this,"2nd time inserted"+url,Toast.LENGTH_SHORT).show();
//                                    web_fav_button.setBackground(getDrawable(R.drawable.bookmarks_svg));
//
//                                }
//                            }
//                        }
//
//                    }else if(dataBaseList.size()<=0){
//                        // if data base has not having data at first this block will call
//                        Boolean checkinsertdata = DB.insertuserdata(url);
//                        if(checkinsertdata) {
//                            Toast.makeText(BrowserActivity.this, "1st Entry Inserted-else-if "+url, Toast.LENGTH_SHORT).show();
//                            web_fav_button.setBackground(getDrawable(R.drawable.bookmarks_svg));
//                        }
//                    }
//
//
//
//                    // Alternatively hits
//                }else{
//                    Boolean checkudeletedata = DB.deletedata(url);
//                    dataBaseList.remove(url);
//                    if(checkudeletedata){
//                        Toast.makeText(BrowserActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
//                        web_fav_button.setBackground(getDrawable(R.drawable.book));
//                    }
//                }
//                dataBaseList = DB.getdata();
//                for (String ar:dataBaseList) {
//                    System.out.println("Final List --> "+ar);
//
//                }
//            }
//        });

    }



    private void loadMyUrl(String url) {
        final Dialog dialog = new Dialog(BrowserActivity.this);
        Button button;
        boolean black = false;
        for (String white : whiteList) {
            if (white.equalsIgnoreCase(url)) {
                System.out.println(white + "-++-->" + url);
                boolean matchUrl = Patterns.WEB_URL.matcher(url).matches();
                if(matchUrl){

                    if (!url.startsWith("http")) {
                        url = "https://" + url;
                    }
                    webView.loadUrl(url);
                }
                black = true;


            }
        }
        if(!black){
            dialog.setContentView(R.layout.blocked_pop_up);
            button = dialog.findViewById(R.id.btn_ok);
            dialog.setCancelable(false);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }


    }
}