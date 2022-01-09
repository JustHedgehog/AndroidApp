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

public class LikeActivity extends AppCompatActivity {
    static final int PICK_CONTACT_REQUEST = 0;
    LocalSaveAndWrite localSaveAndWrite = new LocalSaveAndWrite();
    ListView listAllBooks;
    List<Book> listBooksToShow = new ArrayList<Book>();
    ArrayAdapter<Book> arrayAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.like_layout);
        listAllBooks = (ListView) findViewById(R.id.listFavouriteBooks);
        arrayAdapter = new ArrayAdapter<Book>(getApplicationContext(), android.R.layout.simple_list_item_1, listBooksToShow);
        listAllBooks.setAdapter(arrayAdapter);

        setData();

        listAllBooks.setOnItemClickListener((parent, view, position, id) -> {
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
                    if(like == true)
                        listBooksToShow.add(new Book(tempAuthors, tempTitle, tempDescription, read, toRead, like));
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
