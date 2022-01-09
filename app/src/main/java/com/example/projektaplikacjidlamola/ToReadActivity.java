package com.example.projektaplikacjidlamola;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ToReadActivity extends AppCompatActivity {

    ListView listToRead;
    List<Book> listBooksToShow = new ArrayList<Book>();
    ArrayAdapter<Book> arrayAdapter;
    LocalSaveAndWrite localSaveAndWrite = new LocalSaveAndWrite();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_read_layout);

        listToRead = (ListView) findViewById(R.id.toReadList);

        arrayAdapter = new ArrayAdapter<Book>(getApplicationContext(), android.R.layout.simple_list_item_1, listBooksToShow);
        listToRead.setAdapter(arrayAdapter);

        setData();

        listToRead.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, BookActivity.class);
            intent.putExtra("Book", (Serializable) arrayAdapter.getItem(position));
            startActivity(intent);
        });
    }

    private void setData() {
        try {
            String readData = localSaveAndWrite.readFromFile(getApplicationContext());
            JsonElement fileElement = JsonParser.parseString(String.valueOf(readData));
            JsonArray jsonArray = fileElement.getAsJsonArray();

            for (JsonElement temp : jsonArray) {
                try {
                    JsonObject tempJsonObject = temp.getAsJsonObject();
                    String tempTitle = tempJsonObject.get("title").getAsString();
                    String tempAuthors = tempJsonObject.get("authors").getAsString();
                    String tempDescription = tempJsonObject.get("description").getAsString();
                    Boolean read = tempJsonObject.get("read").getAsBoolean();
                    Boolean toRead = tempJsonObject.get("toRead").getAsBoolean();
                    Boolean like = tempJsonObject.get("like").getAsBoolean();
                    Boolean inReading = tempJsonObject.get("inReading").getAsBoolean();
                    if(toRead == true)
                        listBooksToShow.add(new Book(tempAuthors, tempTitle, tempDescription, read, toRead, like, inReading));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        listBooksToShow.clear();
        setData();
        arrayAdapter.notifyDataSetChanged();
        super.onResume();
    }
}
