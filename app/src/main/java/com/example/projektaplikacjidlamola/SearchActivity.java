package com.example.projektaplikacjidlamola;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity{

    private static final String USER_AGENT = "Mozilla/5.0";

    private String GET_URL = "https://www.googleapis.com/books/v1/volumes?q=intitle:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<Book> bookArray = new ArrayList<Book>();

        setContentView(R.layout.search_layout);
        SearchView search = (SearchView) findViewById(R.id.searchView);
        ListView listAllBooks = (ListView) findViewById(R.id.listAllBooks);
        ArrayAdapter<Book> arrayAdapter = new ArrayAdapter<Book>(getApplicationContext(), android.R.layout.simple_list_item_1, bookArray);
        listAllBooks.setAdapter(arrayAdapter);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextSubmit(String text) {

                try {
                    StringBuffer response = new StringBuffer();
                    try {
                        URL obj = new URL(GET_URL + text + "&maxResults=40");
                        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                        con.setRequestMethod("GET");
                        con.setRequestProperty("User-Agent", USER_AGENT);

                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String inputLine;

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    bookArray.clear();


                    JsonElement fileElement = JsonParser.parseString(String.valueOf(response));
                    JsonObject fileObject = fileElement.getAsJsonObject();
                    JsonArray jsonArray = fileObject.get("items").getAsJsonArray();
                    for (JsonElement temp : jsonArray) {
                        JsonObject tempJsonObject = temp.getAsJsonObject();
                        JsonElement tempJsonElement = tempJsonObject.get("volumeInfo").getAsJsonObject();
                        tempJsonObject = tempJsonElement.getAsJsonObject();


                        String tempTitle;
                        String tempAuthors;
                        String tempDescription;

                        if (tempJsonObject.get("title") != null) {
                            tempTitle = tempJsonObject.get("title").getAsString();
                        } else {
                            tempTitle = "brak";
                        }
                        if (tempJsonObject.get("authors") != null) {
                            tempAuthors = tempJsonObject.get("authors").getAsJsonArray().toString();
                        } else {
                            tempAuthors = "brak";
                        }
                        if (tempJsonObject.get("description") != null) {
                            tempDescription = tempJsonObject.get("description").getAsString();
                        } else {
                            tempDescription = "brak";
                        }
                        bookArray.add(new Book(tempAuthors, tempTitle, tempDescription));
                    }
                    System.out.println(bookArray.size());

                    arrayAdapter.addAll(bookArray);
                    arrayAdapter.getFilter().filter(text);

                } catch (Exception e) {
                    System.out.println(e);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });

        listAllBooks.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, BookActivity.class);
            intent.putExtra("Book", (Serializable) arrayAdapter.getItem(position));
            startActivity(intent);
        });


    }


}