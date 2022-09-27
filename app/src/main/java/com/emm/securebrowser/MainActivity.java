package com.emm.securebrowser;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RelativeLayout searchLayout;
    DBHelper DB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        View decorview = findViewById(R.id.ovrlay);
        decorview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        searchLayout = findViewById(R.id.searchMainLay);
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BrowserActivity.class));
            }
        });
//        Cursor res = DB.getdata();
//        if (res.getCount() == 0) {
//            Toast.makeText(MainActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
//        } else {
//            StringBuffer buffer = new StringBuffer();
//            while (res.moveToNext()) {
//                buffer.append("URL :" + res.getString(0) + "\n");
//                Toast.makeText(MainActivity.this,"URL's->"+ res.getString(0),Toast.LENGTH_SHORT).show();
//            }
//        }
    }
}
