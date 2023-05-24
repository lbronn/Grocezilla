package com.example.partial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class GroceryAdder extends AppCompatActivity {
    ImageButton groceryToHome;
    ImageView grocerAdd;
    ImageView goToBudgetView;
    static ListView listView;
    EditText groceryInput;
    AppCompatButton addBudget;
    static ArrayList<GroceryItem> groceryItems;
    static GroceryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groceryadder);
        grocerAdd = findViewById(R.id.groceryItemAdd);
        groceryToHome = findViewById(R.id.grocerToHome);
        groceryInput = findViewById(R.id.groceryItemInput);
        addBudget = findViewById(R.id.btnAddBudget);
        goToBudgetView = findViewById(R.id.groceryBudgetView);
        listView = findViewById(R.id.groceryListView);

        groceryItems = new ArrayList<>();

        adapter = new GroceryAdapter(getApplicationContext(), groceryItems);
        listView.setAdapter(adapter);

        groceryToHome.setOnClickListener(view -> {
            Intent i = new Intent(GroceryAdder.this, home.class);
            startActivity(i);
            finish();
        });

        grocerAdd.setOnClickListener(view -> {
            String item = groceryInput.getText().toString();
            if(item.isEmpty()) {
                Toast.makeText(GroceryAdder.this, "Please enter an item!", Toast.LENGTH_SHORT).show();
            } else {
                add(item);
                groceryInput.setText("");
                Toast.makeText(GroceryAdder.this, item + " is added to the list!", Toast.LENGTH_SHORT).show();
            }
        });

        addBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GroceryAdder.this, AddBudget.class);
                startActivity(i);
                finish();
            }
        });

        goToBudgetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GroceryAdder.this, BudgetAdder.class);
                startActivity(i);
                finish();
            }
        });

        loadContent();
    }

    public void loadContent() {
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, "groceryList.txt");
        Gson gson = new Gson();
        if (readFrom.exists() && readFrom.length() != 0) {
            try {
                FileInputStream stream = new FileInputStream(readFrom);
                InputStreamReader reader = new InputStreamReader(stream);
                Type listType = new TypeToken<ArrayList<GroceryItem>>() {}.getType();

                ArrayList<GroceryItem> items;
                try {
                    items = gson.fromJson(reader, listType);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    items = new ArrayList<>();
                }

                groceryItems.clear(); // Clear the existing items
                if (items != null) {
                    groceryItems.addAll(items); // Add the loaded items
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        adapter.notifyDataSetChanged(); // Notify the adapter that data set has changed
    }


    @Override
    protected void onDestroy() {
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, "groceryList.txt"));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(writer);
            Gson gson = new Gson();
            gson.toJson(groceryItems, outputStreamWriter);
            outputStreamWriter.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        super.onDestroy();
    }

    public static void add(String item) {
        GroceryItem groceryItem = new GroceryItem(item, 1);
        groceryItems.add(groceryItem);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        saveData();
    }

    public static void addItemQuantity(int position) {
        GroceryItem item = groceryItems.get(position);
        item.setGroceryQuantity(item.getGroceryQuantity() + 1);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        saveData();
    }

    public static void decrementItemQuantity(int position) {
        GroceryItem item = groceryItems.get(position);
        if (item.getGroceryQuantity() > 1) {
            item.setGroceryQuantity(item.getGroceryQuantity() - 1);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(listView.getContext(), "Quantity cannot be less than 1!", Toast.LENGTH_SHORT).show();
        }
        saveData();
    }

    public static void removeItem(int removedItem) {
        groceryItems.remove(removedItem);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        saveData();

        File path = listView.getContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, "groceryList.txt"));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(writer);
            Gson gson = new Gson();
            gson.toJson(groceryItems, outputStreamWriter);
            outputStreamWriter.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveData() {
        File path = listView.getContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, "groceryList.txt"));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(writer);
            Gson gson = new Gson();
            gson.toJson(groceryItems, outputStreamWriter);
            outputStreamWriter.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
