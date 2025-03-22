package com.example.ratings;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {

    private ImageView starSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        starSplash = findViewById(R.id.starSplash);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    starSplash.animate().rotation(360f).setDuration(2000);
                    starSplash.animate().scaleX(0.5f).scaleY(0.5f).setDuration(3000);
                    starSplash.animate().translationYBy(1000f).setDuration(2000);
                    starSplash.animate().alpha(0f).setDuration(6000);
                    sleep(5000);
                    Intent intent = new Intent(SplashActivity.this, ListActivity.class);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    protected void onPause(){
        super.onPause();
        this.finish();
    }
}