package com.example.projektaplikacjidlamola;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class StateListActivity extends AppCompatActivity {

    ListView stateList;
    List<String> statesList = new ArrayList<String>(){{
        add("Przeczytane");
        add("Do przeczytania");
        add("W trakcie czytania");
    }};
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.state_list_layout);
        System.out.println(statesList);
        stateList = (ListView) findViewById(R.id.stateList);
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, statesList);
        stateList.setAdapter(arrayAdapter);

        stateList.setOnItemClickListener((parent, view, position, id) -> {

            switch(position){
                case 0:{
                    Intent intent = new Intent(this, ReadActivity.class);
                    startActivity(intent);
                    break;
                }
                case 1:{
                    Intent intent = new Intent(this, ToReadActivity.class);
                    startActivity(intent);
                    break;
                }
                case 2:{
                    Intent intent = new Intent(this, InReadingActivity.class);
                    startActivity(intent);
                    break;
                }
            }
        });


    }
}
