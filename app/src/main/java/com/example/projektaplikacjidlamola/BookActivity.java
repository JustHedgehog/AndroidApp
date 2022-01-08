package com.example.projektaplikacjidlamola;

import android.content.Context;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_layout);

        //      Serializacja do zapisu
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

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

//        getApplicationContext().deleteFile("config.txt");

        Intent intent = getIntent();

        Book selectedBook = (Book) intent.getSerializableExtra("Book");
        List<Book> listBooksToSave = new ArrayList<Book>();

        try{
            String readData = readFromFile(getApplicationContext());
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

        for (Book temp : listBooksToSave){
            if(isIsTheSame(temp, selectedBook)){
                checkBoxBookRead.setChecked(temp.getRead());
                checkBoxBookToRead.setChecked(temp.getToRead());
                checkBoxBookLike.setChecked(temp.getLike());
                break;
            }
        }

        //Ustawianie tytułu itd.
        textBookTitle.setText(selectedBook.title);
        textBookAuthors.setText(selectedBook.authors);
        textBookDescription.setText(selectedBook.description);



        checkBoxBookLike.setOnClickListener(v -> {
            if(checkBoxBookLike.isChecked()){
                selectedBook.setLike(true);
                listBooksToSave.add(selectedBook);
                String jsonString = gson.toJson(listBooksToSave);
                writeToFile(jsonString, getApplicationContext());
            }else
            {
                System.out.println("XD");
                selectedBook.setLike(false);
                String jsonString = gson.toJson(listBooksToSave);
                getApplicationContext().deleteFile("config.txt");
                writeToFile(jsonString, getApplicationContext());
            }
        });




//        System.out.println(jsonString);


        String read = readFromFile(getApplicationContext());
        System.out.println(read);
    }


    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

        return ret;
    }

    private Boolean isIsTheSame(Book first, Book second){
        if(first.getTitle().equals(second.getTitle()) && first.getAuthors().equals(second.getAuthors()) && first.getDescription().equals(second.getDescription()))
            return true;
        else
            return false;
    }


}
