package com.example.projektaplikacjidlamola;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String USER_AGENT = "Mozilla/5.0";

    private static final String GET_URL = "https://www.googleapis.com/books/v1/volumes?q=intitle:Metro";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] name = {"TEST1", "2TEST","T3ST"};

        ImageView searchImage = (ImageView) findViewById(R.id.searchImage);
        ImageView favouriteImage = (ImageView) findViewById(R.id.favouriteImage);
        ImageView listImage = (ImageView) findViewById(R.id.listImage);

        searchImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: {
                        setContentView(R.layout.search_layout);
                        SearchView search = (SearchView) findViewById(R.id.searchView);
                        ListView listAllBooks = (ListView) findViewById(R.id.listAllBooks);
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,name);
                        listAllBooks.setAdapter(arrayAdapter);
                        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String text) {
                                System.out.println("XD!");
                                System.out.println(text);
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                arrayAdapter.getFilter().filter(newText);
                                return false;
                            }
                        });

                        System.out.println(search);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        break;
                    }
                }
                return false;
            }
        });

    }
}