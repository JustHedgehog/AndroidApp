package com.example.projektaplikacjidlamola;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView searchImage;
    ImageView favouriteImage;
    ImageView listImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchImage = (ImageView) findViewById(R.id.searchImage);
        favouriteImage = (ImageView) findViewById(R.id.favouriteImage);
        listImage = (ImageView) findViewById(R.id.listImage);
        setListeners();
        getApplicationContext().deleteFile("config.txt");

    }

    public void setListeners(){
        searchImage.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}