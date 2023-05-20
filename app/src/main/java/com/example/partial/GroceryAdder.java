package com.example.partial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ListViewAutoScrollHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class GroceryAdder extends AppCompatActivity {
    ImageView grocerAdd;
    ImageButton groceryToHome;
    static ListView listView;
    EditText groceryInput;
    static ArrayList<String> groceryItems;
    static GroceryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groceryadder);
        grocerAdd = findViewById(R.id.groceryItemAdd);
        groceryToHome = findViewById(R.id.grocerToHome);
        groceryInput = findViewById(R.id.groceryItemInput);
        listView = findViewById(R.id.groceryListView);

        groceryItems = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = groceryItems.get(i);
                Toast.makeText(GroceryAdder.this, name, Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new GroceryAdapter(getApplicationContext(), groceryItems);
        listView.setAdapter(adapter);

        groceryToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GroceryAdder.this, home.class);
                startActivity(i);
                finish();
            }
        });

        grocerAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = groceryInput.getText().toString();
                if(item.isEmpty()) {
                    Toast.makeText(GroceryAdder.this, "Please enter an item!", Toast.LENGTH_SHORT).show();
                } else {
                    addItem(item);
                    groceryInput.setText("");
                    Toast.makeText(GroceryAdder.this, item + " is added to the list!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadContent();
    }

    public void loadContent() {
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, "groceryList.txt");
        byte[] content = new byte[(int) readFrom.length()];

        FileInputStream stream = null;
        try {
            stream = new FileInputStream(readFrom);
            stream.read(content);

            String grocerString = new String(content);
            if (grocerString.length() > 2) {
                grocerString = grocerString.substring(1, grocerString.length() - 1);
                String split[] = grocerString.split(", ");
                if(split.length == 1 && split[0].isEmpty()) {
                    groceryItems = new ArrayList<>();
                } else {
                    groceryItems = new ArrayList<>(Arrays.asList(split));
                }
            } else {
                groceryItems = new ArrayList<>();
            }

            adapter = new GroceryAdapter(this, groceryItems);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onDestroy() {
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, "groceryList.txt"));
            writer.write(groceryItems.toString().getBytes());
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        super.onDestroy();
    }

    public static void addItem(String item) {
        groceryItems.add(item);
        listView.setAdapter(adapter);
    }

    public static void removeItem(int removedItem) {
        groceryItems.remove(removedItem);
        listView.setAdapter(adapter);

        File path = listView.getContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, "groceryList.txt"));
            writer.write(groceryItems.toString().getBytes());
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}