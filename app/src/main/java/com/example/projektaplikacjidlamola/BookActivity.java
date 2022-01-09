package com.example.projektaplikacjidlamola;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;


public class BookActivity extends AppCompatActivity {

    LocalSaveAndWrite localSaveAndWrite= new LocalSaveAndWrite();

    //Serializacja do zapisu
    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    List<Book> listBooksToSave = new ArrayList<Book>();
    Intent intent;
    Book selectedBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_layout);
        intent = getIntent();
        selectedBook = (Book) intent.getSerializableExtra("Book");



        TextView textBookTitle = (TextView) findViewById(R.id.textViewBookTitle);
        TextView textBookAuthors = (TextView) findViewById(R.id.textViewBookAuthors);
        TextView textBookDescription = (TextView) findViewById(R.id.textViewBookDescription);

        CheckBox checkBoxBookRead = (CheckBox) findViewById(R.id.checkBoxBookRead);
        CheckBox checkBoxBookToRead = (CheckBox) findViewById(R.id.checkBoxBookToRead);
        CheckBox checkBoxBookLike = (CheckBox) findViewById(R.id.checkBoxBookLike);

        //Scrollowanie
        textBookTitle.setMovementMethod(new ScrollingMovementMethod());
        textBookAuthors.setMovementMethod(new ScrollingMovementMethod());
        textBookDescription.setMovementMethod(new ScrollingMovementMethod());

        //Zczytanie z localnego magazynu książek
        try{
            String readData = localSaveAndWrite.readFromFile(getApplicationContext());
            JsonElement fileElement = JsonParser.parseString(String.valueOf(readData));
            JsonArray jsonArray = fileElement.getAsJsonArray();

            for(JsonElement temp : jsonArray){
                try{
                    JsonObject tempJsonObject = temp.getAsJsonObject();
                    String tempTitle = tempJsonObject.get("title").getAsString();
                    String tempAuthors = tempJsonObject.get("authors").getAsString();
                    String tempDescription = tempJsonObject.get("description").getAsString();
                    Boolean read = tempJsonObject.get("read").getAsBoolean();
                    Boolean toRead = tempJsonObject.get("toRead").getAsBoolean();
                    Boolean like = tempJsonObject.get("like").getAsBoolean();
                    listBooksToSave.add(new Book(tempAuthors, tempTitle, tempDescription, read, toRead,like));
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        //Reset obiektu ksiażki
        for (Book temp : listBooksToSave){
            if(isIsTheSame(temp, selectedBook)){
                checkBoxBookRead.setChecked(temp.getRead());
                checkBoxBookToRead.setChecked(temp.getToRead());
                checkBoxBookLike.setChecked(temp.getLike());
                selectedBook.setLike(temp.getLike());
                selectedBook.setRead(temp.getRead());
                selectedBook.setToRead(temp.getToRead());
                listBooksToSave.remove(temp);
                break;
            }
        }

        //Ustawianie tytułu itd.
        textBookTitle.setText(selectedBook.title);
        textBookAuthors.setText(selectedBook.authors);
        textBookDescription.setText(selectedBook.description);

        //Listeners
        setListeners(checkBoxBookRead,selectedBook,"read");
        setListeners(checkBoxBookToRead,selectedBook,"toRead");
        setListeners(checkBoxBookLike,selectedBook,"like");

    }

    @Override
    protected void onPause() {
        if(selectedBook.getLike() == true || selectedBook.getRead() == true || selectedBook.getToRead() == true)
            listBooksToSave.add(selectedBook);

        String jsonString = gson.toJson(listBooksToSave);
        getApplicationContext().deleteFile("config.txt");
        localSaveAndWrite.writeToFile(jsonString, getApplicationContext());
        super.onPause();
    }

    private Boolean isIsTheSame(Book first, Book second){
        if(first.getTitle().equals(second.getTitle()) && first.getAuthors().equals(second.getAuthors()) && first.getDescription().equals(second.getDescription()))
            return true;
        else
            return false;
    }

    private void setListeners(CheckBox checkBox, Book selectedBook, String typeCheckBox ){

        checkBox.setOnClickListener(v -> {
            if(checkBox.isChecked()){
                switch(typeCheckBox){
                    case "read":{
                        selectedBook.setRead(true);
                        break;
                    }
                    case "toRead":{
                        selectedBook.setToRead(true);
                        break;
                    }
                    case "like":{
                        selectedBook.setLike(true);
                        break;
                    }
                }
            }else
            {
                switch(typeCheckBox){
                    case "read":{
                        selectedBook.setRead(false);
                        break;
                    }
                    case "toRead":{
                        selectedBook.setToRead(false);
                        break;
                    }
                    case "like":{
                        selectedBook.setLike(false);
                        break;
                    }
                }
            }
        });
    }

}
