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

public class GroceryAdapter extends ArrayAdapter<GroceryItem> {
    ArrayList<GroceryItem> list;
    Context context;

    public GroceryAdapter(@NonNull Context context, ArrayList<GroceryItem> groceryItems) {
        super(context, R.layout.grocerylist_recycleritems, groceryItems);
        this.context = context;
        this.list = groceryItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.grocerylist_recycleritems, null, true);
        }

        TextView grocerItemNum = convertView.findViewById(R.id.groceryItemNumber);
        grocerItemNum.setText(position + 1 + ".");

        TextView grocerItemName = convertView.findViewById(R.id.groceryItemName);
        grocerItemName.setText(list.get(position).getGroceryItem() + " - " + list.get(position).getGroceryQuantity() + " pcs.");

        ImageView copyGrocery = convertView.findViewById(R.id.copyGroceryItem);
        ImageView removeGrocery = convertView.findViewById(R.id.removeGroceryItem);
        ImageView decrementGrocery = convertView.findViewById(R.id.decrementGroceryItem);

        copyGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroceryAdder.addItemQuantity(position);
                Toast.makeText(context, list.get(position).getGroceryItem() + " quantity increased.", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });

        decrementGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroceryAdder.decrementItemQuantity(position);
                Toast.makeText(context, list.get(position).getGroceryItem() + " quantity decreased.", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });

        removeGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String removedItem = list.get(position).getGroceryItem();
                GroceryAdder.removeItem(position);
                Toast.makeText(context, removedItem + " is removed from the list.", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
