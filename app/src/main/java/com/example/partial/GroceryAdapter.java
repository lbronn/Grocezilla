package com.example.partial;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GroceryAdapter extends ArrayAdapter<String> {
    ArrayList<String> list;
    Context context;

    public GroceryAdapter(@NonNull Context context, ArrayList<String> groceryItems) {
        super(context, R.layout.grocerylist_recycleritems, groceryItems);
        this.context = context;
        list = groceryItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.grocerylist_recycleritems, null);

            TextView grocerItemNum = convertView.findViewById(R.id.groceryItemNumber);
            grocerItemNum.setText(position + 1 + ".");
            TextView grocerItemName = convertView.findViewById(R.id.groceryItemName);
            grocerItemName.setText(list.get(position));
            ImageView copyGrocery = convertView.findViewById(R.id.copyGroceryItem);
            ImageView removeGrocery = convertView.findViewById(R.id.removeGroceryItem);

            copyGrocery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GroceryAdder.addItem(list.get(position));
                    Toast.makeText(context, GroceryAdder.groceryItems.get(position) + " is added again to the list.", Toast.LENGTH_SHORT).show();
                }
            });

            removeGrocery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String removedItem = list.get(position);
                    GroceryAdder.removeItem(position);
                    Toast.makeText(context, removedItem + " is removed from the list.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return convertView;
    }
}
